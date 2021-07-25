import { Button, CircularProgress, createStyles, makeStyles } from '@material-ui/core';
import axios from 'axios';
import React, { useState } from 'react';
import ArrowLeftIcon from '@material-ui/icons/ArrowLeft';
import { Box } from '@material-ui/core';
import { Warning } from '@material-ui/icons';
import BlockIcon from '@material-ui/icons/Block';
import AddIcon from '@material-ui/icons/Add';

var firstName;
var lastName;
var role;
var nFollowers;
var nFollowing;
var description;
var gender;
var nationality;
var language;
var email;
var birthday;
var name;
var address;
var phone;
var service;
var owner;

function PublicProfile(username){
    const user= JSON.stringify(username).split(':"')[1].split('"}')[0];

    const [isLoading, setLoading] = useState(true);

    const [gotError, setError] = useState(false);

    const [following, setFollowing] = useState(false);

    const [disabled, setDisabled] = useState(false);

    const hasAccountLogged = localStorage.getItem('tokenID') !== null;

    axios.get("https://amazing-office-313314.appspot.com/rest/getFollowing/"+localStorage.getItem('username'))
    .then(function ({data}) {
        for (var i = 0; i < data.length; i++) {
            if(data[i] === user)
                setFollowing(true);
        }
    });

    axios.get("https://amazing-office-313314.appspot.com/rest/isDisable/"+user)
    .then(function ({data}) {
        setDisabled(true);
    }).catch(err => {
        setDisabled(false);
    });

    const useStyles = makeStyles((theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );


    axios.get("https://amazing-office-313314.appspot.com/rest/profile/get/"+user)
        .then(function ({data}) {
            role = data.role;
            firstName=data.firstName;
            lastName=data.lastName;
            nFollowers = data.followers;
            nFollowing = data.following;
            description=data.description;
            gender=data.gender;
            nationality=data.nationality;
            language=data.language;
            email=data.email;
            birthday=data.birthday;
            name = data.name;
            address=data.address;
            phone=data.phone;
            service=data.service;
            owner=data.owner;
            setLoading(false);
        }).catch(err => {
            setLoading(false);
            setError(true);
        }) 

    const classes = useStyles();

    function createJson(){
        return JSON.stringify({username:user, tokenID:localStorage.getItem("tokenID"), newRole:role});
    }

    function handleChangeRole(e){
        if(e.target.value==="")
            return
        if(role !== e.target.value){
            role = e.target.value;
            fetch("https://amazing-office-313314.appspot.com/rest/changeRole/",
            {method:"PUT", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: createJson()
            })
            .then(data=> {if(!data.ok){
                alert("Something went wrong.");
                window.location.reload();
            }else {
                window.location.reload();
            }});
        }
        else
            alert("You can't change to actual role.")
    }

    function handleFollow(){
        fetch("https://amazing-office-313314.appspot.com/rest/profile/follow/"+user,
        {method:"POST", 
        headers:{ 'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json;charset=UTF-8'},
        body: createJsonFollow()
        })
        .then(data=> {if(!data.ok){
            alert("Something went wrong");
        }else {
            window.location.reload();
        }});
    }

    function handleUnfollow(){
        fetch("https://amazing-office-313314.appspot.com/rest/profile/unfollow/"+user,
        {method:"DELETE", 
        headers:{ 'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json;charset=UTF-8'},
        body: createJsonFollow()
        })
        .then(data=> {if(!data.ok){
            alert("Something went wrong");
        }else {
            window.location.reload();
        }});
    }

    function createJsonFollow(){
        return JSON.stringify({tokenID:localStorage.getItem('tokenID')})
    }

    function createJsonWarning(){
        return JSON.stringify({tokenID:localStorage.getItem('tokenID')})
    }

    function createJsonDisable(){
        return JSON.stringify({tokenID:localStorage.getItem('tokenID')})
    }

    function handleWarning(){
        fetch("https://amazing-office-313314.appspot.com/rest/warning/"+user,
        {method:"POST", 
        headers:{ 'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json;charset=UTF-8'},
        body: createJsonWarning()
        })
        .then(data=> {if(!data.ok){
            alert("Something went wrong");
        }else {
            window.location.reload();
        }});
    }

    function handleDisable(){
        fetch("https://amazing-office-313314.appspot.com/rest/disable/"+user,
        {method:"PUT", 
        headers:{ 'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json;charset=UTF-8'},
        body: createJsonDisable()
        })
        .then(data=> {if(!data.ok){
            alert("Something went wrong");
        }else {
            window.location.reload();
        }});
    }

    function handleEnable(){
        fetch("https://amazing-office-313314.appspot.com/rest/enable/"+user,
        {method:"PUT", 
        headers:{ 'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json;charset=UTF-8'},
        body: createJsonDisable()
        })
        .then(data=> {if(!data.ok){
            alert("Something went wrong");
        }else {
            window.location.reload();
        }});
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

    if(gotError){
        return <div style={{color:"white"}} className="App"><br/>No account with given username.</div>;
    }

    var info = <p/>; 
    if(role === "USER" || role === "SU" || role === "ADMIN" || role === "MOD"){
        info =
        <Box bgcolor='#1B3651' width={0.5} textAlign="Left" p={2} borderRadius="borderRadius" boxShadow={2}>
        <span style = {{color:"white",fontSize:"25px"}}>About {user}:</span>
        <br/>
        <span style={{color:"white", fontSize:"20px"}}><b>Name:</b> {firstName}&nbsp;{lastName}</span>
        <br/>
        <span style={{color:"white", fontSize:"20px"}}><b>Birthday:</b> {birthday}</span>
        <br/>
        <span style={{color:"white", fontSize:"20px"}}><b>Email:</b> {email}</span>
        <br/>
        <span style={{color:"white", fontSize:"20px"}}><b>Gender:</b> {gender}</span>
        <br/>
        <span style={{color:"white", fontSize:"20px"}}><b>Nationatlity:</b> {nationality}</span>
        <br/>
        <span style={{color:"white", fontSize:"20px"}}><b>Language:</b> {language}</span>
        <br/>
        </Box>
    } else{
        if (role === "ORG"){
        info=
        <Box bgcolor='#1B3651' width={0.5} textAlign="Left" p={2} borderRadius="borderRadius" boxShadow={2}>
        <span style = {{color:"white",fontSize:"25px"}}>About {user}:</span>
        <br/>
        <span style={{color:"white", fontSize:"20px"}}><b>Name:</b> {name}</span>
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
        </Box>
        }
    }

    var isLoggedIn = null;
    if(localStorage.getItem('username') === user)
        isLoggedIn = 
        <Button
            variant="contained"
            color="primary"
            href="/My Profile"
            className={classes.button}
            startIcon={<ArrowLeftIcon/>}>
            go back
        </Button>;

    var SU = null;
    if(localStorage.getItem('role') === "SU" && role !== "SU" && role !== "ORG"){
        SU =  
        <select name={role} onChange={handleChangeRole} value={role}>
        <option value="USER">USER</option>
        <option value="MOD">MOD</option>
        <option value="ADMIN">ADMIN</option>
    </select>;
    }

    var ADMIN = null;
    if(localStorage.getItem('role') === "ADMIN" && role !== "ADMIN" && role !== "ORG"){
        ADMIN =  
        <select name={role} onChange={handleChangeRole} value={role}>
        <option value="USER">USER</option>
        <option value="MOD">MOD</option>
    </select>;
    }

    var FOLLOW = null;
    if(localStorage.getItem('username') !== user && !following && hasAccountLogged)
        FOLLOW =  
        <Button
            variant="contained"
            color="primary"
            className={classes.button}
            onClick={handleFollow}>
            follow
        </Button>;

    var UNFOLLOW = null;
    if(following && hasAccountLogged)
        UNFOLLOW = 
        <Button
            variant="contained"
            color="primary"
            onClick={handleUnfollow}
            className={classes.button}>
            unfollow
        </Button>;
    
    var WARNING = null;
    if(localStorage.getItem('role') !== "USER" && localStorage.getItem('role') !== "ORG" && hasAccountLogged){
        WARNING = 
        <Button
            variant="contained"
            color="primary"
            className={classes.button}
            startIcon={<Warning/>}
            onClick={handleWarning}>
            Warning
        </Button>
    }

    var DISABLE = null;
    if(!disabled && localStorage.getItem('role') !== "USER" && localStorage.getItem('role') !== "MOD" && localStorage.getItem('role') !== "ORG" && hasAccountLogged){
        DISABLE = 
        <Button
            variant="contained"
            color="primary"
            className={classes.button}
            startIcon={<BlockIcon/>}
            onClick={handleDisable}>
            DISABLE
        </Button>
    }
    
    var ENABLE = null;
    if(disabled && localStorage.getItem('role') !== "USER" && localStorage.getItem('role') !== "MOD" && localStorage.getItem('role') !== "ORG" && hasAccountLogged){
        ENABLE = 
        <Button
            variant="contained"
            color="primary"
            className={classes.button}
            startIcon={<AddIcon/>}
            onClick={handleEnable}>
            ENABLE
        </Button>
    }

    return(
        
        <div>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <span style={{color:"white", fontSize:"30px"}}><b>{user}</b></span>
            <span style={{color:"white", fontSize:"29px"}}>'s profile.</span>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}> {description}</span>
            <br/>
            <span style={{color:"white", fontSize:"22px"}}><b>Followers: </b>{nFollowers}</span>
            <span style={{color:"white", fontSize:"22px"}}><b> Following: </b>{nFollowing}</span>
            <br/>
            
            {FOLLOW}
            {UNFOLLOW}
            {isLoggedIn}
            <div style={{display:"flex", justifyContent:"center"}}>
                {info}
            </div>
            <br/>
            <div>
                {WARNING}
                {DISABLE}
                {ENABLE}
                <br/><br/>
                {SU}
                {ADMIN}
            </div>
        </div>
    )   
}

export default PublicProfile;