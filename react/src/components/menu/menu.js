import { useCookies } from 'react-cookie';
import { MenuItem } from './menuItem';

export function Menu() {

    const [cookies, setCookie] = useCookies();

    if(cookies.user.role=="admin"){
        return(
            <div>
                <MenuItem name="Clients" url="/clients" />
                <MenuItem name="Doctors" url="/doctors" />
                <MenuItem name="My Profile" url="/my-profile" />
            </div>
        )
    }

    if(cookies.user.role=="client"){
        return(
            <div>
                <MenuItem name="Doctors" url="/doctors" />
                <MenuItem name="My Profile" url="/my-profile" />
            </div>
        )
    }
}