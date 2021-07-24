import axios from 'axios';
import React, { useState } from 'react';
import { Box, Button, createStyles, CircularProgress, makeStyles } from "@material-ui/core";
import ArrowRightIcon from '@material-ui/icons/ArrowRight';

var events;

export default function JoinEvent(){
    const [isLoading, setLoading] = useState(true);

    axios.get("https://amazing-office-313314.appspot.com/rest/map/list")
    .then(function ({data}) {
        events = data;
        console.log(events);
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

    function handleButton(event){
        document.location.href = "/event/"+event;
    }

    return(
    <div style={{overflowX:"hidden", height:"100vh"}}>
        <br/><br/><br/><br/><br/><br/>
        <div  style={{transform: "scale(1.5)"}}>
            {events.map(event => (
                <div key={event} style={{display:"flex", justifyContent:"center", paddingTop:"5vh"}}>
                    <Box bgcolor='#1B3651' width="25vw" textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                        <div style={{color:"white"}}>{event.event}</div>
                        <Button
                            onClick={() => {handleButton(event.event)}}
                            variant="contained"
                            color="primary"
                            className={classes.button}
                            startIcon={<ArrowRightIcon/>}>
                            See Event
                        </Button>
                    </Box>
                </div>
            ))}
        </div>
    </div>
    )
};