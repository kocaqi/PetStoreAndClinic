import { useEffect, useState, useRef } from "react"


import { PetListing } from './pets/petListing';
import { HoverButton } from '../../commons';
import { AddPetForm } from "./pets/addPetForm";

function MainPage(props){

    return (
        <div style={Container}>

            <PetListing type={props.props.type} onPageChange={props.onPageChange} onClose={props.onClose}/>

            {props.props.type=="edit" || props.props.type=="self"
            ? <div style={actionButtonContainer}>
              <HoverButton text="ADD" HoverStyle={ActionButtonHover} DefaultStyle={ActionButton} onClick={(e) => props.onPageChange(e, <AddPetForm onClose={props.onClose} user_id={props.props.user_id}/>)}/>
            </div>
            : ""}

        </div>
  );

}

export function PetInformation(props) {

    const effectRan = useRef(false)
    const [currentPage, setCurrentpage] = useState(<MainPage props={props} onPageChange={onPageChange} onClose={setMainPage}/>);

    var onPageChange = (e, component) => {
        setCurrentpage(component)
    }


    function setMainPage(){
        setCurrentpage(<MainPage props={props} onPageChange={onPageChange} onClose={setMainPage}/>)
    }

    

    useEffect(() => {

        if(!effectRan.current){

            setMainPage() 
        
            effectRan.current = true
        }
    })
    

    return (currentPage)
}


const Container = {
    "height": "80%"
}

const actionButtonContainer = {
    "display": "inline-block",
    "margin": "20px",
    "margin-left": "20px",
    "vertical-align": "middle",

}

const ActionButton = {
    "border": "none",
    "padding": "18px",
    "padding-top": "10px",
    "padding-bottom": "10px",
    "font-family": "'Rubik', sans-serif",
    "cursor": "pointer",
    "text-transform": "uppercase",
    "font-weight": "550",
    "background": "#2d3b55",
    "color": "#fff",
    "border-bottom-left-radius": "4px",
    "border-bottom-right-radius": "0",
    "letter-spacing": "0.2px",
    "outline": "0",
    "-webkit-transition": "all .3s",
    "transition": "all .3s",
}

const ActionButtonHover = {
    
    "background": "#3c4d6d",

}
