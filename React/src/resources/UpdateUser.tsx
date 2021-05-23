import React from "react"
import { ChangeEvent, useState} from "react"
import './Register.css';

function UpdateForm(){
    const [firstName1, setFirstName] = useState("")
    const [lastName1, setLastName] = useState("")
    const [email1, setEmail] = useState("")
    const [gender1, setGender] = useState("")
    const [phone1, setPhone] = useState("")
    const [landLine1, setLandLine] = useState("")
    const [address1, setAddress] = useState("")
    const [secondAddress1, setSecondAddress] = useState("")
    const [city1, setCity] = useState("")
    const [country1, setCountry] = useState("")
    const [zip1, setZip] = useState("")
    const [nationality1, setNationality] = useState("")
    const [firstLanguage1, setFirstLanguage] = useState("")
    const [secondLanguage1, setSecondLanguage] = useState("")
    const [bio1, setBio] = useState("")
    const [education1, setEducation] = useState("")
    const [profile1, setProfile] = useState("")

    const tokenID1 = "26215a01-4506-41de-8ed7-c0a765f5e1a0"

    function handleChangeFirstName(e:ChangeEvent<HTMLInputElement>){
        setFirstName(e.target.value)
    }

    function handleChangeLastName(e:ChangeEvent<HTMLInputElement>){
        setLastName(e.target.value)
    }

    function handleChangeEmail(e:ChangeEvent<HTMLInputElement>){
        setEmail(e.target.value)
    }

    function checkEmail(){
        return email1.toString().includes("@") || email1.toString() === ""
    }

    function handleChangeGender(e:ChangeEvent<HTMLSelectElement>){
        setGender(e.target.value)
    }

    function handleChangePhone(e:ChangeEvent<HTMLInputElement>){
        setPhone(e.target.value)
    }

    function checkPhone(){
        return phone1.toString().length === 9
    }

    function handleChangeLandLine(e:ChangeEvent<HTMLInputElement>){
        setLandLine(e.target.value)
    }

    function checkLandLine(){
        return phone1.toString().length === 9
    }

    function handleChangeAddress(e:ChangeEvent<HTMLInputElement>){
        setAddress(e.target.value)
    }

    function handleChangeSecondAddress(e:ChangeEvent<HTMLInputElement>){
        setSecondAddress(e.target.value)
    }

    function handleChangeCity(e:ChangeEvent<HTMLInputElement>){
        setCity(e.target.value)
    }

    function handleChangeCountry(e:ChangeEvent<HTMLInputElement>){
        setCountry(e.target.value)
    }

    function handleChangeZip(e:ChangeEvent<HTMLInputElement>){
        setZip(e.target.value)
    }

    function handleChangeNationality(e:ChangeEvent<HTMLInputElement>){
        setNationality(e.target.value)
    }

    function handleChangeFirstLanguage(e:ChangeEvent<HTMLInputElement>){
        setFirstLanguage(e.target.value)
    }

    function handleChangeSecondLanguage(e:ChangeEvent<HTMLInputElement>){
        setSecondLanguage(e.target.value)
    }

    function handleChangeBio(e:ChangeEvent<HTMLInputElement>){
        setBio(e.target.value)
    }

    function handleChangeEducation(e:ChangeEvent<HTMLInputElement>){
        setEducation(e.target.value)
    }

    function handleChangeProfile(e:ChangeEvent<HTMLSelectElement>){
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
        if(!checkLandLine()){
            alert("Invalid Land Line Number")
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
            console.log(data);
        }});
        
    }

    function createJson(){
        return JSON.stringify({firstName:firstName1, lastName:lastName1,  
            email:email1, gender:gender1, phoneNumber:phone1,
            landLine:landLine1, address:address1, secondAddress:secondAddress1,
            city:city1, country:country1, cp:zip1, nationality:nationality1,
            firstLanguage:firstLanguage1, secondLanguage:secondLanguage1,
            description:bio1, educationLevel:education1, perfil:profile1,
            tokenID:tokenID1 })
    }

    return <div className="container">
    <form onSubmit={(e:any) => {e.preventDefault()}}>
        <label style={{color: "white"}}>First Name:</label>
        <input type={"text"} value={firstName1} onChange={handleChangeFirstName}/>
        <label style={{color: "white"}}>Last Name:</label>
        <input type={"text"} value={lastName1} onChange={handleChangeLastName}/>
        <label style={{color: "white"}}>Email:</label>
        <input type={"text"} value={email1} onChange={handleChangeEmail}/>
        <label style={{color: "white"}}>Gender:</label>
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
        <label style={{color: "white"}}>Land Line Phone Number:</label>
        <input type={"text"} value={landLine1} onChange={handleChangeLandLine}/>
        <label style={{color: "white"}}>Address:</label>
        <input type={"text"} value={address1} onChange={handleChangeAddress}/>
        <label style={{color: "white"}}>Second Address:</label>
        <input type={"text"} value={secondAddress1} onChange={handleChangeSecondAddress}/>
        <label style={{color: "white"}}>City:</label>
        <input type={"text"} value={city1} onChange={handleChangeCity}/>
        <label style={{color: "white"}} >Country:</label>
        <input type={"text"} value={country1} onChange={handleChangeCountry}/>
        <label style={{color: "white"}}>Zip Code:</label>
        <input type={"text"} value={zip1} onChange={handleChangeZip}/>
        <label style={{color: "white"}}>Nationality:</label>
        <input type={"text"} value={nationality1} onChange={handleChangeNationality}/>
        <label style={{color: "white"}}>Native Language:</label>
        <input type={"text"} value={firstLanguage1} onChange={handleChangeFirstLanguage}/>
        <label style={{color: "white"}}>Second Language:</label>
        <input type={"text"} value={secondLanguage1} onChange={handleChangeSecondLanguage}/>
        <label style={{color: "white"}}>Bio:</label>
        <input type={"text"} value={bio1} height= {50} onChange={handleChangeBio}/>
        <label style={{color: "white"}}>Education:</label>
        <input type={"text"} value={education1} onChange={handleChangeEducation}/>
        <label style={{color: "white"}}>Profile Visibility:</label>
        <select name={profile1} onChange={handleChangeProfile}>
            <option value=""></option>
            <option value="Publico">Public</option>
            <option value="Privado">Privad</option>
        </select>
    </form>
    <button type="button" onClick={updateUser}>Update</button>
</div>

}

export default UpdateForm