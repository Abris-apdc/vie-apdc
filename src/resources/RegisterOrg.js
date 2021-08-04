import { Box, Button, createStyles, makeStyles } from "@material-ui/core";
import React from "react"
import { useState} from "react"
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
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

    function handleChangeName(e){
        setName(e.target.value)
    }

    function handleChangeOwner(e){
        setOwner(e.target.value)
    }

    function handleChangeEmail(e){
        setEmail(e.target.value)
    }

    function checkEmail(){
        return email1.toString().includes("@")
    }

    function handleChangePassword(e){
        setPassword(e.target.value)
    }

    function checkPassword(){
        return password1.toString().length > 9
    }

    function handleChangeConfirmation(e){
        setConfirmation(e.target.value)
    }

    function checkConfirm(){
        return confirmation1.toString() === password1.toString()
    }

    function handleChangeID(e){
        setID(e.target.value)
    }

    function handleChangeAddress(e){
        setAddress(e.target.value)
    }

    function handleChangeServiceType(e){
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
        if(!name1.match("^\s*([0-9a-zA-Z]+)\s*$")){
            alert("Organization name cannot contain empty spaces nor special characters")
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
            console.log("Logging In");
            console.log(createJsonLogin());
            fetch("https://amazing-office-313314.appspot.com/rest/login",
                {method:"POST", 
                headers:{ 'Accept': 'application/json, text/plain',
                'Content-Type': 'application/json;charset=UTF-8'},
                body: createJsonLogin()
                })
            .then(response=> {response.json().then(function(parsedData){
                localStorage.setItem("tokenID", parsedData.tokenID);
                localStorage.setItem("username", parsedData.username);
                localStorage.setItem("role", parsedData.role);
                window.location.href = "/feed";
                });
            });
        }});
    }
    
    function createJsonLogin(){
        return JSON.stringify({username:name1, password:password1})
    }

    function createJson(){
        return JSON.stringify({name:name1, owner:owner1,  
            email:email1, password:password1,
            id:id1, address:address1, serviceType:serviceType1})
    }

    const useStyles = makeStyles((theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );

    const classes = useStyles();

    return <div className="container"  style={{transform: "scale(1.35)"}}>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <div style={{display:"flex", justifyContent:"center"}}>
                    <Box bgcolor='#1B3651' width={3/4} textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                        <form onSubmit={(e) => {e.preventDefault()}}>
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
                        </form><br/>
                        <Button
                            variant="contained"
                            color="primary"
                            className={classes.button}
                            startIcon={<CheckCircleIcon/>}
                            onClick={registerOrganisation}>
                            Register
                        </Button>
                    </Box>
                </div>
            </div>
}

export default RegisterOrgForm