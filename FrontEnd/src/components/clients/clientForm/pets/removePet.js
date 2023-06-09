import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const removePet = async (user_id) => {

    

    return axios.get(process.env.REACT_APP_HOST+"pets/remove/"+user_id) //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}