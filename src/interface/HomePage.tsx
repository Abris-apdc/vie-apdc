import { Link } from '@material-ui/core';
import React from 'react';

function HomePage(){
    return(
        <div>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <span style={{color:"white", fontSize:"50px"}}><strong>Vie</strong></span>
            <span style={{color:"white", fontSize:"35px"}}>&nbsp;by Abris</span>
            <br/>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}>Ever wanted to be a volunteer but didn't know where to start?</span>
            <br/>
            <br/>
            <span style={{color:"white", fontSize:"20px"}}>Sign up to begin changing the world!</span>
            <br/>
            <br/>
            <a href="/Register"><button style={{padding: "15px 55px",  fontSize:"20px"}}>Sign up</button></a>
            <br/>
            <Link href="/Login" style={{color: "white"}}> I already have an account.</Link>
        </div>
    )
}

export default HomePage;