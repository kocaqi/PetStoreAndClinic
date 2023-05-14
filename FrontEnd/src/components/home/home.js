import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { Menu } from '../menu/menu';
import { Container } from '../commons';



export function Home() {

      const navigate = useNavigate()

      //Cookies

      const [cookies, setCookie, removeCookie] = useCookies();


      //SignOut function

      const handleSignOut = () => {
            removeCookie('user');
            removeCookie('session_id');
            removeCookie('access_token');
            navigate("/login")

      }
      

      return (
            <div>
                  <Container>
                        <Menu />
                  </Container>
            
            </div>
      );
}
