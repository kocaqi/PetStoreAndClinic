
import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { useEffect, useState, useRef } from "react"

import { useFormik } from "formik";
import { getPetData } from './getPetData';

import { HoverButton } from '../../../commons';


export function PetForm(props) {


    const effectRan = useRef(false)
    const [cookies, setCookie] = useCookies();

    async function setUserData(pet_id){

        const petData = await getPetData(pet_id)
        
        Object.keys(formik.values).forEach(key => {
            formik.setFieldValue(key, petData[key], false)
        })

        
    }


    useEffect(() => {

        if(!effectRan.current){

            setUserData(props.pet_id)
        
            effectRan.current = true
        }
    }, [])

    
    const formik = useFormik({
        initialValues: {
            "Pet Name": "",
            "Owner Name": "",
            "pet_id": "",
            "owner_id": "",
            "Type": "",
            "Breed": "",
            "Medical Info": ""
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
                                {
                                    props.type=="view"
                                    ? <input type="text" placeholder="Pet Name" name="Pet Name" value={formik.values['Pet Name']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                    : <input type="text" placeholder="Pet Name" name="Pet Name" value={formik.values['Pet Name']} onChange={formik.handleChange} style={FormInput} />
                                }
                    
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Owner Name</label>
                                {
                                    props.type=="view"
                                    ? <input type="text" name="Owner Name"  placeholder={"Owner Name"} value={formik.values['Owner Name']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                    : <input type="text" name="Owner Name"  placeholder={"Owner Name"} value={formik.values['Owner Name']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                    </div>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>pet_id</label>
                                {
                                    props.type=="view"
                                    ? <input name="pet_id" type="text" placeholder="pet_id" value={formik.values['pet_id']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="pet_id" type="text" placeholder="pet_id" value={formik.values['pet_id']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>owner_id</label>
                                {
                                    props.type=="view"
                                    ? <input name="owner_id" type="text" placeholder="owner_id" value={formik.values['owner_id']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="owner_id" type="text" placeholder="owner_id" value={formik.values['owner_id']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                    </div>
                </div>
                <hr />
                <div style={FormBlock}>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Type</label>
                                {
                                    props.type=="view"
                                    ? <input name="Type" placeholder="Type" value={formik.values.Type} onChange={formik.handleChange} type="text" style={FormInput} readonly="true"/>
                                    : <input name="Type" placeholder="Type" value={formik.values.Type} onChange={formik.handleChange} type="text" style={FormInput} />
                                }
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Breed</label>
                                {
                                    props.type=="view"
                                    ? <input name="Breed" type="text" placeholder="Breed" value={formik.values.Breed} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="Breed" type="text" placeholder="Breed" value={formik.values.Breed} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                    </div>
                    
                </div>
                <hr />
                <h6 style={BlockHeader}>Medical Information</h6>
                <div style={FormBlock}>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <label style={InputLabel}>Medical Info</label>
                            {
                                props.type=="edit" && cookies.user.role=="doctor"
                                ? <textarea name="Medical Info" rows="4" placeholder="A few words about you ..." style={FormTextArea} value ={formik.values['Medical Info']} onChange={formik.handleChange}></textarea>
                                : <textarea name="Medical Info" rows="4" placeholder="A few words about you ..." style={FormTextArea} value ={formik.values['Medical Info']} onChange={formik.handleChange} readonly="true"></textarea>
                            }
                        </div>
                    </div>
                </div>

                {
                    props.type=="edit"
                    ? (<div style={FormBlock}>
                            <div style={SaveButtonContainer}>
                                <HoverButton text="SAVE" HoverStyle={SaveButtonHover} DefaultStyle={SaveButton} />
                            </div>
                        </div>)
                    : ""
                }
                
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


