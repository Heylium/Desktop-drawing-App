use entity::post;
use entity::post::Entity as Post;
use migration::{Migrator, MigratorTrait};


use actix_files::Files;
use actix_web::{
    error, get, middleware, post, web, App, Error, HttpRequest, HttpResponse, HttpServer, Result,
};
use listenfd::ListenFd;
use sea_orm::DatabaseConnection;
use sea_orm::{entity::*, query::*};
use serde::{Deserialize, Serialize};
use std::env;
use std::rc::Weak;
use actix_http::client::SendRequestError::Response;
// use actix_http::Response;




const DEFAULT_POSTS_PER_PAGE: usize = 6;

#[derive(Debug, Clone)]
struct AppState {
    conn: DatabaseConnection,
}


#[derive(Debug, Clone, Deserialize, Serialize)]
struct FlashData {
    kind: String,
    message: String,
}




/**
 * get "new" request
 */
#[get("/new")]
async fn new(data: web::Data<AppState>) -> Result<HttpResponse, Error> {

    Ok(HttpResponse::Ok().content_type("text/html").body("new route response."))
}

/**
 * get "create" request
 */
#[post("/")]
async fn create(
    data: web::Data<AppState>,
    post_form: web::Form<post::Model>,
) -> HttpResponse {
    let conn = &data.conn;

    let form = post_form.into_inner();

    post::ActiveModel {
        chem_name: Set(form.chem_name.to_owned()),
        chem_cas: Set(form.chem_cas.to_owned()),
        chem_quantity: Set(form.chem_quantity.to_owned()),
        ..Default::default()
    }
    .save(conn)
    .await
    .expect("could not insert post");

    HttpResponse::Ok().body("ok from create response")
}

#[get("/{id}")]
async fn edit(data: web::Data<AppState>, id: web::Path<i32>) -> Result<HttpResponse, Error> {
    let conn = &data.conn;

    Ok(HttpResponse::Ok().content_type("text/html").body("response from edit"))
}

#[post("/{id}")]
async fn update(
    data: web::Data<AppState>,
    id: web::Path<i32>,
    post_form: web::Form<post::Model>,
) -> HttpResponse {
    let conn = &data.conn;
    let form = post_form.into_inner();

    post::ActiveModel {
        id: Set(id.into_inner()),
        chem_name: Set(form.chem_name.to_owned()),
        chem_cas: Set(form.chem_cas.to_owned()),
        chem_quantity: Set(form.chem_quantity.to_owned()),
    }
    .save(conn)
    .await
    .expect("could not edit post");

    HttpResponse::Ok().body("response from update response")
}

#[post("'/delete/{id}")]
async fn delete(
    data: web::Data<AppState>,
    id: web::Path<i32>,
) -> HttpResponse {
    let conn = &data.conn;

    let post: post::ActiveModel = Post::find_by_id(id.into_inner())
        .one(conn)
        .await
        .unwrap()
        .unwrap()
        .into();

    post.delete(conn).await.unwrap();

    HttpResponse::Ok().body("response from delete response")
}

pub fn init(cfg: &mut web::ServiceConfig) {
    // cfg.service(list);
    cfg.service(new);
    cfg.service(create);
    cfg.service(edit);
    cfg.service(update);
    cfg.service(delete);
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    std::env::set_var("RUST_LOG", "debug");
    tracing_subscriber::fmt::init();

    dotenv::dotenv().ok();
    let db_url = env::var("DATABASE_URL").expect("DATABASE_URL NOT FOUND IN .env file");
    let host = env::var("HOST").expect("HOST NOT FOUND IN .env file");
    let port = env::var("PORT").expect("PORT NOT FOUND IN .env file");
    let server_url = format!("{}:{}", host, port);

    //create table if not exist
    let conn = sea_orm::Database::connect(&db_url).await.unwrap();
    Migrator::up(&conn, None).await.unwrap();
    // let templates = Tera::new(concat!(env!("CARGO_MANIFEST_DIR"), "/templates/**/*")).unwrap();
    let state = AppState { conn };

    let mut listenfd = ListenFd::from_env();

    let mut server = HttpServer::new(move || {
        App::new()
            .app_data(state.clone())
            .service(Files::new("/","./dist").index_file("index.html"))
            .wrap(middleware::Logger::default())
            
            .configure(init)
            
    });

    server = match listenfd.take_tcp_listener(0)? {
        Some(listener) => server.listen(listener)?,
        None => server.bind(&server_url)?,
    };
    println!("Starting server at {}", server_url);
    server.run().await?;

    Ok(())
}
