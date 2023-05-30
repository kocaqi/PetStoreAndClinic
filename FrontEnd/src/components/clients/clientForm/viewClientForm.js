import { useState } from "react"

import { ClientInformation } from './clientInformation';
import { PetInformation } from "./petInformation";
import { BillingInformation } from "./billingInformation";

function FormCurrentPage(props){
    if(props.current==1)
        return (<ClientInformation type={props.props.type} user_id={props.props.user_id}/>)

    if(props.current==2)
        return (<PetInformation type={props.props.type} user_id={props.props.user_id}/>)

    if(props.current==3)
        return (<BillingInformation type={props.props.type} user_id={props.props.user_id}/>)
}


export function ViewClientForm(props) {

    const [currentTab, setCurrentTab] = useState(1);

      return (
            <div>
                <div style={Overlay} onClick={props.onClose}>
                </div>
                <div style={Container}>
                    <div style={TabContainer}>
                        <ul style={TabList}>
                            <li style={currentTab==1 ? {...Tab, ...ActiveTab} : Tab} onClick={() => setCurrentTab(1)}>Client Information</li>
                            <li style={currentTab==2 ? {...Tab, ...ActiveTab} : Tab} onClick={() => setCurrentTab(2)}>Pet Information</li>
                            <li style={currentTab==3 ? {...Tab, ...ActiveTab} : Tab} onClick={() => setCurrentTab(3)}>Billing Information</li>
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

