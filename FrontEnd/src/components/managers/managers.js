import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"


import { Container } from '../commons';
import { ManagersListing } from './managersListing';
import { ManagersSearchBar } from './managersSearchBar';
import { AddManagerForm } from './managerForm/addManagerForm';
import { ViewManagerForm } from './managerForm/viewManagerForm';
import { getUserList } from './js/getUserList';


export function Managers() {
      
      const [currentOverlay, setcurrentOverlay] = useState("");
      const [UserList, setUserListState] = useState([]);

      var onAddClick = () => {
            setcurrentOverlay(<AddManagerForm onClose={closeOverlay} refresh={setUserList}/>)
      }

      var onOpenUserForm = (e, user_id, type="view") => {

            setcurrentOverlay(<ViewManagerForm onClose={closeOverlay} type={type} user_id={user_id}/>)
      }

      var closeOverlay = () => {
            setcurrentOverlay("")
            setUserList()
      }

      var openCustomOverlay = (e, component) => {
            setcurrentOverlay(component)
      }

      var onSearch = async (e, params=null) => {
            setUserListState(await getUserList(params))
      }




      const effectRan = useRef(false)

    async function setUserList(){

      setUserListState(await getUserList())
        
    }




    useEffect(() => {

        if(!effectRan.current){

            setUserList()
        
            effectRan.current = true
        }
    }, [])


      return (
            <div>
                  <div>{currentOverlay}</div>
                  <Container>
                    <ManagersSearchBar onAddClick={onAddClick} onSearch={onSearch}/>
                    <ManagersListing openCustomOverlay={openCustomOverlay} closeOverlay={closeOverlay} onOpenUserForm={onOpenUserForm} data={UserList}/>
                  </Container>
            
            </div>
      );
}
