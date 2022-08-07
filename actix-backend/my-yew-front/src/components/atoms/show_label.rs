
use yew::prelude::*;


#[derive(Properties, PartialEq)]
pub struct Props {
    pub label: String,
}

#[function_component(ShowLabel)]
pub fn row_label(props: &Props) -> Html {
    
    html!{
        <label class={ classes!("row-label") }>{&props.label}</label>
    }
}
