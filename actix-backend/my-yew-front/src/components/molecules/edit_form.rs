
use std::ops::Deref;
use std::fmt;
use gloo::console::log;

use reqwasm::http::{Request, RequestMode};
use serde::{Serialize, Deserialize};
use serde_json::json;
use yew::prelude::*;

use crate::components::atoms::text_input::TextInput;
use crate::components::atoms::custom_button::CustomButton;

#[derive(Default,Clone, PartialEq, Serialize, Deserialize)]
pub struct EditData{
    pub chem_name: String,
    pub chem_cas: String,
    pub chem_quantity: String,
}

impl fmt::Display for EditData {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f, "chem_name={}&chem_cas={}&chem_quantity={}", self.chem_name,self.chem_cas, self.chem_quantity)
    }
}


#[derive(Properties, PartialEq)]
pub struct Props{
    pub onsubmit:Callback<EditData>,
}

#[function_component(EditForm)]
pub fn edit_form(props: &Props) -> Html {
    let state = use_state(|| EditData::default());
    let form_context = use_context::<EditData>();

    let cloned_state = state.clone();
    let chem_name_change = Callback::from(move |chem_name: String| {
        cloned_state.set(EditData{
            chem_name,
            ..cloned_state.deref().clone()
        });
    });

    let cloned_state = state.clone();
    let chem_cas_change = Callback::from(move |chem_cas: String| {
        cloned_state.set(EditData{
            chem_cas,
            ..cloned_state.deref().clone()
        });
    });

    let cloned_state = state.clone();
    let chem_quantity_change = Callback::from(move |chem_quantity: String| {
        cloned_state.set(EditData{
            chem_quantity,
            ..cloned_state.deref().clone()
        });
    });
    
    let form_onsubmit = props.onsubmit.clone();
    let cloned_state = state.clone();
    let onsubmit = Callback::from(move |event:FocusEvent| {
        event.prevent_default();
        let form_data = cloned_state.deref().clone();
        let cloned_data = form_data.clone();
        form_onsubmit.emit(form_data);

        wasm_bindgen_futures::spawn_local(async move {
            

            let response = Request::get("http://127.0.0.1:8080/new")
            
                .header("content-type", "application/x-www-form-urlencoded")
                .header("accept","text/html")
                .header("Access-Control-Allow-Origin", "no-cors")
                // .body(cloned_data.to_string())
                    .send()
                    .await
                    .unwrap()
                    .json::<EditData>()
                    .await
                    .unwrap()
                    ;
                           
                    log!("response:",response.to_string());

        });
        

    });

    html!{
        <form onsubmit={onsubmit}>
            <TextInput name="chem_name" handle_onchange={chem_name_change} />
            <TextInput name="chem_cas" handle_onchange={chem_cas_change} />
            <TextInput name="chem_quantity" handle_onchange={chem_quantity_change} />
            // <CustomButton label="submit" />
        </form>
    }
}