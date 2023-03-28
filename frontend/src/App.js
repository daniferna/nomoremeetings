import React, {useState} from 'react';
import {GoogleLogin, useGoogleLogin} from '@react-oauth/google';
import axios from 'axios';
import logo from './logo.svg';
import './App.css';
import {Button, ListGroup, ListGroupItem} from "react-bootstrap";

function App() {

    const [events, setEvents] = useState([]);

    async function onSuccess(code) {
        console.log(code);
        const tokens = await axios.post('http://localhost:8080/test/google', code);

        console.log(tokens);
    }

    async function onFailure(error) {
        console.log(error);
    }

    const login = useGoogleLogin({
        onSuccess: async codeResponse => {
            console.log(codeResponse)
            const {data: events} = await axios.post('http://localhost:8080/test/google', {
                code: codeResponse.code,
            });

            console.log(events);
            setEvents(events);
        },
        onError: error => {
            console.log(error)
        },
        flow: 'auth-code',
        scope: 'https://www.googleapis.com/auth/calendar.readonly',
    });

    const eventItems = events.map((event) => {
        return (
            <ListGroupItem key={event.id} action href={event.htmlLink}>
                <h5>{event.summary}</h5>
                <p>{event.description}</p>
                <h6>{event.start.dateTime && new Date(event.start.dateTime.value).toLocaleString()}</h6>
                <h6>{event.start.date && new Date(event.start.date.value).toDateString()}</h6>
                {event.location && <p>Location: {event.location}</p>}
            </ListGroupItem>
        );
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
                <p/>
                {events.length > 0 &&
                    <>
                        <h2>Próximos eventos del calendario</h2>
                        <div>
                            <ListGroup>
                                {eventItems}
                            </ListGroup>
                        </div>
                    </>
                }
            </header>
        </div>
    );
}

export default App;
