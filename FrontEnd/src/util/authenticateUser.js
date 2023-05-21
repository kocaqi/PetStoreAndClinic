import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const authenticateUser = async (session_id) => {

    if(session_id=="session_id_template"){

        return axios.get("./templates/userAuthSuccess.json") //template request
        .then(data => {
            return data.data
        })
        .catch(error => {
            alert(error.message)
        });
    }
    else{
        return axios.get("./templates/userAuthFail.json") //template request
        .then(data => {
            return data.data
        })
        .catch(error => {
            alert(error.message)
        });
    }

}