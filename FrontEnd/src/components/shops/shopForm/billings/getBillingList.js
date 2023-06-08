import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const getBillingList = async (user_id) => {

    

    return axios.get("./templates/BillingList"+user_id+".json") //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}