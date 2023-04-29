import React, {useState} from 'react';
import logo from './logo.svg';
import './App.css';
import Navbar from "./Navbar";
import UserProfile from "./UserProfile";

function App() {

    const [loggedIn, setLoggedIn] = useState(false);
    const [user, setUser] = useState(null);

    return (
        <div className="App">
            <Navbar loggedIn={loggedIn} setLoggedIn={setLoggedIn} setUser={setUser}/>
            {(loggedIn && user != null) ? <UserProfile user={user} setUser={setUser}/> :
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
                </header>}

        </div>
    );
}

export default App;
