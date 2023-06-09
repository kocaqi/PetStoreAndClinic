import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"

import { getFeedbackList } from './getAppointmentsList';
import { SingleAppointment } from './SingleAppointment';


export function AppointmentListing(props) {

    const [AppointmentList, setAppointmentListState] = useState([]);

    const effectRan = useRef(false)

    async function setFeedbackList(){

        setAppointmentListState(await getFeedbackList(props.user_id))
    }


    useEffect(() => {

        if(!effectRan.current){

            setFeedbackList() 
        
            effectRan.current = true
        }
    }, [])
      
      return (
        <div style={container1}>
            
            <div style={container2}>
                <table style={table}>
                <tbody>
                    
                    {AppointmentList.map((feedback, index) => {
                        return <SingleAppointment key={index} feedback_data={feedback} type={props.type} refresh={setFeedbackList} onClose={props.onClose} onPageChange={props.onPageChange}/>;
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
