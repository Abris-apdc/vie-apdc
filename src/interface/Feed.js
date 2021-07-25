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

export class Feed extends Component {

    constructor(props) {
        super(props);
        this.state = {isLoading: true, showingInfoWindow: false};
        this.ApiCall = this.ApiCall.bind(this);
        this.displayEvents = this.displayEvents.bind(this);
    }

    ApiCall = () => {
        axios.get("https://amazing-office-313314.appspot.com/rest/map/list/")
        .then( (data) => {
            events = data.data;
            this.setState({
                isLoading: false
            });
        })
    }

    displayEvents = () => {
        return events.map((event,index) => {
            return <Marker
                name = {event.event}
                id = {index}
                onClick={this.onMarkerClick}
                position={{lat:event.coordinates.split(", ")[0], lng:event.coordinates.split(", ")[1]}}
            />
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
                            <b> Welcome to Vie!</b> <br/> Click on one of the events to get started!
                        </p>
                    </Box>
                </div>
            </div>
        )
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyB2C-13Md-w2FVlB8W-8QtALSUTbLNjXuM',
})(Feed);