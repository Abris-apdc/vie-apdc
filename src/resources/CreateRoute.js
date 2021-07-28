import axios from 'axios';
import React, {useState} from 'react';
import { Box, Button, createStyles, CircularProgress, makeStyles } from "@material-ui/core";
import ArrowRightIcon from '@material-ui/icons/ArrowRight';

export default function CreateRoute(){

    const [name, setName] = useState("")
    const [description,setDescription] = useState("")

    function handleChangeName(e){
        setName(e.target.value)
    }
    
    function handleChangeDescription(e){
        setDescription(e.target.value)
    }
    
    function createJson(){
        const locations = [localStorage.getItem('event')]
        return JSON.stringify({ username:localStorage.getItem('username'), 
                                tokenID:localStorage.getItem('tokenID'),
                                locations:locations, 
                                routeName:name, 
                                info:description})
    }

    function submitRoute(){
        if(!name.match("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")){
            alert("Event name can only contain regular letters and numbers")
            return
        }
        fetch("https://amazing-office-313314.appspot.com/rest/route",
        {method:"POST", 
        headers:{ 'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json;charset=UTF-8'},
        body: createJson()
        }).then(response=> {if(!response.ok){
            console.log("shrug")
        } else {
            document.location.href = "/event/"+localStorage.getItem('event');
        }})
    }

    const useStyles = makeStyles((theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );
    
    const classes = useStyles();

    return(
    <div style={{overflowX:"hidden", height:"100vh"}}>
        <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
        <div style={{transform: "scale(1.8)", display:"flex", justifyContent:"center"}}>
            <Box bgcolor='#1B3651' width="20vw" height="35vh" textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                <b style={{color:"white", fontSize:"1.5vw"}}>Create New Route:</b><br/><br/>
                <form onSubmit={(e) => {e.preventDefault()}}>
                    <label style={{color: "white"}}>Route Name:</label><br/> 
                    <input type={"text"} style={{width:"13vw"}} value={name}  onChange={handleChangeName} required = {true}/><br/>
                    <label style={{color: "white"}}>Route Description:</label><br/>
                    <input type={"text"} style={{width:"13vw"}} value={description} onChange={handleChangeDescription} required = {true}/>
                </form>
                <br/>
                <Button
                    onClick={submitRoute}
                    variant="contained"
                    color="primary"
                    className={classes.button}
                    startIcon={<ArrowRightIcon/>}
                >
                    submit Route
                </Button>
            </Box>
        </div>
    </div>
    )
};