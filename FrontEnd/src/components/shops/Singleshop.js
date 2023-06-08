import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useState } from "react"

import { AiFillEye } from 'react-icons/ai';
import { AiFillEdit } from 'react-icons/ai';
import { AiFillDelete } from 'react-icons/ai';
import { MdLocationOn } from "react-icons/md";
import { MdFilterListAlt } from "react-icons/md";

import { ConfirmationPage } from '../commons';
import { removeUser } from './js/removeUser';

export function SingleShop(props) {

    const [hover, setHover] = useState(false);
    const [cookies, setCookie] = useCookies();


    function MouseOver(event) {
        setHover(true)
    }
    function MouseOut(event){
        setHover(false)
    }

    async function onDeleteConfirm(){
      await removeUser(props.user_data.id)
      props.closeOverlay()
    }

    function onDeleteDecline(){
      props.closeOverlay()
    }


    function onDeleteClick(){
      props.openCustomOverlay(null , <div style={Overlay}><div style={Container}><ConfirmationPage onAccept={onDeleteConfirm} onDecline={onDeleteDecline}/></div></div>)
    }

      
      return (
          <tr style={hover ? {...client, ...clientHover} : client} onMouseOver={MouseOver} onMouseOut={MouseOut}>
            <td style={title}>
              <div style={thumbnail}>
                <img src={"https://bootdey.com/img/Content/avatar/avatar"+(Number(props.user_data.id%8)+1)+".png"} alt="" style={thumbnailImage}/>
              </div>
              <div>
                <div>
                  <div>
                    <h5 style={name}><a href="#" onClick={(e) => props.onOpenUserForm(e, props.user_data.id)} user_id = {props.user_data.id} style={{"text-decoration": "none", "color": "#2d3b55"}}>{props.user_data.name}</a></h5>
                  </div>
                  <div>
                    <ul style={list}>
                      <li style={listItem}><MdLocationOn size="15" />{props.user_data.address}</li>
                    </ul>
                  </div>
                </div>
              </div>
            </td>
            <td style={textField}>
              <span style={textFieldText}>Shop ID: {props.user_data.id}</span>
            </td>
            <td style={actions}>
              <ul style={actionList}>
                <li style={actionListItem}><a href="#" onClick={(e) => props.onOpenUserForm(e, props.user_data.id)} user_id = {props.user_data.id} style={{"color": "#2d3b55"}}><AiFillEye size="25"/></a></li>
                {cookies.user.role.id==1 ? <li style={actionListItem}><a href="#" onClick={(e) => props.onOpenUserForm(e, props.user_data.id, "edit")} user_id = {props.user_data.id} style={{"color": "#60C656"}}><AiFillEdit size="25"/></a></li> : ""}
                {cookies.user.role.id==1 ? <li style={actionListItem}><a href="#"style={{"color": "#D13C1D"}} onClick={onDeleteClick}><AiFillDelete size="25"/></a></li> : ""}
              </ul>
            </td>
          </tr>
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
  "height": "20%",
  "width": "40%",
  "z-index": '5',
  "margin-left": "25%",
  "margin-top": "5%",
  "border-radius": "10px",
  "padding": "30px",
  "padding-top": "0px",
  "font-family": "Open Sans, sans-serif",
  "overflow": "auto"
}

const thumbnail = {
    "margin-right": "25px",
    "-webkit-box-flex": 0,
    "-ms-flex": "0 0 80px",
    "flex": "0 0 80px",
    "border": "none",
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
    "font-size": "15px",
    "-webkit-box-flex": 0,
    "-ms-flex": "0 0 90px",
    "flex": "0 0 90px",
}

const textFieldText = {
    "display": "block",
    "margin": "0 auto"
}

const actions = {
    "vertical-align": "middle",
}

const list = {
    "color": "#969696",
    "display": "-webkit-box",
    "display": "-ms-flexbox",
    "display": "flex",
    "-ms-flex-wrap": "wrap",
    "flex-wrap": "wrap",
    "margin-bottom": "0px",
    "list-style-type": "none",
}

const listItem = {
    "margin": "0 4px",
    "margin": "5px 10px 5px 0px",
    "font-size": "13px",
    
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


const client = {
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

const clientHover = {
    "-webkit-box-shadow": "0px 0px 34px 4px rgba(33, 37, 41, 0.06)",
    "box-shadow": "0px 0px 34px 4px rgba(33, 37, 41, 0.06)",
    "position": "relative",
    "z-index": 99,
}


