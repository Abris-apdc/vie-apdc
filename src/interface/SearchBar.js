/* eslint-disable no-use-before-define */
import React, { useState } from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import axios from 'axios';

export default function SearchBar() {

    const[value, setValue] = React.useState("");

    var [myOptions, setMyOptions] = useState([]);

    function requestAPI(){
        if(localStorage.getItem("hasRequested") === "yes"){
            myOptions = [];
        }
        axios.get("https://amazing-office-313314.appspot.com/rest/find/all")
        .then(function ({data}) {
            for (var i = 0; i < data.length; i++) {
                var next = data[i];
                myOptions.push(next);
            }
            setMyOptions(myOptions)
        })
        localStorage.setItem("hasRequested", "yes");
    }
    
    function handleRedirect(event){
		if(event.key === 'Enter'){
			document.location.href = "/profile/"+value;
	    }
    }

    return (
        <div style={{ backgroundColor: 'white', position: 'fixed', right:"5px"}}>
            <Autocomplete
            style={{width: "25vw"}}
            freeSolo
            autoComplete
            autoHighlight
            options={myOptions}
            onChange={(_event, value) => setValue(value)}
            renderInput={(params) => (
                <TextField {...params}
                    variant="filled"
                    label="Search Users"
                    onKeyPress = {handleRedirect}
                    onClick = {requestAPI}
                />
            )}
            />
        </div>
    );
}