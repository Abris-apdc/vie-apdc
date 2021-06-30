import axios from 'axios';
import React, { useState } from 'react';

var firstName: string;
var lastName: string;
var role: string;

function PublicProfile(username: string){
    const user= JSON.stringify(username).split(':"')[1].split('"}')[0];

    const [isLoading, setLoading] = useState(true);

    const [gotError, setError] = useState(false);

    axios.get("https://amazing-office-313314.appspot.com/rest/profile/"+user)
        .then(function ({data}) {
            firstName = data.firstName;
            lastName = data.lastName;
            role = data.role;
            setLoading(false);
        }).catch(err => {
            console.log(err);
            setLoading(false);
            setError(true);
        })  

    if (isLoading) {
        return <div style={{color:"white"}} className="App"><br/>Loading...</div>;
    }

    if(gotError){
        return <div style={{color:"white"}} className="App"><br/>No account with given username.</div>;
    }

    var isLoggedIn = <p/>;
    if(localStorage.getItem('username') === user)
        isLoggedIn = <a href="/myProfile"><button style={{padding: "12px 20px",  fontSize:"15px"}}>Go Back</button></a>;

    return(
        <div>
            <br/>
            <span style={{color:"white"}}>Username: {user}</span>
            <br/>
            <span style={{color:"white"}}>Name: {firstName}&nbsp;{lastName}</span>
            <br/>
            <span style={{color:"white"}}>Role: {role}</span>
            <br/>
            <br/>
            {isLoggedIn}
        </div>
    )   
}

export default PublicProfile;