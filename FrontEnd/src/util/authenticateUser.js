import axios from "axios";


export const authenticateUser = async (body) => {

    

    return axios.get(process.env.REACT_APP_HOST+"auth?sessionId="+body) //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}