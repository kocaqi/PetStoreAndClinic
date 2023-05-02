import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const authenticateUser = async (session_id) => {

    return axios.get("http://localhost:3000/404") //template request
    .then(data => {
        //
    })
    .catch(error => {
        if(session_id === "1111"){
            return {
                role: "client",
                LoggedIn: true
            }
        }
        else{
            return {
                LoggedIn: false
            }
        }
    });

}