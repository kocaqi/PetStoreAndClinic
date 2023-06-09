import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const PostBill = async (body) => {

    

    return axios.get(process.env.REACT_APP_HOST+"bills/create?clientId="+body) //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}