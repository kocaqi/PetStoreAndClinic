import { Routes, Route } from "react-router-dom";
import { Home } from "./components/home/home";
import { Clients } from "./components/clients/clients";
import { Login } from "./components/login/login";
import { SecureRoute } from "./util/secureRoute"; //Custom secure route
import { NotAuthorized } from "./components/notAuthorized/notAuthorized";


function App() {

  return (

    //Creating routes for the app using the custom routes

    <Routes>

      <Route path="/" element={
        <SecureRoute Route = {<Home />} LoggedIn={true} roles={["client", "reception", "manager", "doctor", "admin"]} Redirect="/login" />} 
      />

      <Route path="/login" element={
        <SecureRoute Route = {<Login />} LoggedIn={false} roles={["client", "reception", "manager", "doctor", "admin"]} Redirect="/" />} 
      />

      <Route path="/clients" element={
        <SecureRoute Route = {<Clients />} LoggedIn={true} roles={["reception", "manager", "admin", "doctor"]} Redirect="/" />} 
      />
      
      <Route path="/not-authorized" element={<NotAuthorized />} />

    </Routes>
  );
}

export default App;
