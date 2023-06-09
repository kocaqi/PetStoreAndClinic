import { useState } from "react"

import { ShopInformation } from './shopInformation';
import { FeedbackInformation } from "./feedbackInformation";

function FormCurrentPage(props){
    if(props.current==1)
        return (<ShopInformation type={props.props.type} user_id={props.props.user_id} onClose={props.props.onClose}/>)

    if(props.current==2)
        return (<FeedbackInformation type={props.props.type} user_id={props.props.user_id}/>)

}


export function ViewShopForm(props) {

    const [currentTab, setCurrentTab] = useState(1);

      return (
            <div>
                <div style={Overlay} onClick={props.onClose}>
                </div>
                <div style={Container}>
                    <div style={TabContainer}>
                        <ul style={TabList}>
                            <li style={currentTab==1 ? {...Tab, ...ActiveTab} : Tab} onClick={() => setCurrentTab(1)}>Shop Information</li>
                            <li style={currentTab==2 ? {...Tab, ...ActiveTab} : Tab} onClick={() => setCurrentTab(2)}>FeedBack Information</li>
                        </ul>
                    </div>
                    <FormCurrentPage props={props} current={currentTab}/>
                </div>
                
            </div>
      );
}

const Overlay = {
    "position": 'fixed',
    "display": 'block',
    "width": '100%',
    "height": '100%',
    "top": 0,
    "left": 0,
    "right": 0,
    "bottom": 0,
    "background-color": "rgba(0,0,0,0.5)",
    "z-index": '4',
}

const Container = {
    "background-color": "white",
    "position": 'fixed',
    "height": "80%",
    "width": "70%",
    "z-index": '5',
    "margin-left": "15%",
    "margin-top": "5%",
    "border-radius": "10px",
    "padding": "30px",
    "padding-top": "0px",
    "font-family": "Open Sans, sans-serif",
    "overflow": "auto"
}

const TabContainer = {
    "margin": "0",
}

const TabList = {
    "list-style-type": "none",
    "display": "flex",
    "margin": "0",
    "padding": "10px"
}

const Tab = {
    "display": "inline-block",
    "flex": "1",
    "text-align": "center",
    "cursor": "pointer",
    "border-radius": "5px",
    "padding": "3px",
    "font-weight": "bold",
    "color": "#2d3b55",
    "opacity": "0.7",
    "font-size": "15px",
    "font-family": "Courier, sans-serif",
}

const ActiveTab = {
    "borderBottom": "3px solid #2d3b55",
    "opacity": "1",
    "transform": "scale(1.1)"
}


