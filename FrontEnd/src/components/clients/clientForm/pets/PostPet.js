import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const PostPet = async (body) => {

    

    return axios.post(process.env.REACT_APP_HOST+"pets/create", body, {
        headers: {
            "Content-Type": "text/plain",

        },
    }) //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}