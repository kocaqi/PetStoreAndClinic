import { useCookies } from 'react-cookie';
import { MenuItem } from './menuItem';

import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"


import { ViewClientForm } from '../clients/clientForm/viewClientForm';
import { authenticateUser } from '../../util/authenticateUser'



export function Menu(props) {
    
    const effectRan = useRef(false)

    //State and cookies

    const [ userData, setuserData] = useState({})
    const [cookies, setCookie] = useCookies();

    
    const checkUser = async () => {

        const userInfo = await authenticateUser(cookies.session_id)

        setuserData(userInfo.userData)


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


    if(cookies.user.role.id==1){
        return(
            <div style={MenuOuterContainer}>
                <div style={MenuInnerContainer}>
                    <MenuItem name="Clients" url="/clients" style={FirstItem}/>
                    <MenuItem name="Doctors" url="/doctors" />
                    <MenuItem name="Shops" url="/shops" />
                    <MenuItem name="Managers" url="/managers" />
                    <MenuItem name="Clients" url="/clients" style={SecondRow} />
                    <MenuItem name="Clients" url="/clients" style={SecondRow} />
                    <MenuItem name="My Info" url="#" style={SecondRow} />
                </div>
            </div>
        )
    }
    else if(cookies.user.role=="client"){
        return(
                <div style={MenuOuterContainer}>
                    <div style={MenuInnerContainer}>
                        <MenuItem name="Clients" url="/clients" style={FirstItem}/>
                        <MenuItem name="Clients" url="/clients" />
                        <MenuItem name="Clients" url="/clients" display={false}/>
                        <MenuItem name="Clients" url="/clients" />
                        <MenuItem name="Clients" url="/clients" style={SecondRow} />
                        <MenuItem name="Clients" url="/clients" style={SecondRow} />
                        <MenuItem name="My Info" url="#" style={SecondRow} onClick={(e) => {
                            props.onOpenProfile(e, <ViewClientForm onClose={props.onClose} type={"self"} user_id={userData.user_id}/>)
                        }}/>
                    </div>
                </div>
        )
    }
}


const MenuOuterContainer = {
    "width": "fit-content",
    "height": "100%",
    "margin": "auto",
    "display": "flex",
    "align-items": "center",
}

const MenuInnerContainer = {
    "width": "730px",
    "height": "fit-content",
    "vertical-align": "middle",
}

const SecondRow = {
    "transform": "translate(110px, -34px)",
}

const FirstItem = {
    "margin-left": "0"
}