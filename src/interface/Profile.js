import { Button, CircularProgress, createStyles, makeStyles } from '@material-ui/core';
import axios from 'axios';
import { Box } from '@material-ui/core';
import React, { useState } from 'react';
import InfoIcon from '@material-ui/icons/Info';
import DeleteIcon from '@material-ui/icons/Delete';
import VpnKeyIcon from '@material-ui/icons/VpnKey';
import VisibilityIcon from '@material-ui/icons/Visibility';

var firstName;
var lastName;
var role;
var birthday;
var email;
var followers;
var following;
var phone;

function Profile(){
    const [isLoading, setLoading] = useState(true);

    axios.get("https://amazing-office-313314.appspot.com/rest/profile/"+localStorage.getItem('username'))
    .then(function ({data}) {
        firstName = data.firstName;
        lastName = data.lastName;
        role = data.role;
        setLoading(false);
    })

    axios.get("https://amazing-office-313314.appspot.com/rest/profile/get/"+localStorage.getItem('username'))
    .then(function ({data}) {
        console.log(data)
        birthday = data.birthday;
        email = data.email;
        followers = data.followers;
        following = data.following;
        phone=data.phone;
    })


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

    return(
        <div style={{transform: "2000px", alignItems:"center"}}>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <span style={{color:"white", fontSize:"30px"}}><b>{localStorage.getItem('username')}</b></span>
            <span style={{color:"white", fontSize:"29px"}}>'s profile.</span>

            <br/>
        
            <Button
                variant="contained"
                color="primary"
                href={"/profile/"+localStorage.getItem('username')+"/followers"}
                className={classes.button}>
                <b>Followers: </b> {followers}
            </Button>
            <Button
                variant="contained"
                color="primary"
                href={"/profile/"+localStorage.getItem('username')+"/following"}
                className={classes.button}>
                <b>Following: </b> {following}
            </Button>
            <br/>
            <div style={{display:"flex", justifyContent:"center"}}>
                <Box bgcolor='#1B3651' width={0.5} textAlign="Left" p={2} borderRadius="borderRadius" boxShadow={2}>
                    <span style = {{color:"white",fontSize:"20px"}}>About {localStorage.getItem('username')}:</span>
                    <br/>
                    <span style={{color:"white", fontSize:"15px"}}><b>Name:</b> {firstName}&nbsp;{lastName}</span>
                    <br/>
                    <span style={{color:"white", fontSize:"15px"}}><b>Birthday:</b> {birthday}</span>
                    <br/>
                    <span style={{color:"white", fontSize:"15px"}}><b>Email:</b> {email}</span>
                    <br/>
                    <span style={{color:"white", fontSize:"15px"}}><b>Phone:</b> {phone}</span>
                    <br/>
                    <span style={{color:"white", fontSize:"15px"}}><b>Role:</b> {role}</span>
                </Box>
            </div>
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