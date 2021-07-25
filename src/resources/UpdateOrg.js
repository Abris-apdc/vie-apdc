import { Box, Button, createStyles, makeStyles } from "@material-ui/core";
import React from "react"
import { useState} from "react"
import './Register.css';
import PublishIcon from '@material-ui/icons/Publish';
import axios from 'axios';

function UpdateOrgForm(){
    const [email1, setEmail] = useState("")
    const [phone1, setPhone] = useState("")
    const [address1, setAddress] = useState("")
    const [description1, setDescription] = useState("")
    const [cardID1, setCardID] = useState("")
    const [serviceType1, setServiceType] = useState("")
    const [owner1, setOwner] = useState("")
    const [country1, setCountry] = useState("")
    const tokenID1 = localStorage.getItem('tokenID');

    function checkEmail(){
        return email1.toString().includes("@") || email1.toString() === ""
    }

    function handleChangeEmail(e){
        setEmail(e.target.value)
    }

    function handleChangeServiceType(e){
        setServiceType(e.target.value)
    }


    function handleChangePhone(e){
        setPhone(e.target.value)
    }

    function checkPhone(){
        return phone1.toString().length === 9 || phone1.toString() === ""
    }

    function handleChangeAddress(e){
        setAddress(e.target.value)
    }

    function handleChangeDescription(e){
        setDescription(e.target.value)
    }

    function handleChangeCardID(e){
        setCardID(e.target.value)
    }

    function handleChangeOwner(e){
        setOwner(e.target.value)
    }

    function handleChangeCountry(e){
        setCountry(e.target.value)
    }

    function updateOrg(){
        console.log("Updating Org");
        console.log(createJson());
        if(!checkEmail()){
            alert("Not a valid Email")
            return
        }
        if(!checkPhone()){
            alert("Invalid Phone Number")
            return
        }

        fetch("https://amazing-office-313314.appspot.com/rest/update/organisation/",
            {method:"PUT", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: createJson()
            })
        .then(data=> {if(!data.ok){
            alert("Something went wrong")
        }else {
            alert("Update Successful")
            console.log(data);
            window.location.href = "/My Profile";
        }});
    }

    function createJson(){
        return JSON.stringify({
            email:email1, 
            phoneNumber:phone1,
            address:address1, 
            country:country1, 
            description:description1,
            serviceType:serviceType1,
            cardID:cardID1,
            owner:owner1,
            tokenID:tokenID1 })
    }

    const useStyles = makeStyles((theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );

    const classes = useStyles();


    return <div className="container" style={{transform: "scale(1.20)"}}>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <div style={{display:"flex", justifyContent:"center"}}>
            <Box bgcolor='#1B3651' width={1} textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                <form onSubmit={(e) => {e.preventDefault()}}>
                    <label style={{color: "white"}}>Email:</label>
                    <input type={"text"} value={email1} onChange={handleChangeEmail}/>
                    <label style={{color: "white"}}>Phone Number:</label>
                    <input type={"text"} value={phone1} onChange={handleChangePhone}/>
                    <label style={{color: "white"}}>Address:</label>
                    <input type={"text"} value={address1} onChange={handleChangeAddress}/>
                    <label style={{color: "white"}}>Country:</label>
                    <input type={"text"} value={country1} onChange={handleChangeCountry}/>
                    <label style={{color: "white"}}>Description:</label>
                    <input type={"text"} value={description1} onChange={handleChangeDescription}/>
                    <label style={{color: "white"}}>Service Type:</label>
                    <input type={"text"} value={serviceType1} onChange={handleChangeServiceType}/>
                    <label style={{color: "white"}}>Card ID:</label>
                    <input type={"text"} value={cardID1} onChange={handleChangeCardID}/>
                    <label style={{color: "white"}}>Owner:</label>
                    <input type={"text"} value={owner1} onChange={handleChangeOwner}/>
                </form>
                <br/>
                <Button
                    variant="contained"
                    color="primary"
                    className={classes.button}
                    startIcon={<PublishIcon/>}
                    onClick={updateOrg}>
                    Update
                </Button>
            </Box>
        </div>
    </div>

}

export default UpdateOrgForm