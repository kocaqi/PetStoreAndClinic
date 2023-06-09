import { useState } from "react"




export function generatePassword(length) {

    const characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

    let result = '';
    const charactersLength = characters.length;
    for ( let i = 0; i < length; i++ ) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }

    return result;
}


export const HoverButton = (props) => {

    const [hover, setHover] = useState(false);

    function MouseOver(event) {
        setHover(true)
    }
    function MouseOut(event){
        setHover(false)
    }

    return(
        <button type={props.type ? props.type : "button"} style={hover ? {...props.DefaultStyle, ...props.HoverStyle} : props.DefaultStyle} onMouseOver={MouseOver} onMouseOut={MouseOut} disabled={props.disabled} onClick={props.onClick}>{props.text}</button>
    )
}


export const Container = ({children}) => {

    return (
        <div style={ContainerStyle}>
            {children}
        </div>
    )

}

export const ConfirmationPage = (props) => {
    const ConfirmContainer={
        "width": "25%",
        "margin": "50px auto"
    }
    
    const TextContainer={
        "width": "100%",
        "margin-bottom": "20px"
    }

    const Text={
        "color": "#2d3b55",
        "font-family": "Courier, sans-serif",
        "font-size": "18px",
        "font-weight": "bold"
    }

    const ButtonOuterContainer={
        "display": "flex",
        "width": "50%",
        "margin": "auto" 
    }

    const ButtonContainer={
        "display": "inline-block",
        "flex": "1",
    }

    const ActionButton = {
        "border": "none",
        "padding": "15px",
        "padding-top": "8px",
        "padding-bottom": "8px",
        "font-family": "'Rubik', sans-serif",
        "font-weight": "550",
        "font-size": "13px",
        "cursor": "pointer",
        "text-transform": "uppercase",
        "background": "#2d3b55",
        "color": "#fff",
        "border-bottom-left-radius": "4px",
        "border-bottom-right-radius": "4px",
        "letter-spacing": "0.2px",
        "outline": "0",
        "-webkit-transition": "all .3s",
        "transition": "all .3s",
      }
      
      const ActionButtonHover = {
        
        "background": "#3c4d6d",
      
      }

    return (
        <div style={ConfirmContainer}>
            <div style={TextContainer}>
                <div style={{"width": "fit-content","margin": "auto"}}>
                    <span style={Text}>{!props.text ? "Are You Sure?" : props.text}</span>
                </div>
            </div>
            <div style={ButtonOuterContainer}>
                <div style={ButtonContainer}>
                    <div style={{"width": "fit-content","margin": "auto"}}>
                        <HoverButton text="Yes" HoverStyle={ActionButtonHover} DefaultStyle={ActionButton} onClick={props.onAccept}/>   
                    </div>
                </div>
                <div style={ButtonContainer}>
                    <div style={{"width": "fit-content","margin": "auto"}}>
                        <HoverButton text="No" HoverStyle={{...ActionButtonHover, "background": "#962B14"}} DefaultStyle={{...ActionButton, "background": "#D13C1D"}} onClick={props.onDecline}/>
                    </div>
                </div>
            </div>
        </div>
    )

}



const ContainerStyle = {
    "margin": "auto",
    "height": "100vh",
    "width": "80%",
    "box-shadow": "0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)"
}