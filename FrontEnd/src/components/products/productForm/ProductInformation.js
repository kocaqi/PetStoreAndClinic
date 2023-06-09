
import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { useEffect, useState, useRef } from "react"

import { useFormik } from "formik";

import { getUserData } from '../js/getUserData';
import { HoverButton } from '../../commons';
import { UpdateUser } from '../js/UpdateUser';


export function ProductInformation(props) {


    const effectRan = useRef(false)

    async function setUserData(user_id){

        const userData = (await getUserData(user_id))[0]
        
        
        Object.keys(formik.values).forEach(key => {

            formik.setFieldValue(key, userData[key], false)

        })
        

        
    }

    async function updateUser(){
        await UpdateUser(props.user_id, formik.values)

        props.onClose()
    }


    useEffect(() => {

        if(!effectRan.current){

            setUserData(props.user_id)
        
            effectRan.current = true
        }
    }, [])

    
    const formik = useFormik({
        initialValues: {
            "name": "",
            "pricePerUnit": "",
            "stock": "",
            "type": "",
            "id": ""
        },
    });

     return (
        <div style={FormContainer}>
        <div>
            <div>
            <form>
                <h6 style={BlockHeader}>Product information</h6>
                <div style={FormBlock}>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel} >Product ID</label>
                                {
                                    props.type=="view"
                                    ? <input type="text" placeholder="Product ID" name="id" value={formik.values['id']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                    : <input type="text" placeholder="Product ID" name="id" value={formik.values['id']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                }
                    
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Product Name</label>
                                {
                                    props.type=="view"
                                    ? <input type="text" name="name"  placeholder={"Product Name"} value={formik.values['name']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                    : <input type="text" name="name"  placeholder={"Product Name"} value={formik.values['name']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Type</label>
                                {
                                    props.type=="view"
                                    ? <input type="text" name="type" placeholder="Medical or Non-Medical" value={formik.values['type']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input type="text" name="type" placeholder="Medical or Non-Medical" value={formik.values['type']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                    </div>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Price Per Unit</label>
                                {
                                    props.type=="view"
                                    ? <input name="pricePerUnit" type="text" placeholder="Price Per Unit" value={formik.values['pricePerUnit']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="pricePerUnit" type="text" placeholder="Price Per Unit" value={formik.values['pricePerUnit']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Stock</label>
                                {
                                    props.type=="view"
                                    ? <input name="stock" type="text" placeholder="Stock" value={formik.values['stock']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="stock" type="text" placeholder="Stock" value={formik.values['stock']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                    </div>
                </div>
                
                {
                    props.type=="edit" || props.type=="self"
                    ? (<div style={FormBlock}>
                            <div style={SaveButtonContainer}>
                                <HoverButton text="SAVE" HoverStyle={SaveButtonHover} DefaultStyle={SaveButton} onClick={updateUser}/>
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


