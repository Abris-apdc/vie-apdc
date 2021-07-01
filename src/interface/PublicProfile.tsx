import { Button, CircularProgress, createStyles, makeStyles, Theme } from '@material-ui/core';
import axios from 'axios';
import React, { useState } from 'react';
import ArrowLeftIcon from '@material-ui/icons/ArrowLeft';

var firstName: string;
var lastName: string;
var role: string;

function PublicProfile(username: string){
    const user= JSON.stringify(username).split(':"')[1].split('"}')[0];

    const [isLoading, setLoading] = useState(true);

    const [gotError, setError] = useState(false);

    axios.get("https://amazing-office-313314.appspot.com/rest/profile/"+user)
        .then(function ({data}) {
            firstName = data.firstName;
            lastName = data.lastName;
            role = data.role;
            setLoading(false);
        }).catch(err => {
            console.log(err);
            setLoading(false);
            setError(true);
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

    if(gotError){
        return <div style={{color:"white"}} className="App"><br/>No account with given username.</div>;
    }

    var isLoggedIn = <p/>;
    if(localStorage.getItem('username') === user)
        isLoggedIn = <Button
        variant="contained"
        color="primary"
        href="/myProfile"
        className={classes.button}
        startIcon={<ArrowLeftIcon/>}>
        go back
    </Button>;  

    return(
        <div>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <span style={{color:"white"}}>Username: {user}</span>
            <br/>
            <span style={{color:"white"}}>Name: {firstName}&nbsp;{lastName}</span>
            <br/>
            <span style={{color:"white"}}>Role: {role}</span>
            <br/>
            <br/>
            {isLoggedIn}
        </div>
    )   
}

export default PublicProfile;