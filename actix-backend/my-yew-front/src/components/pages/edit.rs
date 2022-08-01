use std::ops::Deref;

use yew::prelude::*;

use crate::components::molecules::custom_form::{CustomForm, Data};
use crate::components::atoms::custom_button::CustomButton;

use crate::api::api_edit;

// #[function_component(Edit)]
// pub fn edit() -> Html {
//     let edit_state = use_state(|| 
//         //     Data{
//         //     chem_name: "chem_name".to_owned(),
//         //     chem_cas: "chem_cas".to_owned(),
//         //     chem_quantity: "quantity".to_owned(),
//         // }
//         Data::default()
//     );
//         let cloned_edit_state = edit_state.clone();

//         let data = cloned_edit_state.deref().clone();

//         let custom_form_edit = Callback::from(move |_| {
//             cloned_edit_state.set(data.clone());
//             api_edit(data.clone());
//         });


//         html!{
//             <div>
//                 <CustomForm   />
//                 <CustomButton label="Edit-Submit" onclick={custom_form_edit} />
//             </div>

//         }
// }


#[derive(Properties, PartialEq)]
pub struct Props{
    pub data: Data,
}

pub struct Edit{
    pub data: Data,
}

impl Component for Edit{
    type Message = ();
    type Properties = ();

    fn create(ctx: &Context<Self>) -> Self {
        Self {
            data: Data { chem_name: "chem_name".to_owned(), chem_cas: "chem_cas".to_owned(), chem_quantity: "chem_quantity".to_owned() }
        }
    }

    fn view(&self, ctx: &Context<Self>) -> Html {
        // let r = ctx.props().data;
        // let edit_state = &self.data;
        // let cloned_edit_state = edit_state.clone();
    
        // let data = cloned_edit_state.deref().clone();
        let data = self.data.clone();
    
        let custom_form_edit = Callback::from( |_| {
            // cloned_edit_state.set(data.clone());
            // self.data = data;
            wasm_bindgen_futures::spawn_local(async move {
                let response = api_edit(&data).await;
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