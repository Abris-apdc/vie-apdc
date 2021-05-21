import { Box, Typography } from '@material-ui/core';
import React from 'react';
import EmailIcon from '@material-ui/icons/Email';

function AboutUsPage(){
    return(
        <div>
            <br/>
            <br/>
            <div style={{display:"flex", justifyContent:"center"}}>
                <Box bgcolor='#1B3651' width={2/4} textAlign="justify" p={2} borderRadius="borderRadius" boxShadow={2}>
                    <span style={{color:"white", fontSize:"50px", display:"flex", justifyContent:"flex-start"}}><strong>Welcome to Vie!</strong></span>
                    <br/>
                    <Typography style={{color:"white", fontSize:"20px"}}>
                        Vie is an app that has as a goal to organize and to make it acessible to make volunteering. 
                        <br/><br/>
                        Our team, Abris, is composed by 5 portuguese computer science students from Faculdade de Ciências 
                        e Tecnologia da Universidade Nova de Lisboa, who where given the challenge by the teachers to make 
                        an app that could change the world.
                        <br/><br/>
                        Have any recomendation? Feel free to send us an email!
                        <br/><br/>
                        <EmailIcon/> &nbsp; abris.apdc@gmail.com
                    </Typography>
                </Box>
            </div>
        </div>
    )
}

export default AboutUsPage;