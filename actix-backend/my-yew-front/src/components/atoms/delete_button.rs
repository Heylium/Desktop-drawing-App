

use yew::prelude::*;

#[derive(Properties, PartialEq)]
pub struct Props{
    pub chem_id: u32,
    pub onclick: Callback<()>
}


pub struct DeleteButton;


impl Component for DeleteButton {
    type Message = ();
    type Properties = Props;

    fn create(ctx: &Context<Self>) -> Self {
        Self
    }


    fn view(&self, ctx: &Context<Self>) -> Html {
        let onclick = ctx.props().onclick.clone();
        let delete_onclick = Callback::from(move |_| {
            onclick.emit(());
        });

        html!{
            <button onclick={delete_onclick}>{"x"}</button>
        }
    }
}