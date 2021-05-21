import React from "react"
import { ChangeEvent, useState} from "react"
import './Register.css';


function DeleteForm(){
    //descobrir como passar estes dois arguementos
    var username1 = localStorage.getItem('username');
    var tokenID1 = localStorage.getItem('tokenID');

    function deleteUser(){
        console.log("Deleting User");
        console.log(createJson());

        fetch("https://amazing-office-313314.appspot.com/rest/delete",
            {method:"DELETE", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: createJson()
            })
        .then(response=> {if(!response.ok){
            alert("Something went wrong")
        }else {
            console.log(response);
            console.log(response.body);
            window.location.href = "/Home";
            localStorage.removeItem('username');
            localStorage.removeItem('tokenID');
        }});
    }

    function createJson(){
        return JSON.stringify({tokenID: tokenID1, username:username1})
    }

    return <div>
            <br/>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}>This will delete your account,</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}>if you still want to continue press the button below.</span>
            <br/>
            <br/>
            <button type="button" onClick={deleteUser}>Delete</button>
</div>

}

export default DeleteForm