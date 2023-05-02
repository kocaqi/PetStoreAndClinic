import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

import { Menu } from '../menu/menu';



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
                  <h1>Welcome {cookies.user.role}</h1>

                  <button onClick={handleSignOut}>Signout</button>

                  <Menu />
            
            </div>
      );
}
