use reqwasm::http::Request;
use serde::{Deserialize, Serialize };
use serde_json::json;
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


pub async fn api_login(username:String, password:String) -> ApiLoginResponse{
    let body = json!({
        "username": username,
        "password": password
    });

    let response = Request::post("http://localhost:8000/api/v1/user/login")
        .header("content-type", "application/json")
        .body(body.to_string())
        .send()
        .await
    .unwrap()
    .json::<ApiLoginResponseData>()
    .await
    .unwrap();

    response.data
}

#[derive(Serialize, Deserialize)]
pub struct EditResponse{
    pub response: String,
}

pub async fn api_edit(data:Data) -> EditResponse {
    let body = json!(data);
    let edit_response = Request::post("/edit")
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
    let delete_response = Request::post("/delete")
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