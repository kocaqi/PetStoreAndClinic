import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const UpdateUser = async (user_id, body) => {

    

    return axios.post(process.env.REACT_APP_HOST+"clients/update/"+user_id, body, {
        headers: {
            "Content-Type": "text/plain"

        },
    }) //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}