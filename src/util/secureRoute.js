import { useEffect, useState, useRef } from "react"
import { useCookies } from 'react-cookie';
import { authenticateUser } from "./authenticateUser";
import { useNavigate } from "react-router-dom";


export function SecureRoute(props){

    const navigate = useNavigate()
    

    const effectRan = useRef(false)

    //State and cookies

    const [ grantAccess, setgrantAccess] = useState(false)
    const [cookies, setCookie] = useCookies();

    
    const checkUser = async () => {

        const userInfo = await authenticateUser(cookies.session_id)

        if(userInfo.LoggedIn && !props.roles.includes(userInfo.role)){ //User is logged in but it's role doesn't match the specified ones
            navigate("/not-authorized")
        }

        else if(userInfo.LoggedIn === props.LoggedIn){ //User login status matches the one specifed
            setgrantAccess(true)
        }

        else{       
            window.location.href=props.Redirect     //Redirect the user to the specifed url
        }


    }
    
    /* 
    * Plays at component load
    * 
    * Check user login status and role
    */

    useEffect(() => {

        if(!effectRan.current){

            checkUser()
        
            effectRan.current = true
        }
    }, [])
    
    
    return grantAccess ? props.Route : null
}