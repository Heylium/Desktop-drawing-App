
use yew::prelude::*;

use crate::components::molecules::custom_form::{CustomForm, Data};



pub struct Edit;

impl Component for Edit {
    type Message = ();
    type Properties = ();

    fn create(ctx: &Context<Self>) -> Self {
        Self
    }

    fn view(&self, ctx: &Context<Self>) -> Html {
        // let edit_form_submit = {
        //     let user_state = chem_state.clone();
        //     Callback::from(move |data: Data| {
        //         let mut user = user_state.deref().clone();
        //         user.chem_name = data.chem_name;
        //         user.chem_cas = data.chem_cas;
        //         user_state.set(user);
        //     })
        // };

        html!{
            <CustomForm  />
        }
    }
}