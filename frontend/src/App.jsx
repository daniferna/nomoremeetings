import React from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Layout from "./routes/Layout";
import Landing from "./routes/Landing";
import UserProfile from "./UserProfile";
import ErrorPage from "./routes/ErrorPage";
import WorkHoursComponent from "./WorkHoursComponent";

function App() {

    const data = {
        "busyHours": 16.0,
        "tentativeHours": 2.0,
        "oooHours": 8.0,
        "totalWorkTime": 56.0
    };

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Landing/>}/>
                    <Route path="/userProfile" element={<UserProfile/>}/>
                    <Route path="/timeAnalysis" element={<WorkHoursComponent data={data}/>}/>
                    <Route path="*" element={<ErrorPage/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );

}

export default App;
