import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"


import { Container } from '../commons';
import { ClientsListing } from './clientsListing';
import { ClientSearchBar } from './clientSearchBar';
import { AddClientForm } from './clientForm/addClientForm';
import { ViewClientForm } from './clientForm/viewClientForm';
import { getUserList } from './js/getUserList';


export function Clients() {
      
      const [currentOverlay, setcurrentOverlay] = useState("");
      const [UserList, setUserListState] = useState([]);

      var onAddClick = () => {
            setcurrentOverlay(<AddClientForm onClose={closeOverlay}/>)
      }

      var onOpenUserForm = (e, type="view") => {

            setcurrentOverlay(<ViewClientForm onClose={closeOverlay} type={type} user_id={e.currentTarget.getAttribute("user_id")}/>)
      }

      var closeOverlay = () => {
            setcurrentOverlay("")
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
                    <ClientSearchBar onAddClick={onAddClick}/>
                    <ClientsListing onOpenUserForm={onOpenUserForm} data={UserList}/>
                  </Container>
            
            </div>
      );
}
