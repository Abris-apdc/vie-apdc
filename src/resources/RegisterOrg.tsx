import React from "react"
import { ChangeEvent, useState} from "react"
import './Register.css';

function RegisterOrgForm(){
    const [name1, setName] = useState("")
    const [owner1, setOwner] = useState("")
    const [email1, setEmail] = useState("")
    const [password1,setPassword] = useState("")
    const [confirmation1, setConfirmation] = useState("")
    const [id1, setID] = useState("")
    const [address1, setAddress] = useState("")
    const [serviceType1, setServiceType] = useState("")

    function checkAllFields(){
        if(name1.toString() === "") {
            return false;
        }
        if(owner1.toString() === "") {
            return false;
        }
        if(email1.toString() === "") {
            return false;
        }
        if(password1.toString() === "") {
            return false;
        }
        if(confirmation1.toString() === "") {
            return false;
        }
        if(id1.toString() === "") {
            return false;
        }
        if(address1.toString() === "") {
            return false;
        }
        if(serviceType1.toString() === "") {
            return false;
        }
        return true;
    }

    function handleChangeName(e:ChangeEvent<HTMLInputElement>){
        setName(e.target.value)
    }

    function handleChangeOwner(e:ChangeEvent<HTMLInputElement>){
        setOwner(e.target.value)
    }

    function handleChangeEmail(e:ChangeEvent<HTMLInputElement>){
        setEmail(e.target.value)
    }

    function checkEmail(){
        return email1.toString().includes("@")
    }

    function handleChangePassword(e:ChangeEvent<HTMLInputElement>){
        setPassword(e.target.value)
    }

    function checkPassword(){
        return password1.toString().length > 9
    }

    function handleChangeConfirmation(e:ChangeEvent<HTMLInputElement>){
        setConfirmation(e.target.value)
    }

    function checkConfirm(){
        return confirmation1.toString() === password1.toString()
    }

    function handleChangeID(e:ChangeEvent<HTMLInputElement>){
        setID(e.target.value)
    }

    function handleChangeAddress(e:ChangeEvent<HTMLInputElement>){
        setAddress(e.target.value)
    }

    function handleChangeServiceType(e:ChangeEvent<HTMLInputElement>){
        setServiceType(e.target.value)
    }
    
    function registerOrganisation(){
        console.log("Registering Organisation");
        console.log(createJson());
        if(!checkAllFields()){
            alert("All Fields Are Mandatory")
            return
        }
        if(!checkEmail()){
            alert("Not a valid Email")
            return
        }
        if(!checkPassword()){
            alert("Pasword must contain at least 10 characters")
            return
        }
        if(!checkConfirm()){
            alert("Pasword do not match")
            return
        }
        
        fetch("https://amazing-office-313314.appspot.com/rest/register/organisation",
            {method:"POST", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: createJson()
            })
        .then(data=> {if(!data.ok){
            alert("Email already in use")
        }else {
            console.log(data);
            console.log("Logging In");
            console.log(createJson());
            fetch("https://amazing-office-313314.appspot.com/rest/login",
                {method:"POST", 
                headers:{ 'Accept': 'application/json, text/plain',
                'Content-Type': 'application/json;charset=UTF-8'},
                body: createJsonLogin()
                })
            .then(response=> {response.json().then(function(parsedData){
                localStorage.setItem("tokenID", parsedData.tokenID);
                localStorage.setItem("username", parsedData.username);
                window.location.href = "/loggedIn/Feed";
                });
            });
        }});
    }
    
    function createJsonLogin(){
        return JSON.stringify({username:email1, password:password1})
    }

    function createJson(){
        return JSON.stringify({name:name1, owner:owner1,  
            email:email1, password:password1, confirmation:confirmation1, 
            id:id1, address:address1, serviceType:serviceType1})
    }

    return <div className="container">
                <br/>
                <br/>
                <form onSubmit={(e:any) => {e.preventDefault()}}>
                    <label style={{color: "white"}}>Organization Name:</label>
                    <input type={"text"} value={name1} onChange={handleChangeName} required = {true}/>
                    <label style={{color: "white"}}>Owner's Name:</label>
                    <input type={"text"} value={owner1} onChange={handleChangeOwner} required = {true}/>
                    <label style={{color: "white"}}>Email:</label>
                    <input type={"text"} value={email1} onChange={handleChangeEmail} required = {true}/>
                    <label style={{color: "white"}}>Password:</label>
                    <input type={"password"} value={password1} onChange={handleChangePassword} required = {true}/>
                    <label style={{color: "white"}}>Confirm Password:</label>
                    <input type={"password"} value={confirmation1} onChange={handleChangeConfirmation} required = {true}/>
                    <label style={{color: "white"}}>CardID:</label>
                    <input type={"text"} value={id1} onChange={handleChangeID} required = {true}/>
                    <label style={{color: "white"}}>Address:</label>
                    <input type={"text"} value={address1} onChange={handleChangeAddress} required = {true}/>
                    <label style={{color: "white"}}>Service Type:</label>
                    <input type={"text"} value={serviceType1} onChange={handleChangeServiceType} required = {true}/>
                </form>
                <button type="button" onClick={registerOrganisation}>Register</button>
            </div>
}

export default RegisterOrgForm