    import React from 'react';
    import logo from './logo.svg';
    import './App.css';
    import RegisterForm from './RegisterUser';

    function App() {
      return (
        <div className="App">
        <Head/>
        <RegisterForm/>
        </div>
      );
    }

    function Head(){
      return(
        <div 
          style={{
            backgroundColor: '#1B3651',
            width: '100%',
            height: '100px'
          }}
          >
          <img style={{position: "relative", top: "5px"}} src={logo} alt="logo" height="90%" width="90%"/>
        </div>
      );
    }

    export default App;
