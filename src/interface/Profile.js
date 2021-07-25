import { Button, CircularProgress, createStyles, makeStyles, Link} from '@material-ui/core';
import axios from 'axios';
import { Box } from '@material-ui/core';
import React, { useState } from 'react';
import InfoIcon from '@material-ui/icons/Info';
import DeleteIcon from '@material-ui/icons/Delete';
import VpnKeyIcon from '@material-ui/icons/VpnKey';
import VisibilityIcon from '@material-ui/icons/Visibility';
import Popup from 'reactjs-popup';
import './EventStyle.css';
import 'reactjs-popup/dist/index.css';

var firstName;
var lastName;
var birthday;
var email;
var followers;
var following;
var followersList;
var followingList;
var phone;
var address;
var gender;
var nationality;
var visibility;
var warnings;
var description;
var language;
var name;
var cardID;
var owner;
var service;

function Profile(){
    const [isLoading, setLoading] = useState(true);

    const useStyles = makeStyles((theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );

    const classes = useStyles();

    var info = <p/>; 
    var role = localStorage.getItem('role')
    if(role === "USER" || role === "SU" || role === "ADMIN" || role === "MOD"){
        axios.get("https://amazing-office-313314.appspot.com/rest/profile/get/"+localStorage.getItem('username'))
        .then(function ({data}) {
            firstName = data.firstName;
            lastName = data.lastName;
            role = data.role;
            birthday = data.birthDay;
            email = data.email;
            followers = data.followers;
            following = data.following;
            phone = data.phone;
            address = data.address;
            gender=data.gender;
            nationality=data.nationality;
            visibility=data.perfil;
            warnings=data.warnings;
            description=data.description;
            language=data.firstLanguage;
            axios.get("https://amazing-office-313314.appspot.com/rest/getFollowers/"+localStorage.getItem('username'))
            .then(function ({data}) {
                followersList = data;
                axios.get("https://amazing-office-313314.appspot.com/rest/getFollowing/"+localStorage.getItem('username'))
            .then(function ({data}) {
                followingList = data; 
                setLoading(false);
            });
            });
        })
        info = 
        <Box bgcolor='#1B3651' width={0.5} textAlign="Left" p={2} borderRadius="borderRadius" boxShadow={2}>
            <span style = {{color:"white",fontSize:"25px"}}>About {localStorage.getItem('username')}:</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Name:</b> {firstName}&nbsp;{lastName}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Birthday:</b> {birthday}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Email:</b> {email}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Phone:</b> {phone}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Address:</b> {address}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Gender:</b> {gender}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Nationatlity:</b> {nationality}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Language:</b> {language}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Visibility:</b> {visibility}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Warnings:</b> {warnings}</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}><b>Role:</b> {role}</span>
        </Box>
    } else{
        if(role === "ORG"){
            axios.get("https://amazing-office-313314.appspot.com/rest/profile/get/"+localStorage.getItem('username'))
            .then(function ({data}) {
            setLoading(false);
            address = data.address;
            cardID = data.cardID;
            email = data.email;
            followers = data.followers;
            following = data.following;
            name=data.name;
            owner=data.owner;
            role = data.role;
            service = data.service;
            warnings=data.warnings;
            phone = data.phone;
            description=data.description;
        })
            info =
            <Box bgcolor='#1B3651' width={0.5} textAlign="Left" p={2} borderRadius="borderRadius" boxShadow={2}>
                <span style = {{color:"white",fontSize:"25px"}}>About {localStorage.getItem('username')}:</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Name:</b> {name}</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Card ID:</b> {cardID}</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Email:</b> {email}</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Phone:</b> {phone}</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Address:</b> {address}</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Service:</b> {service}</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Owner:</b> {owner}</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Visibility:</b> {visibility}</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Warnings:</b> {warnings}</span>
                <br/>
                <span style={{color:"white", fontSize:"20px"}}><b>Role:</b> {role}</span>
            </Box>
        }
    }

    function handleRedirect(user) {
        document.location.href = "/profile/"+user;
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
        <div style={{transform: "2000px", alignItems:"center"}}>
            <br/>
            <br/>
            <br/>
            <br/>
            <span style={{color:"white", fontSize:"30px"}}><b>{localStorage.getItem('username')}</b></span>
            <span style={{color:"white", fontSize:"29px"}}>'s profile.</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}> {description}</span>
            <br/>
            <Popup trigger={
                <Button
                    variant="contained"
                    color="primary"
                    className={classes.button}>
                    <b>Followers: </b> {followers}
                </Button>
                } 
                modal>
                {close => (
                    <div className="modal">
                        <button className="close" onClick={close}>
                        &times;
                        </button>
                        <div className="header"> Followers: </div>
                        <div className="content">
                        {' '}
                        {followersList.length === 0 && <p>No followers yet! Much Empty</p>}
                        {followersList.map(user => (
                                <div>
                                    <Link key={user} onClick={() => handleRedirect(user)}>{user}</Link>
                                    <br/>
                                </div>
                        ))}
                        </div>
                    </div>
                )}
            </Popup>
            <Popup trigger={
                <Button
                    variant="contained"
                    color="primary"
                    className={classes.button}>
                    <b>Following: </b> {following}
                </Button>
                } 
                modal>
                {close => (
                    <div className="modal">
                        <button className="close" onClick={close}>
                        &times;
                        </button>
                        <div className="header"> Following: </div>
                        <div className="content">
                        {' '}
                        {followingList.length === 0 && <p>No following yet! Much Empty</p>}
                        {followingList.map(user => (
                                <div>
                                    <Link key={user} onClick={() => handleRedirect(user)}>{user}</Link>
                                    <br/>
                                </div>
                        ))}
                        </div>
                    </div>
                )}
            </Popup>
            <br/>
            <br/>
            <div style={{display:"flex", justifyContent:"center"}}>
                {info}
            </div>
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
            <Button
                variant="contained"
                color="primary"
                href="/password"
                style={{width: "210px"}}
                className={classes.button}
                startIcon={<VpnKeyIcon/>}>
                change password
            </Button>
            <Button
                variant="contained"
                color="primary"
                href="/delete"
                style={{width: "210px"}}
                className={classes.button}
                startIcon={<DeleteIcon/>}>
                Delete account
            </Button>
            <br/>
            <Button
                variant="contained"
                color="primary"
                href={"/profile/"+localStorage.getItem('username')}
                className={classes.button}
                startIcon={<VisibilityIcon/>}>
                Check how your profile looks to public eye!
            </Button>
        </div>
    )
}

export default Profile;