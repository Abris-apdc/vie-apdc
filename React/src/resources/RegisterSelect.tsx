import React from 'react';

function RegisterSelect(){
    return(
        <div>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <span style={{color:"white", fontSize:"40px"}}>Which type of account would you like to Register?</span>
            <br/>
            <br/>
            <a href="/Register_User"><button style={{padding: "15px 60px",  fontSize:"20px"}}>User</button></a>
            <a href="/Register_Organization"><button style={{padding: "15px 30px",  fontSize:"20px"}}>Organization</button></a>
        </div>
    )
}

export default RegisterSelect