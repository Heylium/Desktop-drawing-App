
use std::ops::Deref;

use yew::prelude::*;
use yew::{classes, html};
use stylist::{yew::styled_component, Style};

use crate::components::atoms::show_label::ShowLabel;
use crate::components::molecules::custom_form::Data;


const STYLE_FILE: &str = include_str!("../styles/list.css");

#[derive(Properties,PartialEq)]
pub struct Props{
    pub data: Data,
}

#[function_component(LabelRow)]
pub fn label_row(props: &Props)->Html {
    
    let stylesheet = Style::new(STYLE_FILE).unwrap();
    
    let state = use_state(|| Data::default());
    let cloned_state = state.clone();
    // let chem_name = cloned_state.clone().deref().chem_name;
    let data = &props.data;
    html!{
        <div class={ stylesheet }>
            <ShowLabel label={data.chem_name.clone()} />
            <ShowLabel label={data.chem_cas.clone()} />
            <ShowLabel label={data.chem_quantity.clone()} />
        </div>
    }
}



