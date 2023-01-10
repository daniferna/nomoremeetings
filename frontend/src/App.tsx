import React, { useEffect } from "react";
import { GoogleLogin } from "react-google-login";
import { gapi } from "gapi-script";
import logo from "./logo.svg";
import "./App.css";

const clientId =
  "814902779569-fhqsi7036j4a3jc0v52bf0n4bfchj997.apps.googleusercontent.com";

function App() {
  useEffect(() => {
    const initClient = () => {
      gapi.client.init({
        clientId,
        scope: "",
      });
    };
    gapi.load("client:auth2", initClient);
  });

  const onSuccess = (res: any) => {
    console.log("success:", res);
    console.log(res.tokenId);
  };
  const onFailure = (err: any) => {
    console.log("failed:", err);
  };

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <p />
        <GoogleLogin
          clientId={clientId}
          buttonText="Sign in with Google"
          onSuccess={onSuccess}
          onFailure={onFailure}
          cookiePolicy="single_host_origin"
          isSignedIn
        />
      </header>
    </div>
  );
}

export default App;
