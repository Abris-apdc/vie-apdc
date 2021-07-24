import  {Component} from 'react';
import { Box, CircularProgress, Button} from "@material-ui/core";
import { Map, GoogleApiWrapper, Marker, InfoWindow} from 'google-maps-react';
import axios from 'axios';

const mapStyles = {
    width: '80vh',
    height: '70vh',
    margin: 'auto',
    left: '45%'
};

var lat; 
var lng;
var address;
var org;
var duration;

export class EventPage extends Component {

    constructor(props) {
        super(props);
        this.state = {isLoading: true, showingInfoWindow: false};
        this.ApiCall = this.ApiCall.bind(this);
    }

    ApiCall = () => {
        axios.get("https://amazing-office-313314.appspot.com/rest/map/get/"+this.props.event.event)
        .then( (data) => {
            const coords = data.data.coordinates.split(", ");
            lat = coords[0];
            lng = coords[1];
            address = data.data.address;
            org = data.data.org;
            duration = data.data.duration;

            this.setState({
                isLoading: false
            });
        })
    }

    onMarkerClick = (props, marker, _e) =>
    this.setState({
        selectedPlace: props,
        activeMarker: marker,
        showingInfoWindow: true
    });

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
                zoom={14}
                onClick={this.onMapClick}
                initialCenter={{ lat: lat, lng: lng}}
                >
                    <Marker
                        onClick={this.onMarkerClick}
                        position={{lat:lat, lng:lng}}
                    />
                    <InfoWindow visible={this.state.showingInfoWindow} marker={this.state.activeMarker}>
                        <div>
                            <h1 style={{fontSize:"15px"}}>{this.props.event.event}</h1>
                        </div>
                    </InfoWindow>
                </Map>
                </div>
                <br/><br/><br/><br/><br/>
                <br/><br/><br/><br/>
                <div style={{   float: 'left', 
                                width:'55%',
                                zIndex: '1', 
                                position: 'fixed',
                                display:"flex", 
                                justifyContent:"center"}}>
                    <Box bgcolor='#1B3651' width="40vw" textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                        <span style={{color:"white", fontSize:"45px"}}><b>{this.props.event.event}</b></span>
                        <br/>
                        <br/>
                        <span style={{color:"white", fontSize:"30px"}}><b>Address: </b>{address}</span>
                        <br/>
                        <span style={{color:"white", fontSize:"30px"}}><b>Host Organization: </b>{org}</span>
                        <br/>
                        <span style={{color:"white", fontSize:"30px"}}><b>Duration: </b>{duration}</span>
                        <br/><br/><br/>
                        <Button style={{transform:"scale(1.5)"}}
                            variant="contained"
                            color="primary"
                        >
                            Join Event
                        </Button>
                        <br/><br/>
                    </Box>
                </div>
            </div>
        )
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyB2C-13Md-w2FVlB8W-8QtALSUTbLNjXuM',
})(EventPage);