use serde::{Deserialize, Serialize};
use std::fmt;

#[derive(Debug, Serialize, Deserialize, Clone)]
pub struct Data{
    id: u32,
    chem_name: String,
    chem_cas: String,
    chem_quantity: String,
}
impl fmt::Display for Data {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(f,"<div data-id={}><div>{}</div><div>{}</div><div>{}</div></div>", self.id, self.chem_name, self.chem_cas, self.chem_quantity)
    }
}

#[derive(Serialize, Deserialize, Clone, Debug)]
#[serde(rename_all = "camelCase")]
pub struct TagListInfo {
    pub tags: Vec<Data>,
}
