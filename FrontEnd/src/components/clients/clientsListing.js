import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { SingleClient } from './Singleclient';

export function ClientsListing() {


      
      return (
        <div style={container1}>
            <div style={container2}>
                <table style={table}>
                <thead style={headers}>
                    <tr>
                        <td style={mainheader}>Candidate Name</td>
                        <td style={header}>Status</td>
                        <td style={header}>Action</td>
                    </tr>
                </thead>
                <tbody>
                    
                    {[1, 2, 3,4,5,6,7,8,9,10,11,12].map(i => {
                        return <SingleClient key={i}/>;
                    })}
                
                </tbody>
                </table>
            </div>
        </div>
      );
}

const container1 = {
    "width": "1250px",
    "height": "80vh",
    "overflow-y": "hidden",
    "overflow-x": "hidden",
    "margin": "auto",
}

const container2 = {
    "width": "1250px",
    "height": "80vh",
    "overflow-y": "scroll",
    "overflow-x": "hidden",
    "padding-right": "20px",
}

const table = {
    "width": "1250px",
    "height": "80vh",
}

const mainheader = {
}

const header = {
    "text-align": "center",
}

const headers = {
    "height": "40px",
    "border": "1px solid #eeeeee"
}