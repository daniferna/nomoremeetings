import React, {useEffect, useState} from "react";
import axios from "axios";
import {GoogleLogin, useGoogleLogin} from "@react-oauth/google";
import {MDBBtn} from "mdb-react-ui-kit";

function LoginComponent({loggedIn, setLoggedIn, setUser}) {

    const backendHost = process.env.REACT_APP_BACKEND_HOST;

    const [needLogIn, setNeedLogIn] = useState(true);
    const [needSignUp, setNeedSignUp] = useState(false);
    const [idToken, setIdToken] = useState(null);
    const [postSignUpObject, setPostSignUpObject] = useState(null);

    useEffect(() => {
        if (postSignUpObject !== null && idToken !== null && needSignUp === true) {
            console.log("Sending post-signup data to backend");
            axios.post(backendHost + '/auth/signup',
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
                    setNeedSignUp(false)
                    setNeedLogIn(false)
                    setLoggedIn(true)
                    let userWithIdToken = res.data;
                    userWithIdToken.idToken = idToken;
                    setUser(userWithIdToken)
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
        await axios.post(backendHost + '/auth/login', {},
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
                let userWithIdToken = res.data;
                userWithIdToken.idToken = code.credential;
                setUser(userWithIdToken)
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
                ? <MDBBtn color="danger" outline onClick={clickLogOut}>Logout</MDBBtn>
                : null
            }
        </div>
    );

}

export default LoginComponent;