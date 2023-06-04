import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const getShopData = async (shop_id) => {

    

    return axios.get("./templates/shopData"+shop_id+".json") //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}