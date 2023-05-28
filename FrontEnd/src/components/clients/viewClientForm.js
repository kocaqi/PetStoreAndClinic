import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { useEffect, useState, useRef } from "react"

import { useFormik } from "formik";

import { getUserData } from './js/getUserData';



export function ViewClientForm(props) {


    const effectRan = useRef(false)

    async function setUserData(user_id){

        const userData = await getUserData(user_id)
        
        Object.keys(formik.values).forEach(key => {
            formik.setFieldValue(key, userData[key], false)
        })

        
    }


    useEffect(() => {

        if(!effectRan.current){

            setUserData(props.user_id)
        
            effectRan.current = true
        }
    }, [])

    
    const formik = useFormik({
        initialValues: {
            Username: "",
            "Email Address": "",
            "First Name": "",
            "Last Name": "",
            "Address": "",
            "City": "",
            "Country": "",
            "Phone": "",
            "About Me": "",
            "Occupation": ""
        },
    });


      return (
            <div>
                <div style={Overlay} onClick={props.onClose}>
                </div>
                <div style={Container}>
                <div style={FormContainer}>
                    <div>
                        <div>
                        <form>
                            <h6 style={BlockHeader}>User information</h6>
                            <div style={FormBlock}>
                                <div style={FormRow}>
                                    <div style={FormInputContainer}>
                                        <div>
                                            <label style={InputLabel} >Username</label>
                                            {
                                                props.type=="view"
                                                ? <input type="text" placeholder="Username" name="Username" value={formik.values['Username']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                                : <input type="text" placeholder="Username" name="Username" value={formik.values['Username']} onChange={formik.handleChange} style={FormInput} />
                                            }
                                
                                        </div>
                                    </div>
                                    <div style={FormInputContainer}>
                                        <div>
                                            <label style={InputLabel}>Email address</label>
                                            {
                                                props.type=="view"
                                                ? <input type="text" name="Email Address"  placeholder={formik.values['First Name']+"@example.com"} value={formik.values['Email Address']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                                : <input type="text" name="Email Address"  placeholder={formik.values['First Name']+"@example.com"} value={formik.values['Email Address']} onChange={formik.handleChange} style={FormInput} />
                                            }
                                        </div>
                                    </div>
                                    <div style={FormInputContainer}>
                                        <div>
                                            <label style={InputLabel}>Occupation</label>
                                            {
                                                props.type=="view"
                                                ? <input type="text" name="Occupation" placeholder="Occupation" value={formik.values['Occupation']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                                : <input type="text" name="Occupation" placeholder="Occupation" value={formik.values['Occupation']} onChange={formik.handleChange} style={FormInput} />
                                            }
                                        </div>
                                    </div>
                                </div>
                                <div style={FormRow}>
                                    <div style={FormInputContainer}>
                                        <div>
                                            <label style={InputLabel}>First name</label>
                                            {
                                                props.type=="view"
                                                ? <input name="First Name" type="text" placeholder="First name" value={formik.values['First Name']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                                : <input name="First Name" type="text" placeholder="First name" value={formik.values['First Name']} onChange={formik.handleChange} style={FormInput} />
                                            }
                                        </div>
                                    </div>
                                    <div style={FormInputContainer}>
                                        <div>
                                            <label style={InputLabel}>Last name</label>
                                            {
                                                props.type=="view"
                                                ? <input name="Last Name" type="text" placeholder="Last name" value={formik.values['Last Name']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                                : <input name="Last Name" type="text" placeholder="Last name" value={formik.values['Last Name']} onChange={formik.handleChange} style={FormInput} />
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
                                                ? <input name="Address" placeholder="Home Address" value={formik.values.Address} onChange={formik.handleChange} type="text" style={FormInput} readonly="true"/>
                                                : <input name="Address" placeholder="Home Address" value={formik.values.Address} onChange={formik.handleChange} type="text" style={FormInput} />
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
                                                ? <input name="City" type="text" placeholder="City" value={formik.values.City} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                                : <input name="City" type="text" placeholder="City" value={formik.values.City} onChange={formik.handleChange} style={FormInput} />
                                            }
                                        </div>
                                    </div>
                                    <div style={FormInputContainer}>
                                        <div>
                                            <label style={InputLabel}>Country</label>
                                            {
                                                props.type=="view"
                                                ? <input name="Country" type="text" placeholder="Country" value={formik.values.Country} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                                : <input name="Country" type="text" placeholder="Country" value={formik.values.Country} onChange={formik.handleChange} style={FormInput} />
                                            }
                                        </div>
                                    </div>
                                    <div style={FormInputContainer}>
                                        <div>
                                            <label style={InputLabel}>Phone</label>
                                            {
                                                props.type=="view"
                                                ? <input name="Phone" type="text" placeholder="Phone" value={formik.values.Phone} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                                : <input name="Phone" type="text" placeholder="Phone" value={formik.values.Phone} onChange={formik.handleChange} style={FormInput} />
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
                                            ? <textarea name="About Me" rows="4" placeholder="A few words about you ..." style={FormTextArea} value ={formik.values['About Me']} onChange={formik.handleChange} readonly="true"></textarea>
                                            : <textarea name="About Me" rows="4" placeholder="A few words about you ..." style={FormTextArea} value ={formik.values['About Me']} onChange={formik.handleChange} readonly="true"></textarea>
                                        }
                                    </div>
                                </div>
                            </div>
                        </form>
                        </div>
                    </div>
                    </div>
                </div>
                
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
    "border-radius": "10px",
    "padding": "30px",
    "font-family": "Open Sans, sans-serif",
    "overflow": "auto"
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
