import { Map, GoogleApiWrapper, Marker} from 'google-maps-react';
import  {Component} from 'react';import 
{ Box, Button } from "@material-ui/core";
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import Geocode from "react-geocode";

const mapStyles = {
    width: '80vh',
    height: '70vh',
    margin: 'auto',
    left: '45%'
};

var lat = 38.6617658331664; 
var lng = -9.205619783468176;
var address;
var name;
var duration;

Geocode.setApiKey("AIzaSyDt5oSU3zShFBQN7N2Q1AlnX2Q84Z1IpsY");

navigator.geolocation.getCurrentPosition(function(position) {
    lat = position.coords.latitude;
    lng = position.coords.longitude;
});

export class MapContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
        markers: []
        };
        this.onMapClick = this.onMapClick.bind(this);
        this.onButtonClick = this.onButtonClick.bind(this);
    }

    onMapClick(_t, _map, coord) {
        const { latLng } = coord;
        const lat2 = latLng.lat();
        const lng2= latLng.lng();
        var address2;

        Geocode.fromLatLng(lat2, lng2).then(
            (response) => {
                address2 = response.results[0].formatted_address;
                address = address2;
                this.setState({
                    markers: [
                    {
                        title: "This will be the location of your event.",
                        position: { lat: lat2, lng: lng2 },
                        address: address2
                    }
                    ]
                });
            },
            (error) => {
                console.error(error);
            }
        );
    }

    onButtonClick() {
        if(isNaN(duration) || name.toString() === "" || address === undefined){
            alert("Please correctly fill all fields.");
        }
        else{
            var addressTmp;
            var latlng;
            this.state.markers.map(function(marker){
                addressTmp = marker.address;
                latlng = marker.position.lat+", "+marker.position.lng;
                console.log(marker)
                console.log(latlng)
            })

            fetch("https://amazing-office-313314.appspot.com/rest/map",
            {method:"POST", 
            headers:{ 'Accept': 'application/json, text/plain',
            'Content-Type': 'application/json;charset=UTF-8'},
            body: JSON.stringify({  tokenID:localStorage.getItem('tokenID'),
                                    name:name,
                                    duration:duration+"h",
                                    address: addressTmp,
                                    coordinates: latlng,
                                    org: localStorage.getItem('username')
                                    })
            })
            .then(data=> {if(!data.ok){
                alert("Something went wrong");
            }else {
                window.location.reload();
            }});
        }
    }

    HandleNameChange(e){
        name = e.target.value;
    }

    HandleDurationChange(e){
        duration=e.target.value;
    }


    render() {
        return (
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
                    {this.state.markers.map((marker, index) => (
                        <Marker
                        key={index}
                        title={marker.title}
                        name={marker.name}
                        position={marker.position}
                        />
                    ))}
                </Map>
                </div>
                <div style={{float: 'left', width:'55%', zIndex: '1', position: 'fixed'}}>
                    <br/><br/><br/><br/><br/>
                    <br/><br/><br/><br/>
                    <Box bgcolor='#1B3651' width={2/3} textAlign="center" style={{margin:'auto'}}sp={2} borderRadius="borderRadius" boxShadow={2}>
                        <form onSubmit={(e) => {e.preventDefault()}}>
                            <br/>
                            <label style={{color: "white", fontSize: "30px"}}>Add New Event:</label> 
                            <br/>
                            <label style={{color: "white", fontSize: "20px"}}>Event Name:</label>
                            <br/>
                            <input type={"text"} style={{width:'25vw'}} defaultValue={name} onChange={this.HandleNameChange} required = {true}/>
                            <br/>
                            <label style={{color: "white", fontSize: "20px"}}>Click on the map to add an address:</label>
                            <br/>
                            <input type={"text"} style={{width:'25vw'}} value={address} required = {true}/>
                            <br/>
                            <label style={{color: "white", fontSize: "20px"}}>Duration: (In hours)</label>
                            <br/>
                            <input type={"text"} style={{width:'25vw'}} defaultValue={duration} onChange={this.HandleDurationChange} required = {true}/> 
                            <br/>
                        </form><br/>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={this.onButtonClick}
                            startIcon={<CheckCircleIcon/>}>
                            Submit
                        </Button>
                        <br/>
                        <br/>
                        <br/>
                    </Box>
                </div>
            </div>
        );
    }
}   

export default GoogleApiWrapper({
apiKey: 'AIzaSyB2C-13Md-w2FVlB8W-8QtALSUTbLNjXuM',
})(MapContainer);