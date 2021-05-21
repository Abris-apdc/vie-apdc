import React from 'react';
import './App.css';
import PersistentDrawerRightNotLoggedIn from './interface/Drawer';
import {BrowserRouter as Router, Redirect, Route} from 'react-router-dom'; 
import RegisterUser from './resources/RegisterUser';
import RegisterOrg from './resources/RegisterOrg';
import RegisterSelect from './resources/RegisterSelect';
import DeleteForm from './resources/DeleteUser';
import HomePage from './interface/HomePage';
import LoginForm from './resources/Login';
import AboutUsPage from './interface/AboutUsPage';
import UpdateForm from './resources/UpdateUser';
import PersistenDrawerLoggedIn from './interface/DrawerLoggedIn';
import Profile from './interface/Profile';

    function App() {
      var isLoggedIn = localStorage.getItem('tokenID') != null;
      return (  
        <Router>
          <div className="App">
            <RedirectHome/>
            {isLoggedIn && <PersistenDrawerLoggedIn/>}
            {!isLoggedIn && <PersistentDrawerRightNotLoggedIn/>}
            <Route path="/Home" component={HomePage}/>
            <Route path="/About" component={AboutUsPage}/>
            <Route path="/Register_User" component={RegisterUser}/>
            <Route path="/Register_Organization" component={RegisterOrg}/>
            <Route path="/Register" component={RegisterSelect}/>
            <Route path="/Login" component={LoginForm}/>
            <Route path="/LoggedIn/Profile" component={Profile}/>
            <Route path="/LoggedIn/Update" component={UpdateForm}/>
            <Route path="/LoggedIn/Delete" component={DeleteForm}/>
            <Route path="/LoggedIn/About" component={AboutUsPage}/>
            <Route path="/LoggedIn/Logout" component={Logout}/>
          </div>
        </Router>
      );
    }

    function RedirectHome(){
      var redirect;
      const inRawPath = window.location.pathname == "/";
      if(inRawPath)
        redirect = <Redirect to="/Home"/>
      return(
        <div>
          {redirect}
        </div>
      )
    }

    function Logout(){
      fetch("https://amazing-office-313314.appspot.com/rest/logout",
      {method:"POST", 
      headers:{ 'Accept': 'application/json, text/plain',
      'Content-Type': 'application/json;charset=UTF-8'},
      body: createJson()
      })

      function createJson(){
        return JSON.stringify({tokenID: localStorage.getItem("tokenID"), username:localStorage.getItem("username")})
      }

      localStorage.removeItem('tokenID');
      localStorage.removeItem('username');

      window.location.href="../Home";

      if(localStorage.getItem('hasReloaded') == "nop"){
        window.location.reload();
        localStorage.removeItem('hasReloaded');
      }
      return <div/>
    } 

    export default App; 