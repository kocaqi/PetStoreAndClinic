
import { useCookies } from 'react-cookie';

import { useEffect, useState, useRef } from "react"

import { useFormik } from "formik";

import { HoverButton } from '../../../commons';
import { PostPet } from './PostPet';


export function AddPetForm(props) {


    const [cookies, setCookie] = useCookies();

    async function addUser(){

        await PostPet(formik.values)

        props.onClose()
        //await props.refresh()
    }

    
    const formik = useFormik({
        initialValues: {
            "name": "",
            "clientId": props.user_id,
            "species": "",
            "breed": "",
            "gender": "",
            "dateOfBirth": "",
            "colour": ""
        },
    });

      return (
        <div style={FormContainer}>
        <div>
        <HoverButton text="BACK" HoverStyle={{...ActionButtonHover, "background": "#962B14"}} DefaultStyle={{...ActionButton, "background": "#D13C1D"}} onClick={props.onClose} />
            <div>
            <form>
                <h6 style={BlockHeader}>Pet Information</h6>
                <div style={FormBlock}>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel} >Pet Name</label>
                                <input type="text" placeholder="Pet Name" name="name" value={formik.values['name']} onChange={formik.handleChange} style={FormInput} />
                                
                    
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Client ID</label>
                                <input type="text" name="clientId"  placeholder={"Client ID"} value={formik.values['clientId']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                
                            </div>
                        </div>
                    </div>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Species</label>
                                <input type="text" name="species"  placeholder={"Species"} value={formik.values['species']} onChange={formik.handleChange} style={FormInput} />
                                
                            </div>
                        </div>
                        
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Breed</label>
                                <input type="text" name="breed"  placeholder={"Breed"} value={formik.values['breed']} onChange={formik.handleChange} style={FormInput} />
                                
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Color</label>
                                <input type="text" name="colour"  placeholder={"Color"} value={formik.values['colour']} onChange={formik.handleChange} style={FormInput} />
                                
                            </div>
                        </div>
                    </div>
                </div>
                <hr />
                <div style={FormBlock}>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Gender</label>
                                <input name="gender" placeholder="Male or Female" value={formik.values.gender} onChange={formik.handleChange} type="text" style={FormInput} />
                                
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>DOB</label>
                                <input name="dateOfBirth" type="date" placeholder="DOB" value={formik.values.dateOfBirth} onChange={formik.handleChange} style={FormInput} />
                                
                            </div>
                        </div>
                    </div>
                    
                </div>
                
                <div style={FormBlock}>
                    <div style={SaveButtonContainer}>
                        <HoverButton text="SAVE" HoverStyle={SaveButtonHover} DefaultStyle={SaveButton} onClick={addUser} />
                    </div>
                </div>
                
            </form>
            </div>
        </div>
        </div>
      );
}


const FormContainer = {
    "width": '70%',
}

const FormBlock = {

}

const BlockHeader = {
    "color": "rgb(120, 120, 120)",
    "font-size": "12px",
    "text-transform": "uppercase",
}

const FormRow = {
    "display": "flex",
    "flex-wrap": "wrap",
}

const FormInputContainer = {
    "display": "inline-block",
    "margin": "10px",
    "flex": 1,
}


const InputLabel = {
    "display": "block",
    "margin-bottom": "10px",
    "color": "#2d3b55",
    "font-size": "14px",
    "font-weight": "bold"
}

const FormInput = {
    "display": "block",
    "width": "100%",
    "border-radius": "4px",
    "border": 0,
    "height": "25px",
    "box-shadow": "0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.19)",
    "padding": "5px 4px",
}

const FormTextArea = {
    "display": "block",
    "width": "100%",
    "border-radius": "4px",
    "height": "100px",
    "border": 0,
    "box-shadow": "0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 1px 5px 0 rgba(0, 0, 0, 0.19)",
    "padding": "5px 4px",
}

const ActionButton = {
    "border": "none",
    "padding": "10px",
    "padding-top": "5px",
    "padding-bottom": "5px",
    "font-family": "'Rubik', sans-serif",
    "cursor": "pointer",
    "text-transform": "uppercase",
    "font-weight": "550",
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

const SaveButton = {
    "border": "none",
    "padding": "20px",
    "padding-top": "10px",
    "padding-bottom": "10px",
    "margin": "auto",
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

const SaveButtonHover = {
    
    "background": "#3c4d6d",

}

const SaveButtonContainer = {
    "width": "fit-content",
    "margin": "auto",
    "margin-top": "20px",
}


