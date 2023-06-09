import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useFormik } from "formik";


import { HoverButton } from '../commons';

export function ProductsSearchBar(props) {
    const [cookies, setCookie] = useCookies();

    const formik = useFormik({
        initialValues: {
            "name": "",
            "id": ""
        },
    });


      return (
            <div style={outerContainer}>
                  <div style={actionButtonContainer}>
                  <HoverButton text="BACK" HoverStyle={{...ActionButtonHover, "background": "#962B14"}} DefaultStyle={{...ActionButton, "background": "#D13C1D"}} onClick={() => window.location.href="/"} />
                  {cookies.user.role.id==1 || cookies.user.role.id==2 ? <HoverButton text="ADD" HoverStyle={ActionButtonHover} DefaultStyle={ActionButton} onClick={props.onAddClick} /> : ""}
                  </div>
                  <div style={searchNavContainer}>
                    <input placeholder='Name' style={Input} name="name" value={formik.values.lastName} onChange={formik.handleChange} />
                    <input placeholder='User ID' style={Input} name="id" value={formik.values.id} onChange={formik.handleChange} />
                    <HoverButton text="SEARCH" HoverStyle={SearchButtonHover} DefaultStyle={SearchButton} onClick={(e) => props.onSearch(e, formik.values)}/>
                  </div>
            
            </div>
      );
}


const outerContainer = {
    "width": "90%",
    "margin": "auto",
    "height": "fit-content",
    "padding": "20px",
    "borderBottom": "1px solid #2d3b55",
    
}

const actionButtonContainer = {
    "display": "inline-block",
    "margin-left": "20px",
    "vertical-align": "middle",

}

const searchNavContainer = {
    "display": "inline-block",
    "float": "right",
    "margin-right": "20px",
    "vertical-align": "middle",
}


const ActionButton = {
    "border": "none",
    "padding": "18px",
    "padding-top": "10px",
    "padding-bottom": "10px",
    "margin-right": "15px",
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



const SearchButton = {
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
    "border-bottom-right-radius": "4px",
    "letter-spacing": "0.2px",
    "outline": "0",
    "-webkit-transition": "all .3s",
    "transition": "all .3s",
}

const SearchButtonHover = {
    
    "background": "#3c4d6d",

}

const Input = {
    "border": 0,
    "margin-right": "20px",
    "padding": "5px 1px",
    "borderBottom": "1px solid #747474",
}

