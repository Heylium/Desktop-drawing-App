//! The root app contains initial authentication and url routes

use yew::prelude::*;
use yew_router::prelude::*;

use crate::components::{
    footer::Footer, header::Header, 
};
use crate::routes::{switch, AppRoute};

/// The root app component
#[function_component(App)]
pub fn app() -> Html {
    html! {
        <>
        
            <BrowserRouter>
                <Header />
                <Switch<AppRoute> render={Switch::render(switch)} />
                <Footer />
            </BrowserRouter>
        
        </>
    }
}