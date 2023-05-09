import React from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import UserProfile from "./UserProfile";
import Layout from "./routes/Layout";
import ErrorPage from "./routes/ErrorPage";
import Landing from "./routes/Landing";

function App() {

    const userLoggedIn = sessionStorage.getItem("user") !== null && sessionStorage.getItem("user") !== "null";

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Landing/>}/>
                    <Route path="/userProfile" element={userLoggedIn
                        ? <UserProfile/>
                        : <Landing error={true} snackbarMessage={"You need to log in first"}/>}
                    />
                    <Route path="*" element={<ErrorPage/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
