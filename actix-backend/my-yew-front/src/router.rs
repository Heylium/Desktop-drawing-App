use crate::components::pages::hello::Hello;
use crate::components::pages::home::Home;
use crate::components::pages::new::New;
use crate::components::pages::edit::Edit;
use yew::prelude::*;
use yew_router::prelude::*;

#[derive(Debug, Clone, PartialEq, Routable)]
pub enum Route {
    #[at("/")]
    Home,
    #[at("hello")]
    Hello,
    #[at("new")]
    New,
    #[at("edit")]
    Edit,
}

pub fn switch(route: &Route) -> Html {
    match route {
        Route::Home => html!( <Home />),
        Route::Hello => html!( <Hello />),
        Route::New => html!( <New />),
        Route::Edit => html!( <Edit /> ),
    }
}
