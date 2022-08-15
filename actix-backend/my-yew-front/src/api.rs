use reqwasm::http::Request as ReqwasmReq;
use serde::{Deserialize, Serialize };
use serde_json::json;
use wasm_bindgen::prelude::*;
use wasm_bindgen::JsCast;
use wasm_bindgen_futures::JsFuture;
use web_sys::{Request, RequestInit, RequestMode, Response, Blob};

use crate::components::molecules::custom_form::Data;



#[derive(Serialize, Deserialize)]
pub struct ApiLoginResponse{
    pub username:String,
    pub token: String,
}

#[derive(Serialize, Deserialize)]
pub struct ApiLoginResponseData{
    pub data: ApiLoginResponse,
}


// pub async fn api_login(username:String, password:String) -> ApiLoginResponse{
//     let body = json!({
//         "username": username,
//         "password": password
//     });

//     let response = Request::post("http://localhost:8000/api/v1/user/login")
//         .header("content-type", "application/json")
//         .body(body.to_string())
//         .send()
//         .await
//     .unwrap()
//     .json::<ApiLoginResponseData>()
//     .await
//     .unwrap();

//     response.data
// }

#[derive(Serialize, Deserialize)]
pub struct EditResponse{
    pub response: String,
}


pub async fn api_edit(data:Data) -> EditResponse {
    let body = json!(data);
    let edit_response = ReqwasmReq::post("/edit")
    .header("content-type", "application/json")
    .body(body.to_string())
    .send()
    .await
    .unwrap()
    .json::<EditResponse>()
    .await
    .unwrap();

    edit_response

}


#[derive(Serialize, Deserialize)]
pub struct DeleteResponse {
    pub response: String,
}


pub async fn api_delete(id: u32) -> DeleteResponse {
    let id = json!(id);
    let delete_response = ReqwasmReq::post("/delete")
        .header("content-type", "application/json")
        .body(id.to_string())
        .send()
        .await
    .unwrap()
    .json()
    .await
    .unwrap();

    delete_response
}

#[derive(Deserialize, Serialize)]
pub struct Params {
    page: u32,
    posts_per_page: u32,
}


pub async fn api_list(page:u32, limit: u32) -> Vec<Data> {
    let params = Params{
        page: page,
        posts_per_page: limit
    };
    let mut opts = RequestInit::new();
        opts.method("GET");
        opts.mode(RequestMode::Cors);
    
    let request = Request::new_with_str_and_init("/list", &opts).unwrap();
    let window = gloo::utils::window();
    let resp_value = JsFuture::from(window.fetch_with_request(&request)).await.unwrap();
    let resp: Response = resp_value.dyn_into().unwrap();
    let data = JsFuture::from(resp.json().unwrap()).await.unwrap().as_string();
    if let Some(data) {
        data
    }

    // let list_response = Request::get("/list")
    //     .header("content-type", "application/json")
    //     .body(serde_json::to_string(&params).unwrap())
    //     .send()
    //     .await
    // .unwrap()
    // .json()
    // .await
    // .unwrap();

    // list_response


}

