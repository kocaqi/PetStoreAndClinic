
import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { useEffect, useState, useRef } from "react"

import { useFormik } from "formik";

import { HoverButton } from '../commons';
import { PostLogin } from './js/PostLogin';


export function SelectRole(props) {

    const [cookies, setCookie] = useCookies();

    const handleCookies = (cookie_list) => {

        cookie_list.forEach(cookie => {
            setCookie(cookie.cookie_name, cookie.cookie_value, {
                path: cookie.cookie_path
            });
        })
    };


    async function onSelectRole(e, id){

    var loginData = await PostLogin({email: props.email, password: props.password, roleId: id}) //template request
            
        if(loginData.status == 200){

            handleCookies([
                {
                    cookie_name: "session_id",
                    cookie_value: loginData.sessionId,
                    cookie_path: "/"
                }
            ])
            
            window.location.href = "/"
        }
    }


    

      return (
        <div>
            <div style={Overlay} onClick={props.onClose}>
        </div>
            <div style={Container}>
                <div style={ConfirmContainer}>
                <div style={TextContainer}>
                    <div style={{"width": "fit-content","margin": "auto"}}>
                        <span style={Text}>LOG IN AS:</span>
                    </div>
                </div>
                <div style={ButtonOuterContainer}>
                    {props.roles.map((item, index) => {
                        return (
                        <div key={index} style={ButtonContainer}>
                            <div  style={{"width": "fit-content","margin": "auto"}}>
                                <HoverButton text={(item.name.replace("ROLE_", ""))} HoverStyle={ActionButtonHover} DefaultStyle={ActionButton} onClick={(e) => onSelectRole(e, item.id)} />   
                            </div>
                        </div>)
                    })}
                    
                </div>
            </div>
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
    "height": "40%",
    "width": "30%",
    "z-index": '5',
    "margin-left": "34%",
    "margin-top": "5%",
    "border-radius": "10px",
    "padding": "30px",
    "padding-top": "0px",
    "font-family": "Open Sans, sans-serif",
    "overflow": "auto"
}



const ConfirmContainer={
    "width": "25%",
    "margin": "50px auto"
}

const TextContainer={
    "width": "100%",
    "margin-bottom": "20px"
}

const Text={
    "color": "#2d3b55",
    "font-family": "Courier, sans-serif",
    "font-size": "18px",
    "font-weight": "bold"
}

const ButtonOuterContainer={
    "width": "50%",
}

const ButtonContainer={
    "flex": "1",
    "margin-top": "10px"
}

const ActionButton = {
    "border": "none",
    "padding": "15px",
    "padding-top": "8px",
    "padding-bottom": "8px",
    "font-family": "'Rubik', sans-serif",
    "font-weight": "550",
    "font-size": "13px",
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