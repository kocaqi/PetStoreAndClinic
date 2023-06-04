
import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { useEffect, useState, useRef } from "react"

import { useFormik } from "formik";
import { getFeedbackData } from './getFeedbackData';

import { HoverButton } from '../../../commons';


export function FeedbackForm(props) {


    const effectRan = useRef(false)
    const [cookies, setCookie] = useCookies();

    async function setUserData(feedback_id){

        const feedbackData = await getFeedbackData(feedback_id)
        
        Object.keys(formik.values).forEach(key => {
            formik.setFieldValue(key, feedbackData[key], false)
        })

        
    }


    useEffect(() => {

        if(!effectRan.current){

            setUserData(props.feedback_id)
        
            effectRan.current = true
        }
    }, [])

    
    const formik = useFormik({
        initialValues: {
            "author_id": "",
            "doctor_id": "",
            "title": "",
            "description": ""
        },
    });

      return (
        <div style={FormContainer}>
        <div>
        <HoverButton text="BACK" HoverStyle={{...ActionButtonHover, "background": "#962B14"}} DefaultStyle={{...ActionButton, "background": "#D13C1D"}} onClick={props.onClose} />
            <div>
            <form>
                <h6 style={BlockHeader}>Feedback Information</h6>
                <div style={FormBlock}>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel} >author_id</label>
                                {
                                    formik.values['author_id']==cookies.user.user_id
                                    ? <input type="text" placeholder="author_id" name="author_id" value={formik.values['author_id']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                    : <input type="text" placeholder="author_id" name="author_id" value={formik.values['author_id']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                }
                    
                            </div>
                        </div>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>doctor_id</label>
                                {
                                    formik.values['author_id']==cookies.user.user_id
                                    ? <input type="text" name="doctor_id"  placeholder={"doctor_id"} value={formik.values['doctor_id']} onChange={formik.handleChange} style={FormInput} readonly="true" />
                                    : <input type="text" name="doctor_id"  placeholder={"doctor_id"} value={formik.values['doctor_id']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                }
                            </div>
                        </div>
                    </div>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Title</label>
                                {
                                    formik.values['author_id']==cookies.user.user_id
                                    ? <input name="title" type="text" placeholder="Title" value={formik.values['title']} onChange={formik.handleChange} style={FormInput} />
                                    : <input name="title" type="text" placeholder="Title" value={formik.values['title']} onChange={formik.handleChange} style={FormInput} readonly="true"/>
                                }
                            </div>
                        </div>
                    </div>
                    <div style={FormRow}>
                        <div style={FormInputContainer}>
                            <div>
                                <label style={InputLabel}>Description</label>
                                {
                                    formik.values['author_id']==cookies.user.user_id
                                    ? <textarea name="description" rows="4" placeholder="A few words about your experince ..." style={FormTextArea} value ={formik.values['description']} onChange={formik.handleChange}></textarea>
                                    : <textarea name="description" rows="4" placeholder="A few words about your experince ..." style={FormTextArea} value ={formik.values['description']} onChange={formik.handleChange} readonly="true"></textarea>
                                }
                            </div>
                        </div>
                    </div>
                </div>
                <hr />
               
                {
                    formik.values['author_id']==cookies.user.user_id
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


