import { useFormik } from "formik";
import { useState } from "react"
import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";


import { HoverButton } from "../commons";
import { PostLogin } from "./js/PostLogin";
import { SelectRole } from "./selectRole";

import axios from 'axios';




export function Login() {

    const [currentOverlay, setcurrentOverlay] = useState("");

    const navigate = useNavigate()

    //State and cookies

    const [error, setError] = useState("");
    const [buttonDisable, setButtonDisable] = useState(false);

    const [cookies, setCookie] = useCookies();



    //Set cookies in batches

    const handleCookies = (cookie_list) => {

        cookie_list.forEach(cookie => {
            setCookie(cookie.cookie_name, cookie.cookie_value, {
                path: cookie.cookie_path
            });
        })
    };


    var OpenSelectRole = (roles, email, password) => {
        setcurrentOverlay(<SelectRole roles={roles} email={email} password={password} onClose={closeOverlay}/>)
  }

  var closeOverlay = () => {
    setcurrentOverlay("")
}



    /*
    *
    * NEED TO REWORK
    * 
    * Add request to back end.
    */

    const onSubmit = async () => {
        setError("");

        var loginData = await PostLogin(formik.values) //template request
            
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
        else if(loginData.status == 300){
            OpenSelectRole(loginData.roles, formik.values.email, formik.values.password)
        }
        else if(loginData.status){
            setError(loginData.message)
            setButtonDisable(true)
        }

    }

    const clearEvents = () => {
        setError("")
        setButtonDisable(false)
    }


    const InputOnChange = (e) => {
        formik.handleChange(e)
        clearEvents()
    }


    //Used to get the values form the form and specify submit function

    const formik = useFormik({
        initialValues: {
            email: "",
            password: "",
            roleId: null
        },
        onSubmit,
    });
    

    return (
        <div>
            <div>{currentOverlay}</div>
            <form style={LoginForm} onSubmit={formik.handleSubmit}>
                <div>
                </div>
                <h1 style={Header}>Login</h1>

                <div style={ErrorContainer}>
                    <p style={error ? Error : {}}>{error}</p>
                </div>


                <div style={OuterInputContainer}>

                    <div style={InputContainer}>
                        <input
                        style={Input}
                        name="email"
                        value={formik.values.email}
                        onChange={InputOnChange}
                        placeholder="EMAIL"
                        size="large"
                        />
                    </div>

                    <div style={InputContainer}>
                        <input
                        style={Input}
                        name="password"
                        value={formik.values.password}
                        onChange={InputOnChange}
                        placeholder="PASSWORD"
                        size="large"
                        type="password"
                        />
                    </div>

                </div>
        
                <div style={ButtonContainer}>

                    <HoverButton type="submit" disabled={buttonDisable} text="Login" HoverStyle={ButtonHover} DefaultStyle={Button} />

                </div>

            </form>
        </div>
    );
}


/*************************/
/*********STYLING*********/
/*************************/

const LoginForm = {
    "background": "#fff",
    "width": "500px",
    "margin": "65px auto",
    "display": "-webkit-box",
    "display": "flex",
    "-webkit-box-orient": "vertical",
    "-webkit-box-direction": "normal",
    "flex-direction": "column",
    "border-radius": "4px",
    "box-shadow": "0 2px 25px rgba(0, 0, 0, 0.2)",
}

const Header = {
    "padding": "35px 35px 0 35px",
    "font-weight": "500"
}

//Input Styling

const OuterInputContainer = {
    "padding": "35px",
    "padding-top": "0px",
    "text-align": "center"
}

const InputContainer = {
    "padding": "12px 5px",
    
}

const Input = {
    "font-size": "14px",
    "display": "block",
    "font-family": "'Rubik', sans-serif",
    "width": "100%",
    "padding": "10px 1px",
    "border": "0",
    "border-bottom": "1px solid #747474",
    "outline": "none",
}

//Button Styling

const ButtonContainer = {
    "display": "-webkit-box",
    "display": "flex",
    "-webkit-box-orient": "horizontal",
    "-webkit-box-direction": "normal",
    "flex-direction": "row",
}

const Button = {
    "width": "100%",
    "border": "none",
    "padding": "18px",
    "font-family": "'Rubik', sans-serif",
    "cursor": "pointer",
    "text-transform": "uppercase",
    "font-weight": "550",
    "background": "#2d3b55",
    "color": "#fff",
    "border-bottom-left-radius": "4px",
    "border-bottom-right-radius": "0",
    "letter-spacing": "0.2px",
    "outline": "0",
    "-webkit-transition": "all .3s",
    "transition": "all .3s",
}

const ButtonHover = {
    
    "background": "#3c4d6d",

}


//Error Styling

const ErrorContainer = {
    "margin-left": "25px",
    "margin-right": "25px",
}

const Error = {
    "background": "#D13C1D",
    "padding": "15px",
    "margin": "0px",
    "border-radius": "10px",
    "color": "white",
    "font-weight": "550",
    "font-family": "'Rubik', sans-serif",
    "text-align": "center",
    "box-shadow": "0 2px 10px rgba(0, 0, 0, 0.2)",
    "-webkit-transition": "all .3s",
    "transition": "all .3s",
}
