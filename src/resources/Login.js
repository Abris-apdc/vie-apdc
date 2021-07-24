import { Box, Button, createStyles, makeStyles } from "@material-ui/core";
import React from "react"
import { useState} from "react"
import './Register.css';
import ArrowRightIcon from '@material-ui/icons/ArrowRight';


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

    function handleChangeUsername(e){
        setUserName(e.target.value)
    }

    function handleChangePassword(e){
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
                localStorage.setItem('role',parsedData.role);
                localStorage.setItem('hasReloaded', "nop");
                window.location.href = "/feed";
            });
        }});
    }

    function createJson(){
        return JSON.stringify({username:username1, password:password1})
    }

    const useStyles = makeStyles((theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );
    
    const classes = useStyles();

    return <div className="container" style={{transform: "scale(1.5)"}}>
        <br/><br/><br/><br/><br/><br/><br/><br/>
        <div style={{display:"flex", justifyContent:"center"}}>
            <Box bgcolor='#1B3651' width={3/4} textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                <form onSubmit={(e) => {e.preventDefault()}}>
                    <label style={{color: "white"}}>Username:</label> 
                    <input type={"text"} value={username1}  onChange={handleChangeUsername} required = {true}/>
                    <label style={{color: "white"}}>Password:</label>
                    <input type={"password"} value={password1} onChange={handleChangePassword} required = {true}/>
                </form>
                <br/> 
                <Button
                    onClick={loginUser}
                    variant="contained"
                    color="primary"
                    className={classes.button}
                    startIcon={<ArrowRightIcon/>}
                >
                    Login
                </Button>
            </Box>
        </div>
    </div>
}

export default LoginForm