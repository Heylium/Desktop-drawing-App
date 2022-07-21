use std::ops::Deref;

use gloo::console::log;
use wasm_bindgen::JsCast;
use web_sys::HtmlInputElement;
use yew::prelude::*;
use yewdux::prelude::*;
use yewdux::prelude::*;
use yewdux_functional::use_store;
use reqwasm::http::Request;

use crate::store::{YewduxStore};
// use crate::{api::api_login};


// pub struct Login {
//     _dispatch: DispatchProps<BasicStore<YewduxStore>>,
// }

// impl Component for Login {
//     type Message = ();
//     type Properties = DispatchProps<BasicStore<YewduxStore>>;

//     fn create(ctx: &Context<Self>) -> Self {
//         let _dispatch = ctx.props().dispatch().clone();
//         Self {
//             _dispatch
//         }
//     }

//     fn view(&self, ctx: &Context<Self>) -> Html {
//         let store = use_store::<PersistentStore<YewduxStore>>();
//         let handle_form_submit = {
//             let dispatch = store.dispatch().clone();
//             store.dispatch().reduce_callback_with(move |state, event:FocusEvent| {
//                 event.prevent_default();
//                 let username = state.username.clone();
//                 let password = state.password.clone();
//                 let dispatch = dispatch.clone();
//                 wasm_bindgen_futures::spawn_local(async move {
//                     let response = api_login(username, password).await;
//                     let token = response.token;
//                     dispatch.reduce(move |state| state.token = token);

        
//                 })
//             })
    
//         };

//         let handle_username_change = ctx
//             .props()
//             .dispatch().reduce_callback_with(|state, event: Event| {
//             let username = event.target().unwrap().unchecked_into::<HtmlInputElement>().value();
//             state.username = username;
//         });

//         let handle_password_change = ctx.props()
//             .dispatch()
//             .reduce_callback_with(|state, event: Event| {
//                 let password = event
//                     .target()
//                     .unwrap()
//                     .unchecked_into::<HtmlInputElement>()
//                     .value();
//                 state.password = password;
//             });

//         html! {
//             <form onsubmit={handle_form_submit}>
//                 <h1>{"Login"}</h1>
//                 <div>
//                     <input type="text" placeholder="username" onchange={handle_username_change} />
//                 </div>

//                 <div>
//                     <input type="text" placeholder="password" onchange={handle_password_change} />
//                 </div>

//                 <div>
//                     <button>{"LOG IN"}</button>
//                 </div>
//             </form>
//         }
//     }
// }

#[function_component(Login)]
pub fn view() -> Html {
    let store = use_store::<PersistentStore<YewduxStore>>();
    let handle_form_submit = {
        let dispatch = store.dispatch().clone();
        store
            .dispatch()
            .reduce_callback_with(move |state, event: FocusEvent| {
                event.prevent_default();
                let username = state.username.clone();
                let password = state.password.clone();
                let dispatch = dispatch.clone();

                wasm_bindgen_futures::spawn_local(async move {
                    // let response = api_login(username, password).await;
                    // let token = response.token;
                    // dispatch.reduce(move |state| state.token = token);

                    let response:String = Request::post("http://localhost:8000/")
                        .header("content-type", "application/json")
                        .send()
                        .await
                    .unwrap()
                    .json()
                    .await
                    .unwrap();
                    log!("response:",response);
                })
            })
    };
    let handle_username_change = store
        .dispatch()
        .reduce_callback_with(|state, event: Event| {
            let username = event
                .target()
                .unwrap()
                .unchecked_into::<HtmlInputElement>()
                .value();
            state.username = username;
        });

    let handle_password_change = store
        .dispatch()
        .reduce_callback_with(|state, event: Event| {
            let password = event
                .target()
                .unwrap()
                .unchecked_into::<HtmlInputElement>()
                .value();
            state.password = password;
        });

    let token = if let Some(state) = store.state() {
        state.token.clone()
    } else {
        String::new()
    };

    html! {
      <form onsubmit={handle_form_submit}>
        <h1>{"Login"}</h1>
        <div>
            <input type="text" placeholder="username" onchange={handle_username_change} />
        </div>
        <div>
            <input type="password" placeholder="password" onchange={handle_password_change} />
        </div>
        <div>
            <button>{"Log in"}</button>
        </div>
        <div>
            <p>{format!("token: {}", token)}</p>
        </div>
      </form>
    }
}


