import React from 'react';
import {GoogleLogin, GoogleOAuthProvider} from '@react-oauth/google';
import axios from "axios";
import logo from './logo.svg';
import './App.css';
import {Button} from "react-bootstrap";

function App() {

    let idToken = "";

    const onSuccess = async (response) => {
        console.log(response);

        let credential = response.credential;
        const { access_token, refresh_token } = await axios.post(
            'http://localhost:8080/test/auth',
            { credential }
        );

        idToken = credential;

        console.log(access_token, refresh_token);
    };

    const onFailure = (error) => {
        console.error(error);
    };


    return (
        <GoogleOAuthProvider clientId="814902779569-fhqsi7036j4a3jc0v52bf0n4bfchj997.apps.googleusercontent.com">
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <p>
                        Edit <code>src/App.js</code> and save to reload.
                    </p>
                    <a
                        className="App-link"
                        href="https://reactjs.org"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        Learn React
                    </a>
                    <p/>
                    <GoogleLogin
                        buttonText="Login with Google"
                        onSuccess={onSuccess}
                        onfailure={onFailure}
                        accessType="offline"
                        responseType="code"
                    />
                    <Button onClick={exchangeIdTokenForTokens}>Pedir accessToken y refreshToken</Button>
                </header>
            </div>
        </GoogleOAuthProvider>
    );
}

export default App;
