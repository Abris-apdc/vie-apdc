import axios from 'axios';
import React, { useState } from 'react';
import { Box, Button, createStyles, CircularProgress, makeStyles } from "@material-ui/core";
import ArrowRightIcon from '@material-ui/icons/ArrowRight';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';

var events;

export default function JoinEvent(){
    const [isLoading, setLoading] = useState(true);
    
    const[value, setValue] = React.useState("");

    axios.get("https://amazing-office-313314.appspot.com/rest/map/list")
    .then(function ({data}) {
        events = data;
        setLoading(false);
    });

    const useStyles = makeStyles((theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );

    const classes = useStyles();

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

    function handleRedirect(e) {
		if(e.key === 'Enter'){
            document.location.href = "/event/"+value;
        }
    }

    return(
    <div style={{overflowX:"hidden", height:"100vh"}}>
        <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
        <div style={{transform: "scale(1.8)", display:"flex", justifyContent:"center"}}>
            <Box bgcolor='#1B3651' width="20vw" height="30vh" textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                <p style={{color:"white", fontSize:"2vh"}}>
                    Use the search bar below to search for events by name:
                </p>
                <Autocomplete
                freeSolo
                id="free-solo-2-demo"
                disableClearable
                options={events.map((option) => option.event)}
                style={{margin: 'auto', transform:"scale(0.7)"}}
                onChange={(_event, value) => setValue(value)}
                renderInput={(params) => (
                <TextField
                    {...params}
                    label="Search Events"
                    margin="normal"
                    style={{backgroundColor:"white"}}
                    variant="filled"
                    InputProps={{ ...params.InputProps, type: 'search' }}
                    onKeyPress = {handleRedirect}
                />
                )}/>
            </Box>
        </div>
    </div>
    )
};