import { Map, GoogleApiWrapper, Marker} from 'google-maps-react';
import  {Component} from 'react';import 
{ Box, Button, Typography } from "@material-ui/core";
import CheckCircleIcon from '@material-ui/icons/CheckCircle';

const mapStyles = {
    width: '80vh',
    height: '70vh',
    top: '-53vh',
    margin: '0 auto',
};

const divSyles = {
    float: 'left',
    marginLeft: '330px',
}

const divLeft = {
    width: '80vh',
    height: '70vh',
    position: 'relative',
    top: '127px',
    margin: '0 auto',
}

const textStyles = {
    float: 'left',
    marginLeft: '-280px',
    width:"70vw"
}

var lat;
var long;

navigator.geolocation.getCurrentPosition(function(position) {
    lat = position.coords.latitude;
    long = position.coords.longitude;
});


export class MapContainer extends Component {
    constructor(props) {
        super(props);

        this.state = {
            stores: [{lat: 47.49855629475769, lng: -122.14184416996333},
                    {latitude: 47.359423, longitude: -122.021071},
                    {latitude: 47.2052192687988, longitude: -121.988426208496},
                    {latitude: 47.6307081, longitude: -122.1434325},
                    {latitude: 47.3084488, longitude: -122.2140121},
                    {latitude: 47.5524695, longitude: -122.0425407}]
        }
    }

        displayMarkers = () => {
        return this.state.stores.map((store, index) => {
            return <Marker key={index} id={index} position={{
            lat: store.latitude,
            lng: store.longitude
            }}
            onClick={() => console.log("You clicked me!")} />
        })
        }


    render() {
        return (
            <div>
                <div style={divLeft}>
                    <div style={textStyles}>
                            <br/>
                            <br/>
                            <br/>
                            <Box bgcolor='#1B3651' width={2/4} textAlign="center" p={2} borderRadius="borderRadius" boxShadow={2}>
                                <form onSubmit={(e) => {e.preventDefault()}}>
                                    <label style={{color: "white", fontSize: "30px"}}>Add New Event:</label> 
                                    <br/>
                                    <label style={{color: "white", fontSize: "20px"}}>Event Name:</label>
                                    <br/>
                                    <input type={"text"} required = {true}/>
                                    <br/>
                                    <label style={{color: "white", fontSize: "20px"}}>Coordinates:</label>
                                    <br/>
                                    <label style={{color: "white", fontSize: "15px", marginBottom: "1%", display: "block"}}>Lat: <input type={"text"} style={{width: "7vw"}} required = {true}/></label>
                                    <label style={{color: "white", fontSize: "15px"}}>Lng: <input type={"text"} style={{width: "7vw"}} required = {true}/></label>
                                    <br/>
                                    <label style={{color: "white", fontSize: "20px"}}>Address:</label>
                                    <br/>
                                    <input type={"text"} required = {true}/>
                                    <br/>
                                    <label style={{color: "white", fontSize: "20px"}}>Duration:</label>
                                    <br/>
                                    <input type={"text"} required = {true}/>
                                    <br/>
                                </form><br/>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    startIcon={<CheckCircleIcon/>}>
                                    Register
                                </Button>
                            </Box>
                    </div>
                </div>
                <div style={divSyles}>
                    <Map
                    google={this.props.google}
                    style={mapStyles}
                    initialCenter={{ lat: lat, lng: long}}
                    zoom={14}>
                        {this.displayMarkers()}
                    </Map>
                </div>
            </div>
        );
    }
}

export default GoogleApiWrapper({
apiKey: 'AIzaSyB2C-13Md-w2FVlB8W-8QtALSUTbLNjXuM',
})(MapContainer);