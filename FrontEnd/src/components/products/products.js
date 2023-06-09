import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react"


import { Container } from '../commons';
import { ProductListing } from './productListing';
import { ProductsSearchBar } from './productsSearchBar';
import { AddProductForm } from './productForm/addProductForm';
import { ViewProductForm } from './productForm/viewProductForm';
import { getUserList } from './js/getUserList';


export function Products() {
      
      const [currentOverlay, setcurrentOverlay] = useState("");
      const [UserList, setUserListState] = useState([]);

      var onAddClick = () => {
            setcurrentOverlay(<AddProductForm onClose={closeOverlay} refresh={setUserList}/>)
      }

      var onOpenUserForm = (e, user_id, type="view") => {

            setcurrentOverlay(<ViewProductForm onClose={closeOverlay} type={type} user_id={user_id}/>)
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
                    <ProductsSearchBar onAddClick={onAddClick} onSearch={onSearch}/>
                    <ProductListing openCustomOverlay={openCustomOverlay} closeOverlay={closeOverlay} onOpenUserForm={onOpenUserForm} data={UserList}/>
                  </Container>
            
            </div>
      );
}
