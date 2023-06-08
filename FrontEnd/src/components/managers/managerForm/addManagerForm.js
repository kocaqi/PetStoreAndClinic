
import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { useEffect, useState, useRef } from "react"

import { useFormik } from "formik";

import { getUserData } from '../js/getUserData';
import { HoverButton } from '../../commons';
import { generatePassword } from '../../commons';
import { PostUser } from '../js/PostUser';


export function AddManagerForm(props) {

    async function addUser(){

        await PostUser(formik.values)

        props.onClose()
        //await props.refresh()
    }
    
    const formik = useFormik({
        initialValues: {
            username: "",
            "password": generatePassword(7),
            "email": "",
            "firstName": "",
            "lastName": "",
            "address": "",
            "city": "",
            "country": "",
            "phone": "",
            "aboutMe": "",
            "shopId": ""
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

                                        <input type="text" placeholder="Username" name="username" value={formik.values['username']} onChange={formik.handleChange} style={FormInput} />
                            
                                    </div>
                                </div>
                                <div style={FormInputContainer}>
                                    <div>
                                        <label style={InputLabel}>Email address</label>

                                        <input type="text" name="email"  placeholder={"name@example.com"} value={formik.values['email']} onChange={formik.handleChange} style={FormInput} />
                                        
                                    </div>
                                </div>
                                <div style={FormInputContainer}>
                                    <div>
                                        <label style={InputLabel}>Password</label>

                                        <input type="text" name="password"  placeholder="Password" value={formik.values['password']} onChange={formik.handleChange} style={FormInput} />
                                        
                                    </div>
                                </div>
                                
                            </div>
                            
                            <div style={FormRow}>
                            <div style={FormInputContainer}>
                                    <div>
                                        <label style={InputLabel}>Shop ID</label>
                                        
                                        <input name="shopId" type="text" placeholder="Shop ID" value={formik.values['shopId']} onChange={formik.handleChange} style={FormInput} />
                                        
                                    </div>
                                </div>
                                <div style={FormInputContainer}>
                                    <div>
                                        <label style={InputLabel}>First name</label>

                                        <input name="firstName" type="text" placeholder="First name" value={formik.values['firstName']} onChange={formik.handleChange} style={FormInput} />
                            
                                    </div>
                                </div>
                                <div style={FormInputContainer}>
                                    <div>
                                        <label style={InputLabel}>Last name</label>
                                        
                                        <input name="lastName" type="text" placeholder="Last name" value={formik.values['lastName']} onChange={formik.handleChange} style={FormInput} />
                                        
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

                                        <input name="address" placeholder="Home Address" value={formik.values.address} onChange={formik.handleChange} type="text" style={FormInput} />
                                        
                                    </div>
                                </div>
                            </div>
                            <div style={FormRow}>
                                <div style={FormInputContainer}>
                                    <div>
                                        <label style={InputLabel}>City</label>

                                        <input name="city" type="text" placeholder="City" value={formik.values.city} onChange={formik.handleChange} style={FormInput} />
                                        
                                    </div>
                                </div>
                                <div style={FormInputContainer}>
                                    <div>
                                        <label style={InputLabel}>Country</label>

                                        <input name="country" type="text" placeholder="Country" value={formik.values.country} onChange={formik.handleChange} style={FormInput} />
                                        
                                    </div>
                                </div>
                                <div style={FormInputContainer}>
                                    <div>
                                        <label style={InputLabel}>Phone</label>

                                        <input name="phone" type="text" placeholder="Phone" value={formik.values.phone} onChange={formik.handleChange} style={FormInput} />
                                        
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

                                    <textarea name="aboutMe" rows="4" placeholder="A few words about you ..." style={FormTextArea} value ={formik.values['aboutMe']} onChange={formik.handleChange} ></textarea>
                                    
                                </div>
                            </div>
                        </div>
                        <div style={FormBlock}>
                            <div style={SaveButtonContainer}>
                                <HoverButton text="SAVE" HoverStyle={SaveButtonHover} DefaultStyle={SaveButton} onClick={addUser}/>
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
    "padding-top": "0px",
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


