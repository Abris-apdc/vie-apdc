import  {Component} from 'react';
import { Box, CircularProgress, Button, Link} from "@material-ui/core";
import { Map, GoogleApiWrapper, Marker, InfoWindow} from 'google-maps-react';
import axios from 'axios';
import Geocode from "react-geocode";
import InfoWindowEx from './InfoWindowEx';

const mapStyles = {
    width: '35vw',
    height: '55vh',
    left: "32.5%",
    top: "32%",
};

var lat = 38.6617658331664; 
var lng = -9.205619783468176;

Geocode.setApiKey("AIzaSyDt5oSU3zShFBQN7N2Q1AlnX2Q84Z1IpsY");

navigator.geolocation.getCurrentPosition(function(position) {
    lat = position.coords.latitude;
    lng = position.coords.longitude;
});

var events;
var allEvents;

export class MyEvents extends Component {

    constructor(props) {
        super(props);
        this.state = {isLoading: true, showingInfoWindow: false};
        this.ApiCall = this.ApiCall.bind(this);
        this.displayEvents = this.displayEvents.bind(this);
    }

    ApiCall = () => {
        if(localStorage.getItem('role') === 'ORG'){
            axios.get("https://amazing-office-313314.appspot.com/rest/map/list/"+localStorage.getItem('username'))
            .then( (data) => {
                events = data.data;
                axios.get("https://amazing-office-313314.appspot.com/rest/map/list")
                .then( (data2) => {
                    allEvents = data2.data;
                    this.setState({
                        isLoading: false
                    });
                })
            })
        }
        else{
            axios.get("https://amazing-office-313314.appspot.com/rest/event/"+localStorage.getItem('username'))
            .then( (data) => {
                events = data.data;
                axios.get("https://amazing-office-313314.appspot.com/rest/map/list")
                .then( (data2) => {
                    allEvents = data2.data;
                    this.setState({
                        isLoading: false
                    });
                })
            })
        }
    }

    displayEvents = () => {
        return events.map((event,index) => {
            for(var i = 0; i<allEvents.length; i++){
                if(event === allEvents[i].event){
                    return <Marker
                    name = {event}
                    id = {index}
                    onClick={this.onMarkerClick}
                    position={{lat:allEvents[i].coordinates.split(", ")[0], lng:allEvents[i].coordinates.split(", ")[1]}}
                />
                }
            }   
            
        })
    }

    onMarkerClick = (props, marker, _e) =>
    this.setState({
        selectedPlace: props,
        activeMarker: marker,
        showingInfoWindow: true
    });

    handleRedirect = () => {
        window.location.href = "/event/"+this.state.activeMarker.name;
    }

    render(){
        if (this.state.isLoading) {
            return <div style={{color:"white"}} className="App">
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
            <br/>Loading...<br/>
            <CircularProgress style={{color:"white"}}/>
            {this.ApiCall()}
        </div>;
        }

        return(
            <div>
                <div>
                    <Map
                        google={this.props.google}
                        style={mapStyles}
                        className={"map"}
                        zoom={10}
                        initialCenter={{ lat: lat, lng: lng}}
                        >
                        {this.displayEvents()}
                        <InfoWindowEx visible={this.state.showingInfoWindow} marker={this.state.activeMarker}>
                        <div>
                            {this.state.showingInfoWindow && <Link onClick={this.handleRedirect} style={{color:"black",fontSize:"15px"}}>{this.state.activeMarker.name}</Link>}
                        </div>
                        </InfoWindowEx>
                    </Map>
                </div>
                <br/><br/><br/><br/><br/>
                <div>
                    <Box bgcolor='#1B3651' width="45vw" height="80vh" textAlign="center" style={{margin:'auto'}} p={2} borderRadius="borderRadius" boxShadow={2}>
                        <p style={{color:"white", fontSize:"3vh"}}>
                            <b> See here your events!</b>
                            {localStorage.getItem('role') === 'ORG' && <p>Here you can see every event you have created!</p>}
                            {localStorage.getItem('role') !== 'ORG' && <p>Here you can see every event you have joined!</p>}
                        </p>
                    </Box>
                </div>
            </div>
        )
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyB2C-13Md-w2FVlB8W-8QtALSUTbLNjXuM',
})(MyEvents);