import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const getFeedbackData = async (feedback_id) => {

    

    return axios.get("./templates/FeedbackData"+feedback_id+".json") //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}