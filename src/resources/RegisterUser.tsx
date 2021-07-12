import { Box, Button, createStyles, makeStyles, Theme } from "@material-ui/core";
import React from "react"
import { ChangeEvent, useState} from "react"
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import './Register.css';

function RegisterForm(){
    const [firstName1, setFirstName] = useState("")
    const [lastName1, setLastName] = useState("")
    const [username1, setUserName] = useState("")
    const [email1, setEmail] = useState("")
    const [password1,setPassword] = useState("")
    const [confirmation1, setConfirmation] = useState("")
    const [day1, setDay] = useState("")
    const [month1, setMonth] = useState("")
    const [year1, setYear] = useState("")

    var dateArr=[];

    function checkAllFields(){
        if(firstName1.toString() === "") {
            return false;
        }
        if(lastName1.toString() === "") {
            return false;
        }
        if(username1.toString() === "") {
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
        if(day1.toString() === "") {
            return false;
        }
        return true;
    }

    function handleChangeFirstName(e:ChangeEvent<HTMLInputElement>){
        setFirstName(e.target.value)
    }

    function handleChangeLastName(e:ChangeEvent<HTMLInputElement>){
        setLastName(e.target.value)
    }

    function handleChangeUsername(e:ChangeEvent<HTMLInputElement>){
        setUserName(e.target.value)
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

    function handleChangeDate(e:ChangeEvent<HTMLInputElement>){
        dateArr = e.target.value.split("-")
        setYear(dateArr[0])
        setMonth(dateArr[1])
        setDay(dateArr[2])
    }

    function checkUsername(){
        return username1.includes(" ");
    }

    function registerUser(){
        console.log("Registering User");
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
        if(checkUsername()){
            alert("Username cannot contain empty spaces")
            return
        }

        fetch("https://amazing-office-313314.appspot.com/rest/register",
            {method:"POST", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: createJson()
            })
        .then(data=> {if(!data.ok){
            alert("Username already in use")
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
                localStorage.setItem("role", parsedData.role);
                window.location.href = "/feed";
                });
            });
        }});
    }
    
    function createJsonLogin(){
        return JSON.stringify({username:username1, password:password1})
    }
    
    function createJson(){
        return JSON.stringify({firstName:firstName1, lastName:lastName1,  
            username:username1, email:email1, password:password1, 
            year:year1, month:month1, day:day1})
    }

    const useStyles = makeStyles((theme: Theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );

    const classes = useStyles();

    return <div className="container" style={{transform: "scale(1.35)"}}>
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
                <form onSubmit={(e:any) => {e.preventDefault()}}>
                    <label style={{color: "white"}}>First Name:</label>
                    <input type={"text"} value={firstName1} onChange={handleChangeFirstName} required = {true}/>
                    <label style={{color: "white"}}>Last Name:</label>
                    <input type={"text"} value={lastName1} onChange={handleChangeLastName} required = {true}/>
                    <label style={{color: "white"}}>Username:</label> 
                    <input type={"text"} value={username1} onChange={handleChangeUsername} required = {true}/>
                    <label style={{color: "white"}}>Email:</label>
                    <input type={"text"} value={email1} onChange={handleChangeEmail} required = {true}/>
                    <label style={{color: "white"}}>Password:</label>
                    <input type={"password"} value={password1} onChange={handleChangePassword} required = {true}/>
                    <label style={{color: "white"}}>Confirm Password:</label>
                    <input type={"password"} value={confirmation1} onChange={handleChangeConfirmation} required = {true}/>
                    <label style={{color: "white"}}>Birthday:</label>
                    <input type={"date"} onChange={handleChangeDate} required = {true}/>
                </form>
                <br/>
                <Button
                    variant="contained"
                    color="primary"
                    className={classes.button}
                    startIcon={<CheckCircleIcon/>}
                    onClick={registerUser}>
                    Register
                </Button>
            </Box>
        </div>
    </div>

}

export default RegisterForm