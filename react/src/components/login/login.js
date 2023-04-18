import { useFormik } from "formik";
import { useState } from "react"
import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";



export function Login() {

    const navigate = useNavigate()

    //State and cookies

    const [error, setError] = useState("");
    const [cookies, setCookie] = useCookies();


    //Set cookies in batches

    const handleCookies = (cookie_list) => {

        cookie_list.forEach(cookie => {
            setCookie(cookie.cookie_name, cookie.cookie_value, {
                path: cookie.cookie_path
            });
        })
    };



    /*
    *
    * NEED TO REWORK
    * 
    * Add request to back end.
    */

    const onSubmit = async () => {
        setError("");
        if (formik.values.username === "admin" && formik.values.password === "admin") {

            handleCookies([{
                    cookie_name: "access_token",
                    cookie_value: "asdasdasdasdasdasd",
                    cookie_path: "/"
                },
                {
                    cookie_name: "session_id",
                    cookie_value: "1111",
                    cookie_path: "/"
                }
            ])

            window.location.href = "/"
            
        } else {
            setError("Wrong Username or Password")
        }
    }

    const InputOnChange = (e) => {
        formik.handleChange(e)
        setError("")
    }


    //Used to get the values form the form and specify submit function

    const formik = useFormik({
        initialValues: {
            username: "",
            password: "",
        },
        onSubmit,
    });
    

    return (
        <form style={LoginForm} onSubmit={formik.handleSubmit}>

            <h1 style={Header}>Login</h1>

            <div style={ErrorContainer}>
                <p style={error ? Error : {}}>{error}</p>
            </div>


            <div style={OuterInputContainer}>

                <div style={InputContainer}>
                    <input
                    style={Input}
                    name="username"
                    value={formik.values.username}
                    onChange={InputOnChange}
                    placeholder="USERNAME"
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

                <button style={Button} size="large" kind="primary" type="submit">
                Login
                </button>
            </div>

        </form>
    );
}


//CSS Styling

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
    "font-weight": "300"
}

const OuterInputContainer = {
    "padding": "35px",
    "padding-top": "0px",
    "text-align": "center"
}

const InputContainer = {
    "padding": "12px 5px"
}

const Input = {
    "font-size": "16px",
    "display": "block",
    "font-family": "'Rubik', sans-serif",
    "width": "100%",
    "padding": "10px 1px",
    "border": "0",
    "border-bottom": "1px solid #747474",
    "outline": "none",
    "-webkit-transition": "all .2s",
    "transition": "all .2s",
}

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
    "background": "#e8e9ec",
    "color": "#777",
    "border-bottom-left-radius": "4px",
    "border-bottom-right-radius": "0",
    "letter-spacing": "0.2px",
    "outline": "0",
    "-webkit-transition": "all .3s",
    "transition": "all .3s",
}

const ErrorContainer = {
    "margin-left": "25px",
    "margin-right": "25px",
}

const Error = {
    "background": "red",
    "padding": "15px",
    "margin": "0px",
    "border-radius": "10px",
    "color": "white",
    "text-align": "center",
    "box-shadow": "0 2px 10px rgba(0, 0, 0, 0.2)"
}

