import React, {useEffect, useState} from "react";
import axios from "axios";
import {GoogleLogin, useGoogleLogin} from "@react-oauth/google";
import {MDBBtn} from "mdb-react-ui-kit";
import {useNavigate} from "react-router-dom";
import {Alert, Snackbar} from "@mui/material";

function LoginComponent() {

    const backendHost = process.env.REACT_APP_BACKEND_HOST;

    const [user, setUser] = useState(JSON.parse(sessionStorage.getItem("user")));
    const navigate = useNavigate();

    const [needSignUp, setNeedSignUp] = useState(false);
    const [idToken, setIdToken] = useState(null);
    const [postSignUpObject, setPostSignUpObject] = useState(null);

    function setUserSessionStorage(user) {
        sessionStorage.setItem("user", JSON.stringify(user));
        setUser(user);
    }

    useEffect(() => {
        if (needSignUp && postSignUpObject === null) {
            signUp();
        }
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
                    let userWithIdToken = res.data;
                    userWithIdToken.idToken = idToken;
                    setUserSessionStorage(userWithIdToken)
                }).catch((err) => {
                console.log(err)
            });
        }
    }, [postSignUpObject, idToken, needSignUp, backendHost]);

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
        setUserSessionStorage(null);
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
                let userWithIdToken = res.data;
                userWithIdToken.idToken = code.credential;
                setUserSessionStorage(userWithIdToken)
                navigate("/userProfile");
            }).catch((err) => {
                console.log(err)
                if (err.response.status === 401) {
                    console.log("Unauthorized");
                    setNeedSignUp(true);
                }
            });
    }

    async function onFailure(error) {
        console.log(error);
    }

    return (
        <div>
            {
                (user == null || user === "null") && !needSignUp
                    ? <GoogleLogin onSuccess={onSuccess} onFailure={onFailure} useOneTap/>
                    : null
            }
            {
                needSignUp ?
                    <>
                        <Snackbar open={needSignUp} anchorOrigin={{vertical: 'bottom', horizontal: 'center'}}>
                            <Alert severity='info'>
                                You need to sign up first
                            </Alert>
                        </Snackbar>
                        <MDBBtn onClick={signUp}>Signup</MDBBtn>
                    </>
                    : null
            }
            {
                user != null && user !== "null"
                    ? <MDBBtn color="danger" outline onClick={clickLogOut}>Logout</MDBBtn>
                    : null
            }
        </div>
    );

}

export default LoginComponent;