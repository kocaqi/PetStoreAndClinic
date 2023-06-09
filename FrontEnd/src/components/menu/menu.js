import { useCookies } from 'react-cookie';
import { MenuItem } from './menuItem';

import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"


import { ViewClientForm } from '../clients/clientForm/viewClientForm';
import { ViewReceptionistForm } from '../receptionists/receptionistForm/viewReceptionistForm';
import { authenticateUser } from '../../util/authenticateUser'



export function Menu(props) {

    const navigate = useNavigate()
    
    const effectRan = useRef(false)

    //State and cookies

    const [ userData, setuserData] = useState({})
    const [cookies, setCookie, removeCookie] = useCookies();

    
    const checkUser = async () => {

        const userInfo = await authenticateUser(cookies.session_id)

        setuserData(userInfo)


    }

    const handleSignOut = () => {
        removeCookie('user');
        removeCookie('session_id');
        removeCookie('access_token');
        navigate("/login")

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
                    <MenuItem name="Pets" url="/pets" />
                    <MenuItem name="Shops" url="/shops" />
                    <MenuItem name="Managers" url="/managers" />
                    <MenuItem name="Managers" url="/managers" display={false}/>
                    <MenuItem name="SignOut" url="#" red="true" style={SecondRow} onClick={handleSignOut}/>
                    <MenuItem name="Doctors" url="/doctors" style={SecondRow} />
                    <MenuItem name="Receptionists" url="/receptionists" style={SecondRow} />
                    <MenuItem name="Products" url="/products" style={SecondRow} />
                    <MenuItem name="My Info" url="#" style={SecondRow} display={false} />
                </div>
            </div>
        )
    }
    else if(cookies.user.role.id==5){
        return(
            <div style={MenuOuterContainer}>
                <div style={MenuInnerContainer}>
                    <MenuItem name="Clients" url="/clients" display={false} style={FirstItem}/>
                    <MenuItem name="Pets" url="/pets" display={false} />
                    <MenuItem name="Shops" url="/shops" />
                    <MenuItem name="Managers" url="/managers" display={false} />
                    <MenuItem name="Managers" url="/managers" display={false}/>
                    <MenuItem name="SignOut" url="#" red="true" style={SecondRow} onClick={handleSignOut}/>
                    <MenuItem name="Doctors" url="/doctors" style={SecondRow} />
                    <MenuItem name="Receptionists" url="/receptionists" display={false} style={SecondRow} />
                    <MenuItem name="Products" url="/products" style={SecondRow} />
                    <MenuItem name="My Info" url="#" style={SecondRow} onClick={(e) => {
                            props.onOpenProfile(e, <ViewClientForm onClose={props.onClose} type={"self"} user_id={userData.userId}/>)
                    }}/>
                </div>
            </div>
        )
    }

    else if(cookies.user.role.id==4){
        return(
            <div style={MenuOuterContainer}>
                <div style={MenuInnerContainer}>
                    <MenuItem name="Clients" url="/clients" style={FirstItem}/>
                    <MenuItem name="Pets" url="/pets" />
                    <MenuItem name="Shops" url="/shops" display={false}/>
                    <MenuItem name="Managers" url="/managers" display={false} />
                    <MenuItem name="Managers" url="/managers" display={false}/>
                    <MenuItem name="SignOut" url="#" red="true" style={SecondRow} onClick={handleSignOut}/>
                    <MenuItem name="Doctors" url="/doctors" style={SecondRow} />
                    <MenuItem name="Receptionists" url="/receptionists" display={false} style={SecondRow} />
                    <MenuItem name="Products" url="/products" style={SecondRow} />
                    <MenuItem name="My Info" url="#" style={SecondRow} onClick={(e) => {
                            props.onOpenProfile(e, <ViewReceptionistForm onClose={props.onClose} type={"self"} user_id={userData.userId}/>)
                    }}/>
                </div>
            </div>
        )
    }

    else if(cookies.user.role.id==3){
        return(
            <div style={MenuOuterContainer}>
                <div style={MenuInnerContainer}>
                    <MenuItem name="Clients" url="/clients" style={FirstItem}/>
                    <MenuItem name="Pets" url="/pets" />
                    <MenuItem name="Shops" url="/shops" display={false}/>
                    <MenuItem name="Managers" url="/managers" display={false} />
                    <MenuItem name="Managers" url="/managers" display={false}/>
                    <MenuItem name="SignOut" url="#" red="true" style={SecondRow} onClick={handleSignOut}/>
                    <MenuItem name="Doctors" url="/doctors" style={SecondRow} display={false} />
                    <MenuItem name="Receptionists" url="/receptionists" display={false} style={SecondRow} />
                    <MenuItem name="Products" url="/products" style={SecondRow} />
                    <MenuItem name="My Info" url="#" style={SecondRow} onClick={(e) => {
                            props.onOpenProfile(e, <ViewReceptionistForm onClose={props.onClose} type={"self"} user_id={userData.userId}/>)
                    }}/>
                </div>
            </div>
        )
    }

    else if(cookies.user.role.id==2){
        return(
            <div style={MenuOuterContainer}>
                <div style={MenuInnerContainer}>
                    <MenuItem name="Clients" url="/clients" style={FirstItem}/>
                    <MenuItem name="Pets" url="/pets" />
                    <MenuItem name="Shops" url="/shops" display={false}/>
                    <MenuItem name="Managers" url="/managers" display={false} />
                    <MenuItem name="Managers" url="/managers" display={false}/>
                    <MenuItem name="SignOut" url="#" red="true" style={SecondRow} onClick={handleSignOut}/>
                    <MenuItem name="Doctors" url="/doctors" style={SecondRow} />
                    <MenuItem name="Receptionists" url="/receptionists" style={SecondRow} />
                    <MenuItem name="Products" url="/products" style={SecondRow} />
                    <MenuItem name="My Info" url="#" style={SecondRow} onClick={(e) => {
                            props.onOpenProfile(e, <ViewReceptionistForm onClose={props.onClose} type={"self"} user_id={userData.userId}/>)
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
    "width": "850px",
    "height": "fit-content",
    "vertical-align": "middle",
    "margin-left": "150px"
}

const SecondRow = {
    "transform": "translate(-52px, -31px)",
}

const FirstItem = {
    "margin-left": "0"
}