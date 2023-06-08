import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const getShopData = async (user_id) => {

    

    return axios.get(process.env.REACT_APP_HOST+"shops?keyword=id:"+user_id) //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}