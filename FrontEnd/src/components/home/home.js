import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"

import { Menu } from '../menu/menu';
import { Container } from '../commons';
import { HoverButton } from '../commons';


import { ViewClientForm } from '../clients/clientForm/viewClientForm';



export function Home() {

      const navigate = useNavigate()

      //Cookies

      const [cookies, setCookie, removeCookie] = useCookies();

      const [currentOverlay, setcurrentOverlay] = useState("");

      var onOpenProfile = (e, component) => {

            setcurrentOverlay(component)
            
      }

      var closeOverlay = () => {
            setcurrentOverlay("")
      }


      //SignOut function
      

      return (
            <div>
                  <div>{currentOverlay}</div>
                  <Container>
                        <Menu onOpenProfile={onOpenProfile} onClose={closeOverlay}/>
                  </Container>
            
            </div>
      );
}


