import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"


import { Container } from '../commons';
import { PetsListing } from './petsListing';
import { PetSearchBar } from './petSearchBar';
import { AddPetForm } from './petForm/addPetForm';
import { ViewPetForm } from './petForm/viewPetForm';
import { getUserList } from './js/getUserList';


export function Pets() {
      
      const [currentOverlay, setcurrentOverlay] = useState("");
      const [UserList, setUserListState] = useState([]);

      var onAddClick = () => {
            setcurrentOverlay(<AddPetForm onClose={closeOverlay} refresh={setUserList}/>)
      }

      var onOpenUserForm = (e, user_id, type="view") => {

            setcurrentOverlay(<ViewPetForm onClose={closeOverlay} type={type} user_id={user_id}/>)
      }

      var closeOverlay = () => {
            setcurrentOverlay("")
            setUserList()
      }

      var openCustomOverlay = (e, component) => {
            setcurrentOverlay(component)
      }

      var onSearch = async (e, params=null) => {
            setUserListState(await getUserList(params))
      }




      const effectRan = useRef(false)

    async function setUserList(){

      setUserListState(await getUserList())
        
    }




    useEffect(() => {

        if(!effectRan.current){

            setUserList()
        
            effectRan.current = true
        }
    }, [])


      return (
            <div>
                  <div>{currentOverlay}</div>
                  <Container>
                    <PetSearchBar onAddClick={onAddClick} onSearch={onSearch}/>
                    <PetsListing openCustomOverlay={openCustomOverlay} closeOverlay={closeOverlay} onOpenUserForm={onOpenUserForm} data={UserList}/>
                  </Container>
            
            </div>
      );
}
