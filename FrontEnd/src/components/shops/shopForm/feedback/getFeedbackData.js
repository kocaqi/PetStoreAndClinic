import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const getFeedbackData = async (id) => {

    

    return axios.get(process.env.REACT_APP_HOST+"feedback?keyword=id:"+id) //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}