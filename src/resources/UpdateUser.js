import { Box, Button, createStyles, makeStyles } from "@material-ui/core";
import React from "react"
import { useState} from "react"
import './Register.css';
import PublishIcon from '@material-ui/icons/Publish';

function UpdateForm(){
    const [firstName1, setFirstName] = useState("")
    const [lastName1, setLastName] = useState("")
    const [email1, setEmail] = useState("")
    const [gender1, setGender] = useState("")
    const [phone1, setPhone] = useState("")
    const [address1, setAddress] = useState("")
    const [nationality1, setNationality] = useState("")
    const [firstLanguage1, setFirstLanguage] = useState("")
    const [bio1, setBio] = useState("")
    const [profile1, setProfile] = useState("")

    const tokenID1 = localStorage.getItem('tokenID');

    function handleChangeFirstName(e){
        setFirstName(e.target.value)
    }

    function handleChangeLastName(e){
        setLastName(e.target.value)
    }

    function handleChangeEmail(e){
        setEmail(e.target.value)
    }

    function checkEmail(){
        return email1.toString().includes("@") || email1.toString() === ""
    }

    function handleChangeGender(e){
        setGender(e.target.value)
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

    function handleChangeNationality(e){
        setNationality(e.target.value)
    }

    function handleChangeFirstLanguage(e){
        setFirstLanguage(e.target.value)
    }

    function handleChangeBio(e){
        setBio(e.target.value)
    }

    function handleChangeProfile(e){
        setProfile(e.target.value)
    }

    function updateUser(){
        console.log("Updating User");
        console.log(createJson());
        console.log(gender1)
        if(!checkEmail()){
            alert("Not a valid Email")
            return
        }
        if(!checkPhone()){
            alert("Invalid Phone Number")
            return
        }
        
        fetch("https://amazing-office-313314.appspot.com/rest/update",
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
        return JSON.stringify({firstName:firstName1, lastName:lastName1,  
            email:email1, gender:gender1, phoneNumber:phone1,
            address:address1, nationality:nationality1,
            firstLanguage:firstLanguage1,
            description:bio1, perfil:profile1,
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
                    <label style={{color: "white"}}>First Name:</label>
                    <input type={"text"} value={firstName1} onChange={handleChangeFirstName}/>
                    <label style={{color: "white"}}>Last Name:</label>
                    <input type={"text"} value={lastName1} onChange={handleChangeLastName}/>
                    <label style={{color: "white"}}>Email:</label>
                    <input type={"text"} value={email1} onChange={handleChangeEmail}/>
                    <label style={{color: "white"}}>Gender:</label>´
                    <br/>
                    <select name={gender1} onChange={handleChangeGender}>
                        <option value=""></option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Non-Binary">Non-Binary</option>
                        <option value="Other">Other</option>
                    </select>
                    <br/>
                    <label style={{color: "white"}}>Phone Number:</label>
                    <input type={"text"} value={phone1} onChange={handleChangePhone}/>
                    <label style={{color: "white"}}>Address:</label>
                    <input type={"text"} value={address1} onChange={handleChangeAddress}/>
                    <label style={{color: "white"}}>Nationality:</label>
                    <input type={"text"} value={nationality1} onChange={handleChangeNationality}/>
                    <label style={{color: "white"}}>Language:</label>
                    <input type={"text"} value={firstLanguage1} onChange={handleChangeFirstLanguage}/>
                    <label style={{color: "white"}}>Bio:</label>
                    <input type={"text"} value={bio1} height= {50} onChange={handleChangeBio}/>
                    <label style={{color: "white"}}>Profile Visibility:</label>
                    <br/>
                    <select name={profile1} onChange={handleChangeProfile}>
                        <option value=""></option>
                        <option value="Public">Public</option>
                        <option value="Private">Private</option>
                    </select>
                </form>
                <br/>
                <Button
                    variant="contained"
                    color="primary"
                    className={classes.button}
                    startIcon={<PublishIcon/>}
                    onClick={updateUser}>
                    Update
                </Button>
            </Box>
        </div>
    </div>

}

export default UpdateForm