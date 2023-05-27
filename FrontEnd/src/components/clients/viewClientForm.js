import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";



export function ViewClientForm(props) {


      return (
            <div>
            <div style={Overlay} onClick={props.onClose}>
            </div>
            <div style={Container}><span>{props.type}</span></div>
            </div>
      );
}

const Overlay = {
    "position": 'fixed',
    "display": 'block',
    "width": '100%',
    "height": '100%',
    "top": 0,
    "left": 0,
    "right": 0,
    "bottom": 0,
    "background-color": "rgba(0,0,0,0.5)",
    "z-index": '4',
}

const Container = {
    "background-color": "white",
    "position": 'fixed',
    "height": "80%",
    "width": "70%",
    "z-index": '5',
    "margin-left": "15%",
    "margin-top": "5%",
}
