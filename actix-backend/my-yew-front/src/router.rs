use crate::components::pages::{
    hello::Hello,
    home::Home,
    new::New,
    edit::Edit,
    list::List,
};

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
    #[at("edit/:id")]
    Edit {id: String},
    #[at("list")]
    List
}

pub fn switch(route: &Route) -> Html {
    match route {
        Route::Home => html!( <Home />),
        Route::Hello => html!( <Hello />),
        Route::New => html!( <New />),
        Route::Edit => html!( <Edit  /> ),
        Route::List => html!( <List /> ),
    }
}
