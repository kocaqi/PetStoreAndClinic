import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { SingleClient } from './Singleclient';

export function ClientsListing(props) {


      
      return (
        <div style={container1}>
            <div style={container2}>
                <table style={table}>
                <tbody>
                    
                    {props.data.map((user, index) => {
                        return <SingleClient key={index} custom_key={index} onOpenUserForm={props.onOpenUserForm} user_data={user}/>;
                    })}
                
                </tbody>
                </table>
            </div>
        </div>
      );
}

const container1 = {
    "width": "95%",
    "height": "90vh",
    "overflow-y": "hidden",
    "overflow-x": "hidden",
    "margin": "auto",
}

const container2 = {
    "width": "100%",
    "height": "90vh",
    "overflow-y": "scroll",
    "overflow-x": "hidden",
    "padding-right": "22px",
}

const table = {
    "width": "100%",
    "height": "90vh",
}
