import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"

import { SinglePet } from './Singlepet';
import { getPetList } from './getPetList';


export function PetListing(props) {

    const [PetList, setPetListState] = useState([]);

    const effectRan = useRef(false)

    async function setPetList(){

        setPetListState(await getPetList())
    }


    useEffect(() => {

        if(!effectRan.current){

            setPetList() 
        
            effectRan.current = true
        }
    }, [])
      
      return (
        <div style={container1}>
            <div style={container2}>
                <table style={table}>
                <tbody>
                    
                    {PetList.map((user, index) => {
                        return <SinglePet key={index} custom_key={index} pet_data={user} type={props.type} onPageChange={props.onPageChange} onClose={props.onClose} refresh={setPetList}/>;
                    })}
                
                </tbody>
                </table>
            </div>
        </div>
      );
}

const container1 = {
    "width": "95%",
    "height": "100%",
    "overflow-y": "hidden",
    "overflow-x": "hidden",
    "margin": "auto",
}

const container2 = {
    "width": "100%",
    "height": "100%",
    "overflow-y": "scroll",
    "overflow-x": "hidden",
    "padding-right": "22px",
}

const table = {
    "width": "100%",
    "height": "100%"
}
