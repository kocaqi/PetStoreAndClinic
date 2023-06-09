import { useState } from "react"

export function MenuItem(props) {

    const [hover, setHover] = useState(false);

    function MouseOver(event) {
        setHover(true)
    }
    function MouseOut(event){
        setHover(false)
    }

    return (
            <div style={hover ? {...MenuItemContainer, ...HoverMenuItemContainer, ...props.style} : {...MenuItemContainer, ...props.style}} onMouseOver={MouseOver} onMouseOut={MouseOut} onClick = {props.onClick ? props.onClick : ()=>{}}>
                
                <a  style={props.display!==false ? ContentContainer :{...ContentContainer, ...{"visibility":"hidden"}}} href={props.url} >
                    
                    <span style={InnerContentContainer}>

                        <span style={Icon}></span>
                        <span style={Title}>{props.name}</span>

                    </span>

                    <svg viewBox="0 0 173.20508075688772 200" height="200" width="174" version="1.1" xmlns="http://www.w3.org/2000/svg" style={hover ? {...Hex, ...HoverHex} : Hex}><path d="M86.60254037844386 0L173.20508075688772 50L173.20508075688772 150L86.60254037844386 200L0 150L0 50Z" fill={props.red ? "#D13C1D" : "#2d3b55"}></path></svg>
                    <svg viewBox="0 0 173.20508075688772 200" height="200" width="174" version="1.1" xmlns="http://www.w3.org/2000/svg" style={SecondHex}><path d="M86.60254037844386 0L173.20508075688772 50L173.20508075688772 150L86.60254037844386 200L0 150L0 50Z" fill="#fff"></path></svg>
                    <svg viewBox="0 0 173.20508075688772 200" height="200" width="174" version="1.1" xmlns="http://www.w3.org/2000/svg" style={ThirdHex}><path d="M86.60254037844386 0L173.20508075688772 50L173.20508075688772 150L86.60254037844386 200L0 150L0 50Z" fill={props.red ? "#D13C1D" : "#2d3b55"}></path></svg>

                </a> 

            </div>
    );

}


const MenuItemContainer = {
    "width": "193px",
    "height": "173.20508px",
    "float": "left",
    "margin-left": "-29px",
    "z-index": 0,
    "position": "relative",
}

const HoverMenuItemContainer = {
    "z-index": 1,
}

const ContentContainer = {
    "cursor": "pointer",
    "color": "#fff",
    "display": "block",
    "height": "180px",
    "margin": "0 auto",
    "position": "relative",
    "text-align": "center",
    "width": "156px",
}

const InnerContentContainer = {
    "left": "50%",
    "margin": "-3px 0 0 2px",
    "position": "absolute",
    "top": "50%",
    "-webkit-transform": "translate(-50%, -50%)",
    "-moz-transform": "translate(-50%, -50%)",
    "-ms-transform": "translate(-50%, -50%)",
    "-o-transform": "translate(-50%, -50%)",
    "transform": "translate(-50%, -50%)",
}

const Icon = {
    "display": "block",
    "font-size": "36px",
    "line-height": "30px",
    "margin-bottom": "11px",
}

const Title = {
    "display": "block",
    "font-family": '"Open Sans", sans-serif',
    "font-size": "14px",
    "letter-spacing": "1px",
    "line-height": "24px",
    "text-transform": "uppercase",
}

const Hex = {
    "left": "-7px",
    "position": "absolute",
    "top": "-13px",
    "transform": "scale(0.87)",
    "z-index": "-1",
    "-webkit-transition": "all 0.3s cubic-bezier(0.165, 0.84, 0.44, 1) 0s",
    "-moz-transition": "all 0.3s cubic-bezier(0.165, 0.84, 0.44, 1) 0s",
    "-o-transition": "all 0.3s cubic-bezier(0.165, 0.84, 0.44, 1) 0s",
    "transition": "all 0.3s cubic-bezier(0.165, 0.84, 0.44, 1) 0s",
}

const HoverHex = {
    "transform": "scale(0.92)",
}


const SecondHex = {
    "left": "-7px",
    "position": "absolute",
    "top": "-13px",
    "transform": "scale(0.91)",
    "z-index": "-2",
}

const ThirdHex = {
    "left": "-7px",
    "position": "absolute",
    "top": "-13px",
    "transform": "scale(0.97)",
    "z-index": "-3",
}


