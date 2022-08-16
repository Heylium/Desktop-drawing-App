use axum::Json;
use axum::response::Response;
use axum::routing::{Route, get_service};
use entity::post;
use entity::post::Entity as Post;
use migration::tests_cfg::json;
use migration::{Migrator, MigratorTrait, Query};

// use actix_files::Files;
// use actix_web::{
//     error, get, middleware, post, web, App, Error, HttpRequest, HttpResponse, HttpServer, Result,
// };
use listenfd::ListenFd;
use sea_orm::DatabaseConnection;
use sea_orm::{entity::*, query::*};
use serde::{Deserialize, Serialize};
// use serde_json::Result;
use std::env;
use std::rc::Weak;
use std::io;
use std::net::{SocketAddr, ToSocketAddrs};
use std::str::FromStr;
// use actix_http::client::SendRequestError::Response;
// use actix_http::Response;

use axum::{
    extract::{Extension, Query as AxumQuery},
    http::StatusCode,
    response::{IntoResponse},
    routing::get,
    Router,
};

use tracing_subscriber::{layer::SubscriberExt, util::SubscriberInitExt};


use tower::{ServiceBuilder};
use tower_http::services::ServeDir;



#[derive(Debug, Clone)]
struct AppState {
    conn: DatabaseConnection,
}

// The query parameters for list index
#[derive(Debug, Deserialize, Default)]
pub struct Pagination {
    pub offset: Option<usize>,
    pub limit: Option<usize>,
}

#[tokio::main]
async fn main() {
    tracing_subscriber::registry().with(tracing_subscriber::EnvFilter::new(
        std::env::var("RUST_LOG").unwrap_or_else(|_| "example_tokio_postgres=debug".into()),
    ))
    .with(tracing_subscriber::fmt::layer())
    .init();
    //get database url
    dotenv::dotenv().ok();
    let db_url = env::var("DATABASE_URL").expect("DATABASE_URL NOT FOUND IN .env file");
    let host = env::var("HOST").expect("HOST NOT FOUND IN .env file");
    let port = env::var("PORT").expect("PORT NOT FOUND IN .env file");
    let server_url = format!("{}:{}", host, port);

    let conn = sea_orm::Database::connect(&db_url).await.expect("sea_orm connection failed");
    Migrator::up(&conn, None).await.unwrap();
    // let state = AppState { conn };

    let app = Router::new()
        .route("/list", get(list))
        .fallback(get_service(ServeDir::new("./dist")).handle_error(handle_error))
        // .merge(SpaRouter::new("/", "../dist"))
        .layer(
            ServiceBuilder::new()
                .layer(Extension(conn)),
        );

    let addr = SocketAddr::from_str(&server_url).unwrap();
    axum::Server::bind(&addr)
        .serve(app.into_make_service())
        .await
    .unwrap();

    println!("Starting server at {}", server_url);
}

#[derive(Deserialize)]
struct Params {
    page: Option<usize>,
    posts_per_page: Option<usize>,
}

#[derive(Serialize, Deserialize)]
struct Chem{
    pub id: u32,
    pub chem_name: String,
    pub chem_cas: String,
    pub chem_quantity: String,
}


async fn list(
    Extension(ref conn): Extension<DatabaseConnection>,
    AxumQuery(params): AxumQuery<Params>,
) -> Result<Json<serde_json::Value>, (StatusCode, &'static str)> {
    let page = params.page.unwrap_or(1);
    let posts_per_page = params.posts_per_page.unwrap_or(5);
    let mut paginator = Post::find()
        .order_by_asc(post::Column::Id)
        .into_json()
        .paginate(conn, posts_per_page);

    
    if let Some(chems) = paginator.fetch_and_next().await.unwrap() {
            // return Ok(Json(json!(chems)));
            let chems = vec![
                Chem{
                    id: 1,
                    chem_name: "benzene".to_string(),
                    chem_cas: "71-43-2".to_string(),
                    chem_quantity: "1L".to_string(),
                },
                Chem{
                    id: 2,
                    chem_name: "acetone".to_string(),
                    chem_cas: "67-64-1".to_string(),
                    chem_quantity: "1.5L".to_string(),
                },
                Chem{
                    id: 3,
                    chem_name: "rther".to_string(),
                    chem_cas: "	60-29-7".to_string(),
                    chem_quantity: "3.0L".to_string(),
                }
            ];
            return Ok(Json(json!(chems)));
    } else {
        return Err((StatusCode::NO_CONTENT, "no cheminfo found"));
    }

}

async fn handle_error(_err: io::Error)-> impl IntoResponse {
    (StatusCode::INTERNAL_SERVER_ERROR, "Something went wrong...")
}




// #[derive(Debug, Clone, Deserialize, Serialize)]
// struct FlashData {
//     kind: String,
//     message: String,
// }

// /**
//  * get "new" request
//  */
// #[get("/new")]
// async fn new(data: web::Data<AppState>) -> Result<HttpResponse, Error> {

//     Ok(HttpResponse::Ok().content_type("text/html").body("new route response."))
// }

// /**
//  * get "create" request
//  */
// #[post("/")]
// async fn create(
//     data: web::Data<AppState>,
//     post_form: web::Form<post::Model>,
// ) -> HttpResponse {
//     let conn = &data.conn;

//     let form = post_form.into_inner();

//     post::ActiveModel {
//         chem_name: Set(form.chem_name.to_owned()),
//         chem_cas: Set(form.chem_cas.to_owned()),
//         chem_quantity: Set(form.chem_quantity.to_owned()),
//         ..Default::default()
//     }
//     .save(conn)
//     .await
//     .expect("could not insert post");

//     HttpResponse::Ok().body("ok from create response")
// }

// #[get("/{id}")]
// async fn edit(data: web::Data<AppState>, id: web::Path<i32>) -> Result<HttpResponse, Error> {
//     let conn = &data.conn;

//     Ok(HttpResponse::Ok().content_type("text/html").body("response from edit"))
// }

// #[post("/{id}")]
// async fn update(
//     data: web::Data<AppState>,
//     id: web::Path<i32>,
//     post_form: web::Form<post::Model>,
// ) -> HttpResponse {
//     let conn = &data.conn;
//     let form = post_form.into_inner();

//     post::ActiveModel {
//         id: Set(id.into_inner()),
//         chem_name: Set(form.chem_name.to_owned()),
//         chem_cas: Set(form.chem_cas.to_owned()),
//         chem_quantity: Set(form.chem_quantity.to_owned()),
//     }
//     .save(conn)
//     .await
//     .expect("could not edit post");

//     HttpResponse::Ok().body("response from update response")
// }

// #[post("'/delete/{id}")]
// async fn delete(
//     data: web::Data<AppState>,
//     id: web::Path<i32>,
// ) -> HttpResponse {
//     let conn = &data.conn;

//     let post: post::ActiveModel = Post::find_by_id(id.into_inner())
//         .one(conn)
//         .await
//         .unwrap()
//         .unwrap()
//         .into();

//     post.delete(conn).await.unwrap();

//     HttpResponse::Ok().body("response from delete response")
// }

// pub fn init(cfg: &mut web::ServiceConfig) {
//     // cfg.service(list);
//     cfg.service(new);
//     cfg.service(create);
//     cfg.service(edit);
//     cfg.service(update);
//     cfg.service(delete);
// }

// #[actix_web::main]
// async fn main() -> std::io::Result<()> {
//     std::env::set_var("RUST_LOG", "debug");
//     tracing_subscriber::fmt::init();

//     dotenv::dotenv().ok();
//     let db_url = env::var("DATABASE_URL").expect("DATABASE_URL NOT FOUND IN .env file");
//     let host = env::var("HOST").expect("HOST NOT FOUND IN .env file");
//     let port = env::var("PORT").expect("PORT NOT FOUND IN .env file");
//     let server_url = format!("{}:{}", host, port);

//     // create table if not exist
//     let conn = sea_orm::Database::connect(&db_url).await.unwrap();
//     Migrator::up(&conn, None).await.unwrap();
//     // let templates = Tera::new(concat!(env!("CARGO_MANIFEST_DIR"), "/templates/**/*")).unwrap();
//     let state = AppState { conn };

//     let mut listenfd = ListenFd::from_env();

//     let mut server = HttpServer::new(move || {
//         App::new()
//             .app_data(state.clone())
//             .service(Files::new("/","./dist").index_file("index.html"))
//             .wrap(middleware::Logger::default())
            
//             .configure(init)
            
//     });

//     server = match listenfd.take_tcp_listener(0)? {
//         Some(listener) => server.listen(listener)?,
//         None => server.bind(&server_url)?,
//     };
//     println!("Starting server at {}", server_url);
//     server.run().await?;

//     Ok(())
// }
