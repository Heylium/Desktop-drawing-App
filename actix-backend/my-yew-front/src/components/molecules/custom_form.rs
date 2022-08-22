use std::clone;
use std::ops::Deref;
use std::borrow::Cow;

use crate::components::atoms::custom_button::{CustomButton};
use crate::components::atoms::text_input::TextInput;


use crate::{Chem, log};
use serde::{Serialize, Deserialize};
use yew::html::IntoPropValue;
use yew::prelude::*;


#[derive(Default, Clone, PartialEq, Serialize, Deserialize, Debug)]
pub struct Data {
    pub id: u32,
    pub chem_name: String,
    pub chem_cas: String,
    pub chem_quantity: String,
}

#[function_component(CustomForm)]
pub fn custom_form() -> Html {
    let state = use_state(|| Data::default());
    // let user_context = use_context::<Chem>();

    let cloned_state = state.clone();
    let chem_name_changed = Callback::from(move |chem_name| {
        
        cloned_state.set(Data {
            chem_name,
            ..cloned_state.deref().clone()
        });
    });

    let cloned_state = state.clone();
    let chem_cas_changed = Callback::from(move |chem_cas| {
        
        cloned_state.set(Data {
            chem_cas,
            ..cloned_state.deref().clone()
        });
    });

    let cloned_state = state.clone();
    let chem_quantity_changed = Callback::from(move |chem_quantity| {
        cloned_state.set(
            Data{
                chem_quantity,
                ..cloned_state.deref().clone()
            }
        )
    });


    let button_changed = Callback::from(|_| {
        log!("button clicked");
    });
    html!{
        <div >
            <TextInput name="chem_name" handle_onchange={chem_name_changed} />
            <TextInput name="chem_cas" handle_onchange={chem_cas_changed} />
            <TextInput name="chem_quantity" handle_onchange={chem_quantity_changed} />
            <CustomButton label="Submit" onclick={button_changed}/>
            <p>{"Chem name: "}{&state.deref().chem_name}</p>
            <p>{"Chem cas: "}{&state.deref().chem_cas}</p>
            <p>{"Chem quantity: "}{&state.deref().chem_quantity}</p>
        </div>
    }
}
