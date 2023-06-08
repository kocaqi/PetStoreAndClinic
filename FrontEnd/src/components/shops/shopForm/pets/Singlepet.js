import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useState } from "react"

import { AiFillEye } from 'react-icons/ai';
import { AiFillEdit } from 'react-icons/ai';
import { AiFillDelete } from 'react-icons/ai';
import { MdLocationOn } from "react-icons/md";
import { MdFilterListAlt } from "react-icons/md";

import { PetForm } from './viewPetForm';
import { ConfirmationPage } from '../../../commons';



export function SinglePet(props) {

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
          <tr style={hover ? {...pet, ...petHover} : pet} onMouseOver={MouseOver} onMouseOut={MouseOut}>
            <td style={textField}>
              <div style={title}>
                <div style={thumbnail}>
                  <img src={props.pet_data.ImageURL+(props.custom_key+1)+".png"} alt="" style={thumbnailImage}/>
                </div>
                <div >
                  <div>
                    <div>
                      <h5 style={name}><a href="#" onClick={(e) => props.onPageChange(e , <PetForm pet_id={props.pet_data.pet_id} type={props.type} onClose={props.onClose}/>)} user_id = {props.pet_data.pet_id} style={{"text-decoration": "none", "color": "#2d3b55"}}>{props.pet_data.Name}</a></h5>
                    </div>
                  </div>
                </div>
              </div>
            </td>
            <td style={textField}>
              <span style={textFieldText}>{props.pet_data.Type}</span>
            </td>
            <td style={textField}>
              <span style={textFieldText}>{props.pet_data.pet_id}</span>
            </td>

            <td style={actions}>
              <ul style={actionList}>
                {props.type=="edit" || props.type=="self" ? <li style={actionListItem}><a href="#"style={{"color": "#D13C1D"}} onClick={onDeleteClick}><AiFillDelete size="25"/></a></li> : ""}
              </ul>
            </td>
          </tr>
      );
}


const thumbnail = {
    "margin-right": "25px",
    "-webkit-box-flex": 0,
    "-ms-flex": "0 0 80px",
    "flex": "0 0 80px",
    "border": "none",
    "display": "block",
}

const thumbnailImage = {
    "width": "80px",
    "height": "80px",
    "-o-object-fit": "cover",
    "object-fit": "cover",
    "overflow": "hidden",
    "border-radius": "50%",
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
    "font-weight": "bold"
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


const pet = {
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

const petHover = {
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


