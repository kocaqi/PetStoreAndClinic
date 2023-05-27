import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { SingleClient } from './Singleclient';

export function ClientsListing(props) {


      
      return (
        <div style={container1}>
            <div style={container2}>
                <table style={table}>
                <tbody>
                    
                    {[1, 2, 3,4,5,6,7,8,9,10,11,12].map(i => {
                        return <SingleClient key={i} onOpenUserForm={props.onOpenUserForm}/>;
                    })}
                
                </tbody>
                </table>
            </div>
        </div>
      );
}

const container1 = {
    "width": "95%",
    "height": "80vh",
    "overflow-y": "hidden",
    "overflow-x": "hidden",
    "margin": "auto",
}

const container2 = {
    "width": "100%",
    "height": "80vh",
    "overflow-y": "scroll",
    "overflow-x": "hidden",
    "padding-right": "22px",
}

const table = {
    "width": "100%",
    "height": "80vh",
}
