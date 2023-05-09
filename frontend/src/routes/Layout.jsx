import {Outlet, useLocation, useNavigate} from "react-router-dom";
import React, {useEffect} from "react";
import Navbar from "../Navbar";
import Box from "@mui/material/Box";

export default function Layout() {

    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const user = JSON.parse(sessionStorage.getItem("user"));
        if (user !== "null" && location.pathname === "/userProfile") {
            console.log("Redirecting to user profile");
            navigate("/userProfile");
        } else {
            console.log("Redirecting to home");
            navigate("/");
        }
    }, [location.pathname, navigate]);

    return (
        <>
            <Navbar/>
            <Box className="App">
                <Outlet/>
            </Box>
        </>
    );
}