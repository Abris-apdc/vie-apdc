import axios from 'axios';
import React, { useState } from 'react';

var firstName: string;
var lastName: string;
var role: string;

function Profile(){
    const [isLoading, setLoading] = useState(true);

    axios.get("https://amazing-office-313314.appspot.com/rest/profile/"+localStorage.getItem('username'))
    .then(function ({data}) {
        firstName = data.firstName;
        lastName = data.lastName;
        role = data.role;
        setLoading(false);
    })

    if (isLoading) {
        return <div style={{color:"white"}} className="App"><br/>Loading...</div>;
    }

    return(
        <div style={{transform: "2000px"}}>
            <br/>
            <span style={{color:"white"}}>{localStorage.getItem('username')}'s profile.</span>
            <br/>
            <span style={{color:"white"}}>Name: {firstName}&nbsp;{lastName}</span>
            <br/>
            <span style={{color:"white"}}>Role: {role}</span>
            <br/>
            <br/>
            <a href="/update"><button style={{padding: "15px 47px",  fontSize:"15px"}}>Update Info</button></a>
            <a href="/delete"><button style={{padding: "15px 30px",  fontSize:"15px"}}>Delete Account</button></a>
            <br/>
            <a href="/password"><button style={{padding: "15px 55px",  fontSize:"15px"}}>Update Password</button></a>
            <a href={"/profile/"+localStorage.getItem('username')}><button style={{padding: "15px 30px",  fontSize:"15px"}}>See Your Public Profile!</button></a>
        </div>
    )
}

export default Profile;