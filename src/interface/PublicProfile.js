    import { Button, CircularProgress, createStyles, makeStyles } from '@material-ui/core';
    import axios from 'axios';
    import React, { useState } from 'react';
    import ArrowLeftIcon from '@material-ui/icons/ArrowLeft';
    var firstName;
    var lastName;
    var role;

    function PublicProfile(username){
        const user= JSON.stringify(username).split(':"')[1].split('"}')[0];

        const [isLoading, setLoading] = useState(true);

        const [gotError, setError] = useState(false);

        const [following, setFollowing] = useState(false);

        axios.get("https://amazing-office-313314.appspot.com/rest/getFollowing/"+localStorage.getItem('username'))
        .then(function ({data}) {
            for (var i = 0; i < data.length; i++) {
                if(data[i] === user)
                    setFollowing(true);
            }
        });

        axios.get("https://amazing-office-313314.appspot.com/rest/profile/"+user)
            .then(function ({data}) {
                firstName = data.firstName;
                lastName = data.lastName;
                role = data.role;
                localStorage.setItem('currentProfile',role);
                setLoading(false);
            }).catch(err => {
                console.log(err);
                setLoading(false);
                setError(true);
            })  

        const useStyles = makeStyles((theme) =>
            createStyles({
            button: {
            margin: theme.spacing(1),
            },
        }),
        );

        const classes = useStyles();

        function createJson(){
            return JSON.stringify({username:user, tokenID:localStorage.getItem("tokenID"), newRole:role});
        }

        function handleChangeRole(e){
            console.log(role)
            console.log(e.target.value)
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
                console.log(data);
            }else {
                window.location.reload();
            }});
        }

        function createJsonFollow(){
            return JSON.stringify({tokenID:localStorage.getItem('tokenID')})
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

        var isLoggedIn = <p/>;
        if(localStorage.getItem('username') === user)
            isLoggedIn = 
            <Button
                variant="contained"
                color="primary"
                href="/myProfile"
                className={classes.button}
                startIcon={<ArrowLeftIcon/>}>
                go back
            </Button>;

        var SU = <p/>;
        if(localStorage.getItem('role') === "SU" && role !== "SU" && role !== "ORG"){
            SU =  
            <select name={role} onChange={handleChangeRole}>
            <option value=""></option>
            <option value="USER">USER</option>
            <option value="MOD">MOD</option>
            <option value="ADMIN">ADMIN</option>
        </select>;
        }

        var ADMIN = <p/>;
        if(localStorage.getItem('role') === "ADMIN" && role !== "ADMIN" && role !== "ORG"){
            ADMIN =  
            <select name={role} onChange={handleChangeRole}>
            <option value=""></option>
            <option value="USER">USER</option>
            <option value="MOD">MOD</option>
        </select>;
        }

        var FOLLOW = <p/>;
        if(localStorage.getItem('username') !== user && !following)
            FOLLOW =  
            <Button
                variant="contained"
                color="primary"
                className={classes.button}
                onClick={handleFollow}>
                follow
            </Button>;

        var UNFOLLOW = <p/>;
        if(following)
            UNFOLLOW = 
            <Button
                variant="contained"
                color="primary"
                onClick={handleUnfollow}
                className={classes.button}>
                unfollow
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
                {SU}
                {ADMIN}
                {FOLLOW}
                {UNFOLLOW}
                {isLoggedIn}
            </div>
        )   
    }

    export default PublicProfile;