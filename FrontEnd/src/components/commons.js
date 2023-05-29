import { useState } from "react"


export const HoverButton = (props) => {

    const [hover, setHover] = useState(false);

    function MouseOver(event) {
        setHover(true)
    }
    function MouseOut(event){
        setHover(false)
    }

    return(
        <button type="button" style={hover ? {...props.DefaultStyle, ...props.HoverStyle} : props.DefaultStyle} onMouseOver={MouseOver} onMouseOut={MouseOut} disabled={props.disabled} onClick={props.onClick}>{props.text}</button>
    )
}


export const Container = ({children}) => {

    return (
        <div style={ContainerStyle}>
            {children}
        </div>
    )

}



const ContainerStyle = {
    "margin": "auto",
    "height": "100vh",
    "width": "80%",
    "box-shadow": "0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)"
}