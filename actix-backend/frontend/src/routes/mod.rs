use yew::prelude::*;
use yew_router::prelude::*;

pub mod home;
pub mod main_view;



use home::Home;


/// App routes
#[derive(Routable, Debug, Clone, PartialEq)]
pub enum AppRoute {
    #[at("/")]
    Home,
    
}

pub fn switch(route: &AppRoute) -> Html {
    match route {
        AppRoute::Home => html! {<Home />},
        
    }
}