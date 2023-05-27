import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useState } from "react"


import { Container } from '../commons';
import { ClientsListing } from './clientsListing';
import { ClientSearchBar } from './clientSearchBar';
import { AddClientForm } from './addClientForm';
import { ViewClientForm } from './viewClientForm';

export function Clients() {
      
      const [currentOverlay, setcurrentOverlay] = useState("");

      var onAddClick = () => {
            setcurrentOverlay(<AddClientForm onClose={closeOverlay}/>)
      }

      var onOpenUserForm = (target, type="view") => {

            setcurrentOverlay(<ViewClientForm onClose={closeOverlay} type={type}/>)
      }

      var closeOverlay = () => {
            setcurrentOverlay("")
      }

      return (
            <div>
                  <div>{currentOverlay}</div>
                  <Container>
                    <ClientSearchBar onAddClick={onAddClick}/>
                    <ClientsListing onOpenUserForm={onOpenUserForm}/>
                  </Container>
            
            </div>
      );
}
