import { Map, GoogleApiWrapper, Marker} from 'google-maps-react';
import  {Component} from 'react';

const mapStyles = {
    width: '80vh',
    height: '70vh',
    margin: '0 auto',
};

const divSyles = {
    float: 'left',
    marginLeft: '310px',
}

var lat = 0;
var long = 0;

navigator.geolocation.getCurrentPosition(function(position) {
    console.log("Latitude is :", position.coords.latitude);
    lat = position.coords.latitude;
    console.log("Longitude is :", position.coords.longitude);
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
            <div style={divSyles}>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <Map
                google={this.props.google}
                style={mapStyles}
                initialCenter={{ lat: lat, lng: long}}
                zoom={14}>
                    {this.displayMarkers()}
                </Map>
            </div>
        );
    }
}

export default GoogleApiWrapper({
apiKey: 'AIzaSyB2C-13Md-w2FVlB8W-8QtALSUTbLNjXuM',
})(MapContainer);