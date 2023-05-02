import React, {useState} from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import UserProfile from "./UserProfile";
import Layout from "./routes/Layout";
import Home from "./routes/Home";
import ErrorPage from "./routes/ErrorPage";

function App() {

    const [loggedIn, setLoggedIn] = useState(false);
    const [user, setUser] = useState(null);

    return (<BrowserRouter>
        <Routes>
            <Route path="/"
                   element={<Layout loggedIn={loggedIn} setLoggedIn={setLoggedIn} user={user} setUser={setUser}/>}>
                <Route index element={<Home/>}/>
                <Route path="/userProfile" element={<UserProfile setUser={setUser} user={user}/>}/>
                <Route path="*" element={<ErrorPage/>}/>
            </Route>
        </Routes>
    </BrowserRouter>);
}

export default App;
