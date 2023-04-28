import React, { useEffect, useState } from "react";
import axios from "axios";
import { GoogleLogin, useGoogleLogin } from "@react-oauth/google";
import {MDBBtn, MDBNavbarItem, MDBNavbarLink, MDBNavbarNav} from "mdb-react-ui-kit";

function LoginComponent({loggedIn, setLoggedIn, setUser}) {

    const [needLogIn, setNeedLogIn] = useState(true);
    const [needSignUp, setNeedSignUp] = useState(false);
    const [idToken, setIdToken] = useState(null);
    const [postSignUpObject, setPostSignUpObject] = useState(null);

    useEffect(() => {
        if (postSignUpObject !== null && idToken !== null && needSignUp === true) {
            console.log("Sending post-signup data to backend");
            axios.post('http://localhost:8080/auth/signup',
                {
                    codeToken: postSignUpObject.code,
                    idToken: idToken
                },
                {
                    auth: {
                        username: null,
                        password: idToken
                    }
                })
                .then((res) => {
                    console.log(res.data)
                    setNeedSignUp(false)
                    setNeedLogIn(false)
                    setLoggedIn(true)
                    setUser(res.data)
                }).catch((err) => {
                console.log(err)
            });
        }
    }, [postSignUpObject, idToken, needSignUp, setLoggedIn]);

    const signUp = useGoogleLogin({
        onSuccess: (response) => {
            console.log(response);
            setPostSignUpObject(response);
        },
        onError: (error) => {
            console.log(error);
        },
        flow: "auth-code",
        scope: 'https://www.googleapis.com/auth/calendar.readonly'
    })

    function clickLogOut() {
        console.log("Logging out");
        setNeedLogIn(true);
        setLoggedIn(false);
    }

    async function onSuccess(code) {
        setIdToken(code.credential);
        await axios.post('http://localhost:8080/auth/login', {},
            {
                auth: {
                    username: null,
                    password: code.credential
                }
            })
            .then((res) => {
                console.log(res.data)
                setNeedLogIn(false);
                setLoggedIn(true);
                setUser(res.data)
            }).catch((err) => {
                console.log(err)
                if (err.response.status === 401) {
                    console.log("Unauthorized");
                    setNeedLogIn(false);
                    setNeedSignUp(true);
                }
            });
    }

    async function onFailure(error) {
        console.log(error);
    }

    return (
        <div>
            {needLogIn
                ? <GoogleLogin onSuccess={onSuccess} onFailure={onFailure} useOneTap/>
                : null
            }
            {needSignUp
                ? <MDBBtn onClick={signUp}>Signup</MDBBtn>
                : null
            }
            {loggedIn
                ? <MDBBtn onClick={clickLogOut}>Logout</MDBBtn>
                : null
            }
        </div>
    );

}

export default LoginComponent;