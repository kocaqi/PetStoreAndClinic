import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const getFeedbackList = async (user_id) => {

    

    return axios.get("./templates/FeedbackList"+user_id+".json") //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}