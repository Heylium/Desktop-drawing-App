use super::main_view::MainView;


use yew::prelude::*;


/// Home page with an article list and a tag list.
#[function_component(Home)]
pub fn home() -> Html {
    // let tag = use_state(|| None);
    // let callback = {
    //     let tag = tag.clone();
    //     Callback::from(move |t| {
    //         tag.set(Some(t));
    //     })
    // };

    html! {
        <div class="home-page">
            
            <div class="container page">
                <div class="row">
                    
                    <div class="col-md-3 col-xs-12">
                        <div class="sidebar">
                            // <p>{ "Popular Tags" }</p>
                            
                            <MainView />
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    }
}