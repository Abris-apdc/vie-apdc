import { Box, Button, createStyles, makeStyles, Typography } from "@material-ui/core";
import React from "react"
import {useState} from "react"
import './Register.css';
import DeleteIcon from '@material-ui/icons/Delete';


function DeleteForm(){
    //descobrir como passar estes dois arguementos
    var username1 = localStorage.getItem('username');
    var tokenID1 = localStorage.getItem('tokenID');

    const [password1,setPassword] = useState("")

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
            alert("Incorrect Password");
        }else {
            console.log(response);
            console.log(response.body);
            window.location.href = "/Home";
            localStorage.removeItem('username');
            localStorage.removeItem('tokenID');
        }});
    }

    function createJson(){
        return JSON.stringify({tokenID: tokenID1, username:username1, password:password1})
    }

    function handleChangePassword(e){
        setPassword(e.target.value)
    }

    const useStyles = makeStyles((theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );

    const classes = useStyles();

    return <div>
            <br/>
            <br/>
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
                <Box bgcolor='#1B3651' width={1/2} textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}  style={{transform: "scale(1.5)"}}>
                    <Typography style={{color:"white", fontSize:"20px"}}>
                        This will delete your account.
                        <br/>
                        If really want to continue confirm your password and press the button below.
                    </Typography>             
                    <br/>
                    <input type={"password"} value={password1} onChange={handleChangePassword} required = {true}/>     
                    <br/>
                    <br/>
                    <Button
                        variant="contained"
                        color="primary"
                        className={classes.button}
                        onClick={deleteUser}
                        startIcon={<DeleteIcon/>}>
                        Delete
                    </Button>
                </Box>
            </div>
    </div>

}

export default DeleteForm