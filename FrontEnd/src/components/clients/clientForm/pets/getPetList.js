import axios from 'axios';
/*
*
* NEED TO REWORK
* 
* Add request to back end.
*/

export const getPetList = async (params= null) => {

    

    return axios.get(process.env.REACT_APP_HOST+"pets"+(params && Object.keys(params).filter(x => params[x]!="").length ? "?keyword="+Object.keys(params).filter(x => params[x]!="").map(x => x+":"+params[x]).join(",") : "")) //template request
    .then(data => {
        return data.data
    })
    .catch(error => {
        alert(error.message)
    });
    

}