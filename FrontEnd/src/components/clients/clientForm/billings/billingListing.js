import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"

import { getBillingList } from './getBillingList';
import { SingleBill } from './SingleBill';


export function BillingListing(props) {

    const [BillingList, setBillingListState] = useState({"Billing History": []});

    const effectRan = useRef(false)

    async function setBillingList(){

        setBillingListState(await getBillingList(props.user_id))
    }


    useEffect(() => {

        if(!effectRan.current){

            setBillingList() 
        
            effectRan.current = true
        }
    }, [])
      
      return (
        <div style={container1}>
            
            <div style={Balance}>{BillingList.Balance ? `Balance: $${BillingList.Balance}` : ""}</div>

            <div style={container2}>
                <table style={table}>
                <tbody>
                    
                    {BillingList["Billing History"].map((bill, index) => {
                        return <SingleBill key={index} bill_data={bill} type={props.type} refresh={setBillingList} onClose={props.onClose} onPageChange={props.onPageChange}/>;
                    })}
                
                </tbody>
                </table>
            </div>
            
        </div>
      );
}

const container1 = {
    "width": "95%",
    "height": "100%",
    "overflow-y": "hidden",
    "overflow-x": "hidden",
    "margin": "auto",
}

const container2 = {
    "width": "100%",
    "height": "100%",
    "overflow-y": "scroll",
    "overflow-x": "hidden",
    "padding-right": "22px",
}

const table = {
    "width": "100%",
    "height": "100%"
}

const Balance = {
    "display": "block",
    "width": "fit-content",
    "margin": "10px auto",
    "font-size": "18px",
    "font-weight": "bold",
    "color": "white",
    "background": "#2d3b55",
    "padding": "5px",
    "border-radius": "10%"
}
