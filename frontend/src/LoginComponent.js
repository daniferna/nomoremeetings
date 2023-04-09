import React, {useEffect, useState} from "react";
import axios from "axios";
import {GoogleLogin, useGoogleLogin} from "@react-oauth/google";
import Nav from "react-bootstrap/Nav";

function LoginComponent() {

    const [loggedIn, setLoggedIn] = useState(false);
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
                }).catch((err) => {
                console.log(err)
            });
        }
    });

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
                ? <Nav.Link onClick={signUp}>Sign Up</Nav.Link>
                : null
            }
            {loggedIn
                ? <Nav.Link onClick={clickLogOut}>Log Out</Nav.Link>
                : null
            }
        </div>
    );

}

export default LoginComponent;