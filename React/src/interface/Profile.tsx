import React from 'react';

function Profile(){
    return(
        <div>
            <br/>
            <span style={{color:"white"}}>{localStorage.getItem('username')}</span>
            <br/>
            <br/>
            <a href="/LoggedIn/Update"><button style={{padding: "15px 47px",  fontSize:"20px"}}>Update Info</button></a>
            <a href="/LoggedIn/Delete"><button style={{padding: "15px 30px",  fontSize:"20px"}}>Delete Account</button></a>
            <br/>
            <a href="/LoggedIn/Password"><button style={{padding: "15px 30px",  fontSize:"20px"}}>Update Password</button></a>
        </div>
    )
}

export default Profile;