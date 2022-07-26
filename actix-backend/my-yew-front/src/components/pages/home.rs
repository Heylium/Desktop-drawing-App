use crate::router::Route;
use crate::components::molecules::label_row::LabelRow;
use crate::components::molecules::custom_form::Data;
use yew::prelude::*;
use yew_router::prelude::*;

#[function_component(Home)]
pub fn home() -> Html{

    let data = Data{
        chem_cas:"benzene".to_owned(),
        chem_name: "75".to_owned(),
        chem_quantity: "1L".to_owned(),
    };

    html!(
        <div>
            <h1>{"Home"}</h1>
            <div>
                <Link <Route> to={Route::New}>{"To New Page"}</Link<Route>>
            </div>
            <LabelRow data={data} />
        </div>
    )
}