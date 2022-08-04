

use yew::prelude::*;


pub struct DeleteButton;


impl Component for DeleteButton {
    type Message = ();
    type Properties = ();

    fn create(ctx: &Context<Self>) -> Self {
        Self
    }


    fn view(&self, ctx: &Context<Self>) -> Html {
        

        html!{
            <button>{"x"}</button>
        }
    }
}