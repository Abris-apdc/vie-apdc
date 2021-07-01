import { Button, CircularProgress, createStyles, makeStyles, Theme } from '@material-ui/core';
import axios from 'axios';
import React, { useState } from 'react';
import InfoIcon from '@material-ui/icons/Info';
import DeleteIcon from '@material-ui/icons/Delete';
import VpnKeyIcon from '@material-ui/icons/VpnKey';
import VisibilityIcon from '@material-ui/icons/Visibility';

var firstName: string;
var lastName: string;
var role: string;

function Profile(){
    const [isLoading, setLoading] = useState(true);

    axios.get("https://amazing-office-313314.appspot.com/rest/profile/"+localStorage.getItem('username'))
    .then(function ({data}) {
        firstName = data.firstName;
        lastName = data.lastName;
        role = data.role;
        setLoading(false);
    })

    const useStyles = makeStyles((theme: Theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );

    const classes = useStyles();


    if (isLoading) {
        return <div style={{color:"white"}} className="App">
        <br/>Loading...<br/>
        <CircularProgress style={{color:"white"}}/>
    </div>;
    }

    return(
        <div style={{transform: "2000px"}}>
            <br/>
            <span style={{color:"white"}}>{localStorage.getItem('username')}'s profile.</span>
            <br/>
            <span style={{color:"white"}}>Name: {firstName}&nbsp;{lastName}</span>
            <br/>
            <span style={{color:"white"}}>Role: {role}</span>
            <br/>
            <br/>
            <Button
                variant="contained"
                color="primary"
                href={"/profile/"+localStorage.getItem('username')}
                className={classes.button}
                startIcon={<VisibilityIcon/>}>
                Check how your profile looks to public eye!
            </Button>
            <br/>
            <Button
                variant="contained"
                color="primary"
                href="/update"
                className={classes.button}
                style={{width: "210px"}}
                startIcon={<InfoIcon/>}>
                update Info
            </Button>
            <br/>
            <Button
                variant="contained"
                color="primary"
                href="/password"
                style={{width: "210px"}}
                className={classes.button}
                startIcon={<VpnKeyIcon/>}>
                change password
            </Button>
            <br/>
            <Button
                variant="contained"
                color="primary"
                href="/delete"
                style={{width: "210px"}}
                className={classes.button}
                startIcon={<DeleteIcon/>}>
                Delete account
            </Button>
        </div>
    )
}

export default Profile;