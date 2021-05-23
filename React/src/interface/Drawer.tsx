import { AppBar, Divider, Drawer, IconButton, Link, List, ListItem, ListItemText, Toolbar} from '@material-ui/core';
import { createStyles, makeStyles, Theme, useTheme } from '@material-ui/core/styles';
import React from 'react';
import clsx from 'clsx';
import logo from './../logo.svg';  
import MenuIcon from '@material-ui/icons/Menu';

const drawerWidth = 240;

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        appBar: {
            transition: theme.transitions.create(['margin', 'width'], {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen, 
            }),
        },
        appBarShift: {
            width: `calc(100% - ${drawerWidth}px)`,
            transition: theme.transitions.create(['margin', 'width'], {
                easing: theme.transitions.easing.easeOut,
                duration: theme.transitions.duration.enteringScreen,
            }),
            marginRight: drawerWidth,
        },
        hide: {
            display: 'none',
        },
        drawer: {
            width: drawerWidth,
            flexShrink: 0,
        },
        drawerPaper: {
            width: drawerWidth,
        },
        drawerHeader: {
            display: 'flex',
            alignItems: 'center',
            padding: theme.spacing(0, 1)
        },
        content: {
            flexGrow: 1,
            padding: theme.spacing(3),
            transition: theme.transitions.create('margin', {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen,
            }),
            marginRight: -drawerWidth,
        },
        contentShift: {
            transition: theme.transitions.create('margin', {
                easing: theme.transitions.easing.easeOut,
                duration: theme.transitions.duration.enteringScreen,
            }),
            marginRight: 0,
        },
    })
);

export default function PersistentDrawerRight(){
    const classes = useStyles();
    const theme = useTheme();
    const[open, setOpen] = React.useState(false);

    const handleDrawerOpen = () => {
        setOpen(true);
    };
    
    const handleDrawerClose = () => {
        setOpen(false);
    };

    var menu;
    if(open)
        menu = handleDrawerClose;
    else
        menu = handleDrawerOpen;

    return (
        <div style={{display:"flex"}}>
            <AppBar position="fixed" className={clsx(classes.appBar, {[classes.appBarShift]: open})}>
                <Toolbar style={{background:'#1B3651', display: 'flex', justifyContent:'space-between'}}>
                    <Link href="/Home" style={{color: "white"}}> 
                        <strong>Vie</strong>
                    </Link>
                    <a href="/Home"><img src={logo} alt="logo" width="55px" style={{position:"relative", top:"2px"}}/></a>
                    <IconButton color="inherit" aria-label="open drawer" edge="end" onClick={menu} className={clsx(open)}>
                        <MenuIcon/>
                    </IconButton>
                </Toolbar>
            </AppBar>
            <main className={clsx(classes.content, {[classes.contentShift]: open,})}>
                <div className={classes.drawerHeader}/>
                <br/>
            </main>
            <Drawer
                className={classes.drawer}
                variant="persistent"
                anchor="right"
                open={open}
                classes={{
                paper: classes.drawerPaper,
                }}>
                <List>
                    {['Home', 'Login', 'Register', 'About'].map((text) => (
                    <ListItem button key={text} component="a" href={text}>
                        <ListItemText primary={text} />
                    </ListItem>
                    ))}
                </List>
                <Divider/>
            </Drawer>
        </div>
    )
}