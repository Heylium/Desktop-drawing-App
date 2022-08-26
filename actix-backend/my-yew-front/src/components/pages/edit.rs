use std::ops::Deref;

use yew::prelude::*;

use crate::components::molecules::custom_form::{CustomForm, Data};
use crate::components::atoms::custom_button::CustomButton;

use crate::api::api_edit;


#[derive(Properties, PartialEq)]
pub struct EditProps{
    pub id: String,
}

pub struct Edit{
    pub data: Data,
}

impl Component for Edit{
    type Message = ();
    type Properties = ();
    // type Properties = EditProps;

    fn create(ctx: &Context<Self>) -> Self {
        Self {
            data: Data {id:4, chem_name: "chem_name_init".to_owned(), chem_cas: "chem_cas_init".to_owned(), chem_quantity: "chem_quantity_init".to_owned() }
        }
    }

    fn view(&self, ctx: &Context<Self>) -> Html {

        let data = self.data.clone();
    
        let custom_form_edit = Callback::from(move |_| {
    
            let copied_data = data.clone();
            wasm_bindgen_futures::spawn_local(async move {
                let response = api_edit(copied_data).await;
            })

        });
        
        html!{
            <div>
                <CustomForm   />
                <CustomButton label="Edit-Submit" onclick={custom_form_edit} />
            </div>

        }
    }
}