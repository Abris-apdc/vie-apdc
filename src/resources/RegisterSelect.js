import { Box, Button, createStyles, makeStyles, Typography } from '@material-ui/core';
import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
import BusinessIcon from '@material-ui/icons/Business';
import React from 'react';

function RegisterSelect(){

    const useStyles = makeStyles((theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );

    const classes = useStyles();

    return(
        <div className="container" style={{transform: "scale(1.5)"}}>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <div style={{display:"flex", justifyContent:"center"}}>
                <Box bgcolor='#1B3651' textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                    <Typography style={{color:"white", fontSize:"20px", width:"500px"}}>
                        Which type of account would you like to Register?
                    </Typography>
                    <br/>
                    <Button
                        variant="contained"
                        color="primary"
                        href="/register_user"
                        className={classes.button}
                        startIcon={<AssignmentIndIcon/>}>
                        User
                    </Button>
                    <Button
                        variant="contained"
                        color="primary"
                        href="/register_organization"
                        className={classes.button}
                        startIcon={<BusinessIcon/>}>
                        Organization
                    </Button>
                </Box>
            </div>
        </div>
    )
}

export default RegisterSelect