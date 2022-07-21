mod components;
mod router;
mod store;
mod login;
mod api;

use crate::components::molecules::custom_form::Data;
// use crate::components::molecules::edit_form::EditData;
use crate::router::{switch, Route};
use components::atoms::main_title::{Color, MainTitle};
use components::molecules::custom_form::CustomForm;
use components::molecules::edit_form::EditForm;
use gloo::console::log;
use std::ops::Deref;
use stylist::yew::{styled_component};
use yew::prelude::*;
use yew::ContextProvider;
use yew_router::prelude::*;
use yewdux::prelude::*;

use login::Login;



#[derive(Debug,PartialEq,Clone,Default)]
pub struct Chem {
    pub chem_name: String,
    pub chem_cas: String,
    pub chem_quantity: String,
}

#[derive(Debug, PartialEq, Clone, Default)]
pub struct Edit{
    pub chem_name: String,
    pub chem_cas: String,
    pub chem_quantity: String,
}


#[styled_component(App)]
pub fn app() -> Html {
    let chem_state = use_state(Chem::default);
    let main_title_load = Callback::from(|message:String| log!(message));

    let custom_form_submit = {
        let user_state = chem_state.clone();
        Callback::from(move |data: Data| {
            let mut user = user_state.deref().clone();
            user.chem_name = data.chem_name;
            user.chem_cas = data.chem_cas;
            user_state.set(user);
        })
    };

    let edit_state = use_state(Edit::default);
    let edit_form_submit = {
        let form_state = edit_state.clone();
        Callback::from(move |edit_data: Chem| {
            let mut edit = form_state.deref().clone();
            edit.chem_name = edit_data.chem_name;
            edit.chem_cas = edit_data.chem_cas;
            edit.chem_quantity = edit_data.chem_quantity;
            form_state.set(edit);
        })
    };

    html!{
        <ContextProvider<Chem> context={chem_state.deref().clone()}>
            <MainTitle title="Hi from Maintitle" color={Color::Ok} on_load = {main_title_load} />
            // <CustomForm onsubmit={custom_form_submit} />
            <BrowserRouter>
                <Switch<Route> render={Switch::render(switch)} />
            </BrowserRouter>
            
        </ContextProvider<Chem>>

    
    }

}