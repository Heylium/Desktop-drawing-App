
use std::ops::Deref;

use yew::prelude::*;
use yew::{classes, html};
use stylist::{yew::styled_component, Style};

use crate::components::atoms::show_label::ShowLabel;
use crate::components::atoms::delete_button::DeleteButton;
use crate::components::molecules::custom_form::Data;

use crate::api::{api_delete, self};


const STYLE_FILE: &str = include_str!("../styles/list.css");

#[derive(Properties,PartialEq)]
pub struct Props{
    pub data: Data,
    pub onclick: Option<Callback<()>>
}

#[function_component(LabelRow)]
pub fn label_row(props: &Props)->Html {
    
    let stylesheet = Style::new(STYLE_FILE).unwrap();
    
    let state = use_state(|| Data::default());
    let cloned_state = state.clone();
    // let chem_name = cloned_state.clone().deref().chem_name;
    let data = &props.data;
    let chem_id = 1u32;
    let delete_click = Callback::from(move |_| {
        let cloned_id = chem_id.clone();
        wasm_bindgen_futures::spawn_local(async move {
            let chem_id = cloned_id.clone();
            let response = api_delete(chem_id).await;
        })
    });
    html!{
        <div class={ stylesheet }>
            <ShowLabel label={data.chem_name.clone()} />
            <ShowLabel label={data.chem_cas.clone()} />
            <ShowLabel label={data.chem_quantity.clone()} />
            <DeleteButton chem_id={chem_id} onclick={delete_click}/>
        </div>
    }
}




