import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const getFeedbackList = async (user_id) => {

    

    return axios.get(process.env.REACT_APP_HOST+"feedback?keyword=doctor:"+user_id) //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}