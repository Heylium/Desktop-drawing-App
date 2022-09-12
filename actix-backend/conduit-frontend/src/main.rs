use wasm_bindgen::prelude::*;

use conduit_wasm::app::App;


// Use `wee_alloc` as the global allocator.
#[global_allocator]
static ALLOC: wee_alloc::WeeAlloc = wee_alloc::WeeAlloc::INIT;

// This is the entry point for the web app

fn main() {
    wasm_logger::init(wasm_logger::Config::default());
    yew::start_app::<App>();
}