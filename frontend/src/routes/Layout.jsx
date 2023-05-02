import {Outlet, useNavigate} from "react-router-dom";
import React, {useEffect} from "react";
import Navbar from "../Navbar";

export default function Layout({loggedIn, setLoggedIn, user, setUser}) {

    const navigate = useNavigate();

    useEffect(() => {
        if (loggedIn && user != null) {
            console.log("Redirecting to user profile");
            navigate("/userProfile");
        } else {
            console.log("Redirecting to home");
            navigate("/");
        }
    }, [loggedIn, navigate, user]);

    return (
        <>
            <Navbar loggedIn={loggedIn} setLoggedIn={setLoggedIn} setUser={setUser}/>
            <Outlet/>
        </>
    );
}