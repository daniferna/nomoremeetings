import React from 'react';
import {GoogleLogin, GoogleOAuthProvider, useGoogleLogin} from '@react-oauth/google';
import axios from 'axios';
import logo from './logo.svg';
import './App.css';
import {Button} from "react-bootstrap";

function App() {

    async function onSuccess(code) {
        console.log(code);
        const tokens = await axios.post('http://localhost:8080/test/google', code);

        console.log(tokens);
    }

    async function onFailure(error) {
        console.log(error);
    }

    function testClick() {
        axios.get('http://localhost:8080/test')
            .then(response => console.log(response))
            .catch(error => console.log(error));
    }

    const login = useGoogleLogin({
        onSuccess: async codeResponse => {
            console.log(codeResponse)
            const tokens = await axios.post('http://localhost:8080/test/google', {
                code: codeResponse.code,
            });

            console.log(tokens);
        },
        onError: error => {
            console.log(error)
        },
        flow: 'auth-code',
    });


    return (
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
                <p/>
                <Button onClick={login}>
                    Sign in with Google 🚀{' '}
                </Button>
            </header>
        </div>
    );
}

export default App;
