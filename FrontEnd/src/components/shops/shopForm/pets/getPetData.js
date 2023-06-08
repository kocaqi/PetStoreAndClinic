import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const getPetData = async (pet_id) => {

    

    return axios.get("./templates/petData"+pet_id+".json") //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}