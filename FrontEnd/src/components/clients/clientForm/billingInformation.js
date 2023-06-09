import { useEffect, useState, useRef } from "react"


import { BillingListing } from "./billings/billingListing";
import { HoverButton } from '../../commons';
import { AddBillForm } from "./billings/addBillForm";
import { ConfirmationPage } from "../../commons";
import { getTransactionList } from "./billings/getTransactionList";
import { PostBill } from "./billings/PostBilling";

function MainPage(props){


    async function onPayConfirm(){
        await PostBill(props.props.user_id)
        props.onClose()
      }
  
      function onPayDecline(){
        props.onClose()
      }

    async function GenerateBill(){

        const transactionsList  = await getTransactionList({client: props.props.user_id})

        var total = 0

        transactionsList.filter(item => !item.paid).forEach(item => total+=item.total)

        if(total){
            props.onPageChange(null, <ConfirmationPage onAccept={onPayConfirm} onDecline={onPayDecline} text={`Total is ${total} ALL. Proceed?`}/>)
        }
    }

    return (
        <div style={Container}>

            <BillingListing user_id={props.props.user_id} type={props.props.type} onPageChange={props.onPageChange} onClose={props.onClose}/>

            <div>
                {props.props.type=="edit" 
                ? <div style={actionButtonContainer}>
                <HoverButton text="ADD" HoverStyle={ActionButtonHover} DefaultStyle={ActionButton} onClick={(e) => props.onPageChange(e, <AddBillForm onClose={props.onClose} user_id={props.props.user_id}/>)}/>
                </div>
                : ""}

                {props.props.type=="edit" 
                ? <div style={actionButtonContainerRight}>
                <HoverButton text="GENERATE BILL" HoverStyle={ActionButtonHover} DefaultStyle={ActionButton} onClick={GenerateBill}/>
                </div>
                : ""}
            </div>

        </div>
  );

}

export function BillingInformation(props) {

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

const actionButtonContainerRight = {
    "display": "inline-block",
    "margin": "20px",
    "margin-right": "40px",
    "vertical-align": "middle",
    "float": "right"

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
