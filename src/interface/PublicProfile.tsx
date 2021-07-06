    import { Button, CircularProgress, createStyles, makeStyles, Theme } from '@material-ui/core';
    import axios from 'axios';
    import React, { ChangeEvent, useState } from 'react';
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
                localStorage.setItem('currentProfile',role);
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

        function createJson(){
            console.log(user)
            console.log(localStorage.getItem("tokenID"))
            console.log(role)
            return JSON.stringify({username:user, tokenID:localStorage.getItem("tokenID"), newRole:role});
        }

        function handleChangeRole(e:ChangeEvent<HTMLSelectElement>){
            console.log(role)
            console.log(e.target.value)
            if(e.target.value=="")
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
            isLoggedIn = <Button
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
                <br/>
                {isLoggedIn}
            </div>
        )   
    }

    export default PublicProfile;