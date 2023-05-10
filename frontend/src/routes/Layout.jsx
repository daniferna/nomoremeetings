import {Outlet} from "react-router-dom";
import React from "react";
import Navbar from "../Navbar";
import Box from "@mui/material/Box";

export default function Layout() {
    return (
        <>
            <Navbar/>
            <Box className="App">
                <Outlet/>
            </Box>
        </>
    );
}