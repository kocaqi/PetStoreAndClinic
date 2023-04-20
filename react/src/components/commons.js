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
        <button style={hover ? {...props.DefaultStyle, ...props.HoverStyle} : props.DefaultStyle} onMouseOver={MouseOver} onMouseOut={MouseOut} disabled={props.disabled}>{props.text}</button>
    )
}

