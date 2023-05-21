import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { Container } from '../commons';
import { ClientsListing } from './clientsListing';


export function Clients() {


      return (
            <div>
                  <Container>
                    <ClientsListing />
                  </Container>
            
            </div>
      );
}
