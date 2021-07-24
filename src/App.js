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
import UpdateOrgForm from './resources/UpdateOrg';
import PersistenDrawerLoggedIn from './interface/DrawerLoggedIn';
import Profile from './interface/Profile';
import UpdatePassForm from './resources/UpdatePassword';
import PublicProfile from './interface/PublicProfile';
import MapContainer from './resources/CreateEvent';
import JoinEvent from './resources/JoinEvent';
import EventPage from './interface/EventPage';

    function App() {
      var isLoggedIn = localStorage.getItem('tokenID') != null;
      return (  
        <Router>
          <div className="App">
            <RedirectHome/>
            {isLoggedIn && <PersistenDrawerLoggedIn/>}
            {!isLoggedIn && <PersistentDrawerRightNotLoggedIn/>}
            {!isLoggedIn && <Route exact path="/home" component={HomePage}/>}
            {isLoggedIn && <Route exact path="/home" component={RedirectFeed}/>}
            {isLoggedIn && <Route exact path="/register_user" component={RedirectFeed}/>}
            {!isLoggedIn && <Route exact path="/register_user" component={RegisterUser}/>}
            {isLoggedIn && <Route exact path="/register_organization" component={RedirectFeed}/>}
            {!isLoggedIn && <Route exact path="/register_organization" component={RegisterOrg}/>}
            {!isLoggedIn && <Route exact path="/register" component={RegisterSelect}/>}
            {isLoggedIn && <Route exact path="/register" component={RedirectFeed}/>}
            {!isLoggedIn && <Route exact path="/login" component={LoginForm}/>}
            {isLoggedIn && <Route exact path="/login" component={RedirectFeed}/>}
            {!isLoggedIn && <Route exact path="/myProfile" component={RedirectLogin}/>}
            {isLoggedIn && <Route exact path="/myProfile" component={Profile}/>}
            {isLoggedIn && localStorage.getItem('role') !== "ORG" && <Route exact path="/update" component={UpdateForm}/>}
            {isLoggedIn && localStorage.getItem('role') === "ORG" && <Route exact path="/update" component={UpdateOrgForm}/>}
            {!isLoggedIn && <Route exact path="/update" component={RedirectLogin}/>}
            {isLoggedIn && <Route exact path="/delete" component={DeleteForm}/>}
            {!isLoggedIn && <Route exact path="/delete" component={RedirectLogin}/>}
            <Route exact path="/About Us" component={AboutUsPage}/>
            <Route exact path="/logout" component={Logout}/>
            {isLoggedIn && <Route exact path="/password" component={UpdatePassForm}/>}
            {!isLoggedIn && <Route exact path="/password" component={RedirectLogin}/>}
            <Route exact path="/profile/:username/" component={Account}/>
            {!isLoggedIn && <Route exact path="/Add New Event" component={RedirectLogin}/>}
            {isLoggedIn && localStorage.getItem('role') !== "ORG" && <Route exact path="/Add New Event" component={RedirectFeed}/>}
            {isLoggedIn && localStorage.getItem('role') === "ORG" && <Route exact path="/Add New Event" component={MapContainer}/>}
            {!isLoggedIn && <Route exact path="/Join Event" component={RedirectLogin}/>}
            {isLoggedIn && localStorage.getItem('role') === "ORG" && <Route exact path="/Join Event" component={RedirectFeed}/>}
            {isLoggedIn && localStorage.getItem('role') !== "ORG" && <Route exact path="/Join Event" component={JoinEvent}/>}
            {isLoggedIn && <Route exact path="/event/:event" component={Event}/>}
            {!isLoggedIn && <Route exact path="/event/:event" component={RedirectLogin}/>}
          </div>
        </Router>
      );
    }

    function RedirectLogin(){
      return <Redirect to="/login"/>
    }
    
    function RedirectFeed(){
      return <Redirect to="/feed"/>
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

    function Event(){
      const event = useParams();
      return  <EventPage event={event}/>;
    }

    export default App; 