import { Box, Button, createStyles, makeStyles, Theme } from "@material-ui/core";
import React from "react"
import { ChangeEvent, useState} from "react"
import './Register.css';
import VpnKeyIcon from '@material-ui/icons/VpnKey';

function UpdatePassForm(){
    const [currPassword1, setCurrPassword] = useState("")
    const [newPassword1, setNewPassword] = useState("")
    const [confirmation1, setConfirmation] = useState("")

    function checkAllFields(){
        if(currPassword1.toString() === "") {
            return false;
        }
        if(newPassword1.toString() === "") {
            return false;
        }
        if(confirmation1.toString() === "") {
            return false;
        }
        return true;
    }

    function handleChangeCurrPassword(e:ChangeEvent<HTMLInputElement>){
        setCurrPassword(e.target.value)
    }

    function handleChangeNewPassword(e:ChangeEvent<HTMLInputElement>){
        setNewPassword(e.target.value)
    }

    function checkNewPassword(){
        return newPassword1.toString().length > 9
    }

    function handleChangeConfirmation(e:ChangeEvent<HTMLInputElement>){
        setConfirmation(e.target.value)
    }

    function checkConfirm(){
        return confirmation1.toString() === newPassword1.toString()
    }

    function updatePassword(){
        console.log("Registering User");
        console.log(createJson());
        if(!checkAllFields()){
            alert("All Fields Are Mandatory")
            return
        }
        if(!checkNewPassword()){
            alert("New Pasword must Contain at Least 10 characters")
            return
        }
        if(!checkConfirm()){
            alert("Pasword do not match")
            return
        }
        
        fetch("https://amazing-office-313314.appspot.com/rest/update/pass",
            {method:"PUT", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: createJson()
            })
        .then(data=> {if(!data.ok){
            alert("Wrong password")
        }else {
            alert("Update Successful")
            console.log(data);
            window.location.href = "/loggedIn/Profile";
        }});

    }

    function createJson(){
        return JSON.stringify({oldPass:currPassword1, newPass:newPassword1,  
            confirmation:confirmation1, tokenID:localStorage.getItem('tokenID')})
    }

    const useStyles = makeStyles((theme: Theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );

    const classes = useStyles();

    return <div className="container" style={{transform: "scale(1.50)"}}>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <div style={{display:"flex", justifyContent:"center"}}>
                <Box bgcolor='#1B3651' width="100%" textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                <form onSubmit={(e:any) => {e.preventDefault()}}>
                    <label style={{color: "white"}}>Current Password:</label>
                    <input type={"password"} value={currPassword1} onChange={handleChangeCurrPassword} required = {true}/>
                    <label style={{color: "white"}}>New Password:</label>
                    <input type={"password"} value={newPassword1} onChange={handleChangeNewPassword} required = {true}/>
                    <label style={{color: "white"}}>Confirm Password:</label>
                    <input type={"password"} value={confirmation1} onChange={handleChangeConfirmation} required = {true}/>
                </form>
                <br/>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={updatePassword}
                    className={classes.button}
                    startIcon={<VpnKeyIcon/>}>
                    Change Password
                </Button>
                </Box>
            </div>
        </div>

}

export default UpdatePassForm