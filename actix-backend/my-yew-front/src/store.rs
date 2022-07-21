use yewdux::prelude::*;
use serde::{Serialize, Deserialize};

#[derive(Clone, Default, Serialize, Deserialize)]
pub struct YewduxStore {
    pub username:String,
    pub password:String,
    pub token: String,
}

// pub fn init() -> Dispatch<BasicStore<YewduxStore>> {
//     Dispatch::<BasicStore<YewduxStore>>::new()
// }


impl Persistent for YewduxStore {
    fn key() -> &'static str{"IntorduxtionToYew.rs"}

    fn area() -> Area {Area::Local}
}