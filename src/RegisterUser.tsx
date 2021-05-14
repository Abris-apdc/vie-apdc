import { ChangeEvent, ChangeEventHandler, useState} from "react"
import './RegisterUser.css';

function RegisterForm(){
    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    const [username, setUserName] = useState("")
    const [email, setEmail] = useState("")
    const [password,setPassword] = useState("")
    const [confirmation, setConfirmation] = useState("")
    const [date, setDate] = useState("")
    const [year, setYear] = useState("")
    const [month, setMonth] = useState("")
    const [day, setDay] = useState("")

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
        setDay("1");
        setMonth("1");
        setYear("2000");
       // if(e.target.valueAsDate != null){
           // setDay(e.target.valueAsDate.getDay.toString)
            //setMonth(e.target.valueAsDate.getMonth.toString)
            //setYear(e.target.valueAsDate.getFullYear.toString)
        //}
    }

    function registerUser(){
        console.log("Registering User");
        fetch("https://amazing-office-313314.appspot.com/rest/register",
            {method:"POST", 
            headers:{ accept: "application/json"},
            body: JSON.stringify({fisrtName:firstName, lastName:lastName, 
                username:username, email:email, password:password, 
                confirmation:confirmation, year:1999, month:1, day:29 })
            })
        .then(data=> {console.log(data)});
     }
   
    return <div className="container">
        <form onSubmit={(e:any) => {e.preventDefault()}}>
            <label>First Name:</label>
            <input type={"text"} value={firstName} onChange={handleChangeFirstName}/>
            <label>Last Name:</label>
            <input type={"text"} value={lastName} onChange={handleChangeLastName}/>
            <label>Username:</label> 
            <input type={"text"} value={username} onChange={handleChangeUsername}/>
            <label>Email:</label>
            <input type={"text"} value={email} onChange={handleChangeEmail}/>
            <label>Password:</label>
            <input type={"password"} value={password} onChange={handleChangePassword}/>
            <label>Confirm Password:</label>
            <input type={"password"} value={confirmation} onChange={handleChangeConfirmation}/>
            <label>Birthday:</label>
            <input type={"date"} value={date} onChange={handleChangeDate} />
        </form>
        <button type="button" onClick={registerUser}>Register</button>
    </div>


}

export default RegisterForm