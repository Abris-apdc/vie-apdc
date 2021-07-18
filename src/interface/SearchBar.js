/* eslint-disable no-use-before-define */
import React, { useState } from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import axios from 'axios';

export default function SearchBar() {

    var text = "";

    var [myOptions, setMyOptions] = useState([]);

    const getDataFromAPI = () => {}

    function requestAPI(){
        if(localStorage.getItem("hasRequested") === "yes"){
            myOptions = [];
        }
        axios.get("https://amazing-office-313314.appspot.com/rest/find/all")
        .then(function ({data}) {
            console.log(data);
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
			document.location.href = "/profile/"+text;
	    }
    }

    function updateInput(value){
        text = value;
    }

    return (
        <div style={{ width:500, backgroundColor: 'white'}}>
            <Autocomplete
            style={{ width: 500 }}
            freeSolo
            autoComplete
            autoHighlight
            options={myOptions}
            onChange={(value) => updateInput(value)}
            renderInput={(params) => (
                <TextField {...params}
                    onChange={getDataFromAPI}
                    variant="filled"
                    label="Search Box"
                    onKeyPress = {handleRedirect}
                    onClick = {requestAPI}
                />
            )}
            />
        </div>
    );
}