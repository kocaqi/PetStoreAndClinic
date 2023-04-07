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

            navigate("/")
            
        } else {
            setError("Wrong Username or Password")
        }
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
        <form onSubmit={formik.handleSubmit}>
            <h1>Welcome Back!</h1>
            <p>{error}</p>
            <div>
                <input
                name="username"
                value={formik.values.username}
                onChange={formik.handleChange}
                placeholder="username"
                size="large"
                />
            </div>
            <div>
                <input
                name="password"
                value={formik.values.password}
                onChange={formik.handleChange}
                placeholder="Password"
                size="large"
                type="password"
                />
            </div>
            <div>
                <button size="large" kind="primary" type="submit">
                Login
                </button>
            </div>
        </form>
    );
}