
import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { useEffect, useState, useRef } from "react"

import { useFormik } from "formik";

import { getUserData } from '../js/getUserData';
import { HoverButton } from '../../commons';
import { UpdateUser } from '../js/UpdateUser';


export function ClientInformation(props) {


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
            "password": null,
            "email": "",
            "firstName": "",
            "lastName": "",
            "address": "",
            "city": "",
            "country": "",
            "phone": "",
            "about": "",
            "occupation": ""
        },
    });

      return (
        <div style={FormContainer}>
        <div>
            <div>
            <form>
                <h6 style={BlockHeader}>User information</h6>
                <div style={FormBlock}>
                    <div style={FormRow}>
                        
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Email address</label>
                                {
                                    props.type=="view"
                                    ? <input type="text" name="email"  placeholder={formik.values['First Name']+"@example.com"} value={formik.values['email']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                    : <input type="text" name="email"  placeholder={formik.values['First Name']+"@example.com"} value={formik.values['email']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Occupation</label>
                                {
                                    props.type=="view"
                                    ? <input type="text" name="occupation" placeholder="Occupation" value={formik.values['occupation']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input type="text" name="occupation" placeholder="Occupation" value={formik.values['occupation']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                        {props.type=="self" ?
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Password</label>
                                {
                                    props.type=="view"
                                    ? <input type="text" name="password" placeholder="Password" value={formik.values['password'] ? formik.values['password'] : ""} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input type="text" name="password" placeholder="Password" value={formik.values['password'] ? formik.values['password'] : ""} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                        : ""}
                    </div>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>First name</label>
                                {
                                    props.type=="view"
                                    ? <input name="firstName" type="text" placeholder="First name" value={formik.values['firstName']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="firstName" type="text" placeholder="First name" value={formik.values['firstName']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Last name</label>
                                {
                                    props.type=="view"
                                    ? <input name="lastName" type="text" placeholder="Last name" value={formik.values['lastName']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="lastName" type="text" placeholder="Last name" value={formik.values['lastName']} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                    </div>
                </div>
                <hr />
                <h6 style={BlockHeader}>Contact information</h6>
                <div style={FormBlock}>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Address</label>
                                {
                                    props.type=="view"
                                    ? <input name="address" placeholder="Home Address" value={formik.values.address} onChange={formik.handleChange} type="text" style={FormInput} readonly="true"/>
                                    : <input name="address" placeholder="Home Address" value={formik.values.address} onChange={formik.handleChange} type="text" style={FormInput} />
                                }
                            </div>
                        </div>
                    </div>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>City</label>
                                {
                                    props.type=="view"
                                    ? <input name="city" type="text" placeholder="City" value={formik.values.city} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="city" type="text" placeholder="City" value={formik.values.city} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Country</label>
                                {
                                    props.type=="view"
                                    ? <input name="country" type="text" placeholder="Country" value={formik.values.country} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="country" type="text" placeholder="Country" value={formik.values.country} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Phone</label>
                                {
                                    props.type=="view"
                                    ? <input name="phone" type="text" placeholder="Phone" value={formik.values.phone} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                    : <input name="phone" type="text" placeholder="Phone" value={formik.values.phone} onChange={formik.handleChange} style={FormInput} />
                                }
                            </div>
                        </div>
                    </div>
                </div>
                <hr />
                <h6 style={BlockHeader}>About me</h6>
                <div style={FormBlock}>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <label style={InputLabel}>About Me</label>
                            {
                                props.type=="view"
                                ? <textarea name="about" rows="4" placeholder="A few words about you ..." style={FormTextArea} value ={formik.values['about']} onChange={formik.handleChange} readonly="true"></textarea>
                                : <textarea name="about" rows="4" placeholder="A few words about you ..." style={FormTextArea} value ={formik.values['about']} onChange={formik.handleChange} ></textarea>
                            }
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


