import  {Component} from 'react';
import { Box, CircularProgress, Button, Link} from "@material-ui/core";
import { Map, GoogleApiWrapper, Marker, InfoWindow} from 'google-maps-react';
import axios from 'axios';
import Popup from 'reactjs-popup';
import './EventStyle.css';
import 'reactjs-popup/dist/index.css';

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
var participants;
var hasJoined = false;

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

            axios.get("https://amazing-office-313314.appspot.com/rest/event/"+this.props.event.event+"/list/")
            .then( (list) => {
                participants = list.data;
                participants.map(user => {
                    var found = false;
                    if(user === localStorage.getItem('username') && !found){
                        hasJoined = true;
                        found = true;
                    }
                })
                this.setState({
                    isLoading: false
                });
            });
        })
    }

    onMarkerClick = (props, marker, _e) =>
    this.setState({
        selectedPlace: props,
        activeMarker: marker,
        showingInfoWindow: true
    });

    handleRedirect = (user) => {
        window.location.href = "/profile/"+user;
    }

    handleJoin = () => {
        console.log(JSON.stringify({event:this.props.event.event, tokenID:localStorage.getItem('tokenID')}))
        fetch("https://amazing-office-313314.appspot.com/rest/event/join",
            {method:"POST", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: JSON.stringify({event:this.props.event.event, tokenID:localStorage.getItem('tokenID')})})
            .then(data=> {if(!data.ok){
                alert("Something went wrong");
                console.log(data);
            }else {
                window.location.reload();
            }});
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
                        <br/>
                        <span style={{color:"white", fontSize:"45px"}}><b>{this.props.event.event}</b></span>
                        <br/>
                        <br/>
                        <span style={{color:"white", fontSize:"30px"}}><b>Address: </b>{address}</span>
                        <br/>
                        <span style={{color:"white", fontSize:"30px"}}><b>Host Organization: </b>{org}</span>
                        <br/>
                        <span style={{color:"white", fontSize:"30px"}}><b>Duration: </b>{duration}</span>
                        <br/><br/><br/>
                        <div style={{width: "100%"}}>
                            <Popup trigger={
                                <Button style={{transform:"scale(1.5)", margin: "40px"}}
                                variant="contained"
                                color="primary">
                                See Participants
                            </Button>
                            } 
                            modal>
                                {close => (
                                    <div className="modal">
                                        <button className="close" onClick={close}>
                                        &times;
                                        </button>
                                        <div className="header"> Participants: </div>
                                        <div className="content">
                                        {' '}
                                        {participants.map(user => (
                                                <Link key={user} onClick={() => this.handleRedirect(user)}>{user}</Link>
                                        ))}
                                        </div>
                                    </div>
                                )}
                            </Popup>
                            {!hasJoined && <Button style={{transform:"scale(1.5)", margin: "40px"}}
                                variant="contained"
                                color="primary"
                                onClick={() => this.handleJoin()}>
                                Join Event
                            </Button>}
                            {hasJoined && <Button style={{transform:"scale(1.5)", margin: "40px"}}
                                variant="contained"
                                color="primary"
                            >
                                Leave Event
                            </Button>}
                        </div>
                    </Box>
                </div>
            </div>
        )
    }
}

export default GoogleApiWrapper({
    apiKey: 'AIzaSyB2C-13Md-w2FVlB8W-8QtALSUTbLNjXuM',
})(EventPage);