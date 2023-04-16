import { Routes, Route } from "react-router-dom";
import { Home } from "./components/home/home";
import { Login } from "./components/login/login";
import { SecureRoute } from "./util/secureRoute"; //Custom secure route
import { NotAuthorized } from "./components/notAuthorized/notAuthorized";


function App() {

  return (

    //Creating routes for the app using the custom routes

    <Routes>

      <Route path="/" element={
        <SecureRoute Route = {<Home />} LoggedIn={true} roles={["admin", "client"]} Redirect="/login" />} 
      />

      <Route path="/login" element={
        <SecureRoute Route = {<Login />} LoggedIn={false} roles={["admin", "client"]} Redirect="/" />} 
      />
      
      <Route path="/not-authorized" element={<NotAuthorized />} />

    </Routes>
  );
}

export default App;
