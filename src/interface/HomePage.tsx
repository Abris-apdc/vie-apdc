import { Box, Button, createStyles, Link, makeStyles, Theme, Typography } from '@material-ui/core';
import React from 'react';

function HomePage(){
    const useStyles = makeStyles((theme: Theme) =>
        createStyles({
        button: {
        margin: theme.spacing(1),
        },
    }),
    );
    
    const classes = useStyles();

    return(
        <div>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <div style={{display:"flex", justifyContent:"center"}}>
                <Box bgcolor='#1B3651' width={2/4} textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                    <br/>
                    <Typography variant="h2" style={{color:"white"}} ><strong>Vie</strong> by Abris</Typography>
                    <br/>
                    <Typography style={{color:"white", fontSize:"20px"}}>
                        Ever wanted to be a volunteer but didn't know where to start?
                        <br/>
                        Sign up and begin your jorney to change the world!
                    </Typography>
                    <br/>
                    <Button
                        href="/register"
                        variant="contained"
                        color="primary"
                        className={classes.button}
                    >
                        Create an account
                    </Button>
                    <br/>
                    <Link href="/login" style={{color: "white"}}> I already have an account.</Link>
                    <br/>
                    <br/>
                </Box>
            </div>
        </div>
    )
}

export default HomePage;