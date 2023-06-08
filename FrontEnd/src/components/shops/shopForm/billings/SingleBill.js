import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useState } from "react"

import { AiFillEye } from 'react-icons/ai';
import { AiFillEdit } from 'react-icons/ai';
import { AiFillDelete } from 'react-icons/ai';
import { MdLocationOn } from "react-icons/md";
import { MdFilterListAlt } from "react-icons/md";

import { HoverButton } from '../../../commons';
import { ConfirmationPage } from '../../../commons';



export function SingleBill(props) {

    const [hover, setHover] = useState(false);

    function MouseOver(event) {
        setHover(true)
    }
    function MouseOut(event){
        setHover(false)
    }

    function onDeleteConfirm(){
      props.onClose()
    }

    function onDeleteDecline(){
      props.onClose()
    }


    function onDeleteClick(){
      props.onPageChange(null , <ConfirmationPage onAccept={onDeleteConfirm} onDecline={onDeleteDecline}/>)
    }

      
      return (
          <tr style={hover ? {...bill, ...billHover} : bill} onMouseOver={MouseOver} onMouseOut={MouseOut}>
            <td style={textField}>
              <div style={title}>
                <div >
                  <div>
                    <div>
                      <h5 style={name}><a style={{"text-decoration": "none", "color": "#2d3b55"}}>{props.bill_data["Product Name"]}</a></h5>
                    </div>
                  </div>
                </div>
              </div>
            </td>
            <td style={textField}>
              <span style={textFieldText}>{`Bill ID: ${props.bill_data["id"]}`}</span>
            </td>
            <td style={textField}>
              <span style={textFieldText}>{`Product ID: ${props.bill_data["product_id"]}`}</span>
            </td>
            <td style={textField}>
              <span style={textFieldText}>{`Price: $${props.bill_data["Product Price"]}`}</span>
            </td>
            <td style={textField}>
              <span style={textFieldText}>{`Quantity: ${props.bill_data["Quantity"]}`}</span>
            </td>
            <td style={textField}>
              <span style={name}>{`Total: $${props.bill_data["Total"]}`}</span>
            </td>
            <td style={StatusField}>
              <span style={props.bill_data["Status"] ? {...Status, "background": "#2d3b55"} : {...Status, "background": "#D13C1D"}}>{props.bill_data["Status"] ? "Paid" : "Not Paid"}</span>
            </td>

            <td style={actions}>
              <ul style={actionList}>
                {props.type=="edit" ? <li style={actionListItem}><a href="#" onClick={onDeleteClick} style={{"color": "#D13C1D"}}><AiFillDelete size="25"/></a></li> : ""}
                {props.type=="self" && !props.bill_data["Status"] ? <HoverButton text="PAY" HoverStyle={ActionButtonHover} DefaultStyle={ActionButton} /> : ""}
              </ul>
            </td>
          </tr>
          
      );
}


const title = {
    "display": "flex",
    "height": "100%",
    "-webkit-box-align": "center",
    "-ms-flex-align": "center",
    "align-items": "center",
    "vertical-align": "middle",
    "padding": "20px 0",

    
}
const name = {
    "font-size": "16px",
    "font-weight": "bold",
    "color": "#2d3b55"
}

const textField = {
  
    "vertical-align": "middle",
    "margin-left": "auto",
    "text-align": "center",
    "-webkit-box-flex": 0,
    "-ms-flex": "0 0 90px",
    "flex": "0 0 90px",
    "padding": "20px 0",
}

const textFieldText = {
    "display": "block",
    "margin": "0 auto"
}

const StatusField = {
  "vertical-align": "middle",
  "margin-left": "auto",
  "text-align": "center",
  "-webkit-box-flex": 0,
  "-ms-flex": "0 0 90px",
  "flex": "0 0 90px",
  "padding": "20px 0",
}

const Status = {
    "display": "block",
    "margin": "0 auto",
    "font-size": "15px",
    "font-weight": "bold",
    "color": "white",
    "padding": "3px",
    "border-radius": "10%"
}


const bill = {
    "width": "100%",
    "background": "#ffffff",
    "borderBottom": "1px solid #eeeeee",
    "-webkit-box-align": "center",
    "-ms-flex-align": "center",
    "align-items": "center",
    "padding": "20px",
    "-webkit-transition": "all 0.3s ease-in-out",
    "transition": "all 0.3s ease-in-out",
}

const billHover = {
    "-webkit-box-shadow": "0px 0px 34px 4px rgba(33, 37, 41, 0.06)",
    "box-shadow": "0px 0px 34px 4px rgba(33, 37, 41, 0.06)",
    "position": "relative",
    "z-index": 99,
}

const actions = {
    "vertical-align": "middle",
    "padding": "20px 0",
}


const actionList={
    "list-style-type": "none",
    "width": "fit-content",
    "padding": 0,
    "margin": 0,
    "display": "-webkit-box",
    "display": "-ms-flexbox",
    "display": "flex",
    "-ms-flex-wrap": "wrap",
    "flex-wrap": "wrap",
    "margin": "auto",
    
}

const actionListItem = {
    "margin": "0 4px", 
}

const ActionButton = {
  "border": "none",
  "padding": "15px",
  "padding-top": "10px",
  "padding-bottom": "10px",
  "font-family": "'Rubik', sans-serif",
  "font-weight": "550",
  "font-size": "15px",
  "cursor": "pointer",
  "text-transform": "uppercase",
  "background": "#2d3b55",
  "color": "#fff",
  "border-bottom-left-radius": "4px",
  "border-bottom-right-radius": "4px",
  "letter-spacing": "0.2px",
  "outline": "0",
  "-webkit-transition": "all .3s",
  "transition": "all .3s",
}

const ActionButtonHover = {
  
  "background": "#3c4d6d",

}


