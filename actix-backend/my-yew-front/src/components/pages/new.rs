use crate::{router::Route, components};
use yew::prelude::*;
use yew_router::prelude::*;
use components::molecules::custom_form::CustomForm;

#[function_component(New)]
pub fn new() -> Html{
    
    html!(
        <div>
            <h1>{"New"}</h1>
            <div>
                <CustomForm />
                <Link <Route> to={Route::Home}>{"To Home Page"}</Link<Route>>
            </div>
        </div>
    )
}