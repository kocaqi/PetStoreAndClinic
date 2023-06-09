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
import { AppointmentForm } from './viewAppointmentForm';
import { removeUser } from './removeUser';


export function SingleAppointment(props) {

    const [hover, setHover] = useState(false);
    const [cookies, setCookie] = useCookies();

    function MouseOver(event) {
        setHover(true)
    }
    function MouseOut(event){
        setHover(false)
    }

    async function onDeleteConfirm(){
      await removeUser(props.feedback_data.id)

      props.onClose()
    }

    function onDeleteDecline(){
      props.onClose()
    }


    function onDeleteClick(){
      props.onPageChange(null , <ConfirmationPage onAccept={onDeleteConfirm} onDecline={onDeleteDecline}/>)
    }

      
      return (
          <tr style={hover ? {...feedback, ...feedbackHover} : feedback} onMouseOver={MouseOver} onMouseOut={MouseOut} >
            <td style={textField}>
              <div style={title}>
                <div >
                  <div>
                    <div>
                      <h5 style={name}><a href="#" style={{"text-decoration": "none", "color": "#2d3b55"}} onClick={(e) => props.onPageChange(e , <AppointmentForm feedback_id={props.feedback_data.id} type={props.type} onClose={props.onClose}/>)}>{props.feedback_data["title"]}</a></h5>
                    </div>
                  </div>
                </div>
              </div>
            </td>
            <td style={textField}>
              <span style={textFieldText}>{`Author ID: ${props.feedback_data["client"].id}`}</span>
            </td>
            {props.feedback_data["message"] ?
              <td style={textField}>
                <span style={textFieldText}>{`Message: ${props.feedback_data["message"].length>20 ? props.feedback_data["message"].slice(0, 20)+"..." : props.feedback_data["message"]}`}</span>
              </td>
              : <td style={textField}>
              </td>
            }
  

            <td style={actions}>
              <ul style={actionList}>
                {cookies.user.user_id == props.feedback_data["client"].id ? <li style={actionListItem}><a href="#" onClick={onDeleteClick} style={{"color": "#D13C1D"}}><AiFillDelete size="25"/></a></li> : ""}
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


const feedback = {
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

const feedbackHover = {
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


