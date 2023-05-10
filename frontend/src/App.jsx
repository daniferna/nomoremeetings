import React from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./routes/Layout";
import Landing from "./routes/Landing";
import UserProfile from "./UserProfile";
import ErrorPage from "./routes/ErrorPage";

function App() {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Landing/>}/>
                    <Route path="/userProfile" element={<UserProfile/>}/>
                    <Route path="*" element={<ErrorPage/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );

}

export default App;
