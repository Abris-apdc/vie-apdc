import React from "react"
import { ChangeEvent, useState} from "react"
import './Register.css';


function LoginForm(){
    const [username1, setUserName] = useState("")
    const [password1,setPassword] = useState("")

    function checkAllFields(){
        if(username1.toString() === "") {
            return false;
        }
        if(password1.toString() === "") {
            return false;
        }
        return true;
    }

    function handleChangeUsername(e:ChangeEvent<HTMLInputElement>){
        setUserName(e.target.value)
    }

    function handleChangePassword(e:ChangeEvent<HTMLInputElement>){
        setPassword(e.target.value)
    }

    function loginUser(){
        console.log("Logging In");
        console.log(createJson());
        if(!checkAllFields()){
            alert("All Fields Are Mandatory")
            return
        }
        fetch("https://amazing-office-313314.appspot.com/rest/login",
            {method:"POST", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: createJson()
            })
        .then(response=> {if(!response.ok){
            alert("Incorrect Username or Password")
        }else {
            response.json().then(function(parsedData){
                localStorage.setItem("tokenID", parsedData.tokenID);
                localStorage.setItem("username", parsedData.username);
                localStorage.setItem('hasReloaded', "nop");
                window.location.href = "/loggedIn/Feed";
            });
        }});
    }

    function createJson(){
        return JSON.stringify({username:username1, password:password1})
    }

    return <div className="container">
    <form onSubmit={(e:any) => {e.preventDefault()}}>
        <label style={{color: "white"}}>Username:</label> 
        <input type={"text"} value={username1} onChange={handleChangeUsername} required = {true}/>
        <label style={{color: "white"}}>Password:</label>
        <input type={"password"} value={password1} onChange={handleChangePassword} required = {true}/>
    </form>
    <button type="button" onClick={loginUser}>Login</button>
</div>

}

export default LoginForm