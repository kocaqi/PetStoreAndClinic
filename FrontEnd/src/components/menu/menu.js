import { useCookies } from 'react-cookie';
import { MenuItem } from './menuItem';

export function Menu() {

    const [cookies, setCookie] = useCookies();

    if(cookies.user.role=="admin"){
        return(
            <div style={MenuOuterContainer}>
                <div style={MenuInnerContainer}>
                    <MenuItem name="Clients" url="/clients" style={FirstItem}/>
                    <MenuItem name="Clients" url="/clients" />
                    <MenuItem name="Clients" url="/clients" />
                    <MenuItem name="Clients" url="/clients" />
                    <MenuItem name="Clients" url="/clients" style={SecondRow} />
                    <MenuItem name="Clients" url="/clients" style={SecondRow} />
                    <MenuItem name="Clients" url="/clients" style={SecondRow} />
                </div>
            </div>
        )
    }
    else if(cookies.user.role=="client"){
        return(
            <div style={MenuOuterContainer}>
                <div style={MenuInnerContainer}>
                    <MenuItem name="Clients" url="/clients" style={FirstItem}/>
                    <MenuItem name="Clients" url="/clients" />
                    <MenuItem name="Clients" url="/clients" display={false}/>
                    <MenuItem name="Clients" url="/clients" />
                    <MenuItem name="Clients" url="/clients" style={SecondRow} />
                    <MenuItem name="Clients" url="/clients" style={SecondRow} />
                    <MenuItem name="Clients" url="/clients" style={SecondRow} />
                </div>
            </div>
        )
    }
}


const MenuOuterContainer = {
    "width": "fit-content",
    "height": "100%",
    "margin": "auto",
    "display": "flex",
    "align-items": "center",
}

const MenuInnerContainer = {
    "width": "800px",
    "height": "fit-content",
    "vertical-align": "middle",
}

const SecondRow = {
    "transform": "translate(110px, -34px)",
}

const FirstItem = {
    "margin-left": "0"
}