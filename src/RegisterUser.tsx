import React from "react"
import { ChangeEvent, ChangeEventHandler, useState} from "react"
import './RegisterUser.css';

function RegisterForm(){
    const [firstName1, setFirstName] = useState("")
    const [lastName1, setLastName] = useState("")
    const [username1, setUserName] = useState("")
    const [email1, setEmail] = useState("")
    const [password1,setPassword] = useState("")
    const [confirmation1, setConfirmation] = useState("")

    var day ="";
    var month= ""; 
    var year ="";

    function handleChangeFirstName(e:ChangeEvent<HTMLInputElement>){
        setFirstName(e.target.value)
    }

    function handleChangeLastName(e:ChangeEvent<HTMLInputElement>){
        setLastName(e.target.value)
    }

    function handleChangeUsername(e:ChangeEvent<HTMLInputElement>){
        setUserName(e.target.value)
    }

    function handleChangeEmail(e:ChangeEvent<HTMLInputElement>){
        setEmail(e.target.value)
    }

    function handleChangePassword(e:ChangeEvent<HTMLInputElement>){
        setPassword(e.target.value)
    }

    function handleChangeConfirmation(e:ChangeEvent<HTMLInputElement>){
        setConfirmation(e.target.value)
    }

    function handleChangeDate(e:ChangeEvent<HTMLInputElement>){
        console.log(e.target.value);
        var dateArr = e.target.value.split("-")
        year=dateArr[0]
        month=dateArr[1]
        day=dateArr[2]
        console.log(day,month,year)
    }

    function registerUser(){
        console.log("Registering User");
        console.log(createJson());
        console.log(firstName1);
        fetch("https://amazing-office-313314.appspot.com/rest/register",
            {method:"POST", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: createJson()
            })
        .then(data=> {console.log(data)});
    }

    function createJson(){
        return JSON.stringify({firstName:firstName1, lastName:lastName1,  
            username:username1, email:email1, password:password1, 
            confirmation:confirmation1, year:year, month:month, day:day})
    }
   
    return <div className="container">
    <form onSubmit={(e:any) => {e.preventDefault()}}>
        <label>First Name:</label>
        <input type={"text"} value={firstName1} onChange={handleChangeFirstName}/>
        <label>Last Name:</label>
        <input type={"text"} value={lastName1} onChange={handleChangeLastName}/>
        <label>Username:</label> 
        <input type={"text"} value={username1} onChange={handleChangeUsername}/>
        <label>Email:</label>
        <input type={"text"} value={email1} onChange={handleChangeEmail}/>
        <label>Password:</label>
        <input type={"password"} value={password1} onChange={handleChangePassword}/>
        <label>Confirm Password:</label>
        <input type={"password"} value={confirmation1} onChange={handleChangeConfirmation}/>
        <label>Birthday:</label>
        <input type={"date"} onChange={handleChangeDate} />
    </form>
    <button type="button" onClick={registerUser}>Register</button>
</div>

}

export default RegisterForm