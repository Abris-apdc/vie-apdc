import axios from 'axios';
import React, {useState} from 'react';
import { Box, Button, createStyles, CircularProgress, makeStyles, Link } from "@material-ui/core";
import ArrowRightIcon from '@material-ui/icons/ArrowRight';

var userRoutes;

export default function AddToRoute(){

    const [isLoading, setLoading] = useState(true);

    fetch("https://amazing-office-313314.appspot.com/rest/route/get",
    {method:"POST", 
    headers:{ 'Accept': 'application/json, text/plain',
    'Content-Type': 'application/json;charset=UTF-8'},
    body: JSON.stringify({  username:localStorage.getItem('username'), 
                            tokenID:localStorage.getItem('tokenID')})      
    }).then(response=> {response.json().then(function(parsedData){
        userRoutes = parsedData;
        setLoading(false)
    })})

    function add(route){
        axios.get("https://amazing-office-313314.appspot.com/rest/route/get/"+route)
        .then( (data) => {
            for(var i = 0; i<data.data.length;i++){
                if(data.data[i].event === localStorage.getItem('event')){
                    alert("Event already in this route")
                    return
                }
            }
            fetch("https://amazing-office-313314.appspot.com/rest/route/add/"+route,
            {method:"POST", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: JSON.stringify({  name:localStorage.getItem('event'), 
                                    tokenID:localStorage.getItem('tokenID')})      
            }).then(response=> {if(!response.ok){
                alert("Something went wrong")
            } else {
                window.location.href = "/event/"+localStorage.getItem('event');
            }})
        })
    }

    if (isLoading) {
        return <div style={{color:"white"}} className="App">
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
        <br/>Loading...<br/>
        <CircularProgress style={{color:"white"}}/>
    </div>;
    }

    return(
    <div style={{overflowX:"hidden", height:"100vh"}}>
        <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
        <div style={{transform: "scale(1.8)", display:"flex", justifyContent:"center"}}>
            <Box bgcolor='#1B3651' width="35vw" height="22vh" textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                <p style={{color:"white", fontSize:"2vh"}}>
                    To which route do you wish to add the event?
                </p>
                {userRoutes.map(route => (
                    <div>
                        <Link key={route} style={{color:"gray"}} onClick={() => add(route)}>{route}</Link>
                        <br/>   
                    </div>
                ))}
            </Box>
        </div>
    </div>
    )
};