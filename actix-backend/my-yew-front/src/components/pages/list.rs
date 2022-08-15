
use crate::components::molecules::label_row::LabelRow;
use crate::components::molecules::custom_form::Data;
use crate::components::atoms::custom_button::CustomButton;
use crate::router::Route;
use crate::api::api_list;



use yew::prelude::*;
use yew_router::{prelude::*, history};



#[derive(Default)]
pub struct List{
    pub data_list: Vec<Data>,
}


impl Component for List {
    type Message = ();
    type Properties = ();

    fn create(ctx: &Context<Self>) -> Self {
        

        // let (page, limit) = (1u32, 5u32);
        // let mut list_response = List::default().data_list;
        // wasm_bindgen_futures::spawn_local(async move {
        //     list_response = api_list(page, limit).await
            
        // });
        // Self{data_list: list_response}
        
        // Self {
        //     data_list: vec![
        //         Data{
        //             id: 1,
        //             chem_name: "benzene".to_string(),
        //             chem_cas: "71-43-2".to_string(),
        //             chem_quantity: "1L".to_string(),
        //         },
        //         Data{
        //             id: 2,
        //             chem_name: "acetone".to_string(),
        //             chem_cas: "67-64-1".to_string(),
        //             chem_quantity: "1.5L".to_string(),
        //         },
        //         Data{
        //             id: 3,
        //             chem_name: "rther".to_string(),
        //             chem_cas: "	60-29-7".to_string(),
        //             chem_quantity: "3.0L".to_string(),
        //         }
        //     ]
        // }
    }

    fn view(&self, ctx: &Context<Self>) -> Html {
        let data_list = self.data_list.clone();
        let history = ctx.link().history().unwrap();
        let go_home_onclick = Callback::from(move |_| history.push(Route::Home) );

        html!{
            <div>
                {
                    data_list.into_iter().map(|data| {
                        html!{ 
                            < LabelRow data={data} />
                          }
                    }).collect::<Html>()
                }

                <CustomButton label={"Go Home"} onclick={go_home_onclick} />
            </div>

        }
    }
}