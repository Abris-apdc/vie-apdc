import React from 'react';
import './App.css';
import PersistentDrawerRightNotLoggedIn from './interface/Drawer';
import {BrowserRouter as Router, Redirect, Route, useParams} from 'react-router-dom'; 
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
import UpdatePassForm from './resources/UpdatePassword';
import PublicProfile from './interface/PublicProfile';
import MapContainer from './resources/Map'

    function App() {
      var isLoggedIn = localStorage.getItem('tokenID') != null;
      return (  
        <Router>
          <div className="App">
            <RedirectHome/>
            {isLoggedIn && <PersistenDrawerLoggedIn/>}
            {!isLoggedIn && <PersistentDrawerRightNotLoggedIn/>}
            <Route exact path="/home" component={HomePage}/>
            <Route exact path="/register_user" component={RegisterUser}/>
            <Route exact path="/register_organization" component={RegisterOrg}/>
            <Route exact path="/register" component={RegisterSelect}/>
            <Route exact path="/login" component={LoginForm}/>
            <Route exact path="/myProfile" component={Profile}/>
            <Route exact path="/update" component={UpdateForm}/>
            <Route exact path="/delete" component={DeleteForm}/>
            <Route exact path="/about" component={AboutUsPage}/>
            <Route exact path="/logout" component={Logout}/>
            <Route exact path="/password" component={UpdatePassForm}/>
            <Route exact path="/profile/:username/" component={Account}/>
            <Route exact path="/Map" component={MapContainer}/>
          </div>
        </Router>
      );
    }

    function RedirectHome(){
      var redirect;
      var isLoggedIn = localStorage.getItem('tokenID') != null;
      const inRawPath = window.location.pathname === "/";
      if(inRawPath)
        if(!isLoggedIn)
          redirect = <Redirect to="/home"/>
        else
          redirect = <Redirect to="/feed"/>
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
      localStorage.removeItem('role');

      window.location.href="../home";

      if(localStorage.getItem('hasReloaded') === "nop"){
        window.location.reload();
        localStorage.removeItem('hasReloaded');
      }
      return <div/>
    } 

    function Account() {
      const account = useParams();
      return PublicProfile(account);
    }

    export default App; 