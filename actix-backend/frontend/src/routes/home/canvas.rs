use std::cell::Cell;
use std::rc::Rc;
use wasm_bindgen::prelude::*;
use wasm_bindgen::JsCast;
use yew::prelude::*;


#[function_component(Canvas)]
pub fn canvas()->Html {
    let document = web_sys::window().unwrap().document().unwrap();
    let canvas = document.create_element("canvas").unwrap().dyn_into::<web_sys::HtmlCanvasElement>().unwrap();

    document.body().unwrap().append_child(&canvas);
    canvas.set_width(900);
    canvas.set_height(900);

    canvas.style().set_property("border", "solid").unwrap();
    let context = canvas.get_context("2d").unwrap().unwrap().dyn_into::<web_sys::CanvasRenderingContext2d>().unwrap();
    let context  = Rc::new(context);
    let pressed = Rc::new(Cell::new(false));
    {
        let context = context.clone();
        let pressed = pressed.clone();
        let closure = Closure::wrap(Box::new(move |event: web_sys::MouseEvent| {
            if pressed.get() {
                // context.line_to(event.offset_x() as f64, event.offset_y() as f64);
                // context.stroke();
                // context.begin_path();
                // context.move_to(event.offset_x() as f64, event.offset_y() as f64);
        
            }
        }) as Box<dyn FnMut(_)>);

        canvas.add_event_listener_with_context("mousemove", closure.as_ref().unchecked_ref()).unwrap();
    }


    html!{
        <></>
    }
}