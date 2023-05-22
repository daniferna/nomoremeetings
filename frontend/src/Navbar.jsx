import {
    MDBCollapse,
    MDBContainer,
    MDBIcon,
    MDBNavbar,
    MDBNavbarBrand,
    MDBNavbarItem,
    MDBNavbarNav,
    MDBNavbarToggler,
} from 'mdb-react-ui-kit';
import LoginComponent from "./LoginComponent";
import {useState} from "react";
import {Link} from "react-router-dom";

function AppMDBNavbar() {

    const [showBasic, setShowBasic] = useState(false);

    return (
        <MDBNavbar expand='lg' light bgColor='light'>
            <MDBContainer fluid>
                <MDBNavbarBrand>
                    <Link className="navbarLinkBrand" to="/">
                        <img src="/logoNoMoreMeetings.svg" loading={"lazy"} height={30}></img>
                        NoMoreMeetings
                    </Link>
                </MDBNavbarBrand>

                <MDBNavbarToggler
                    aria-controls='navbarSupportedContent'
                    aria-expanded='false'
                    aria-label='Toggle navigation'
                    onClick={() => setShowBasic(!showBasic)}
                >
                    <MDBIcon icon='bars' fas/>
                </MDBNavbarToggler>

                <MDBCollapse navbar show={showBasic}>
                    <MDBNavbarNav className='mr-auto mb-2 mb-lg-0'>
                        <MDBNavbarItem>
                            <Link to="/" className="navbarLink, nav-link">
                                Home
                            </Link>
                        </MDBNavbarItem>
                        <MDBNavbarItem>
                            <Link to='/userProfile' className="navbarLink, nav-link">
                                User profile
                            </Link>
                        </MDBNavbarItem>
                        <MDBNavbarItem>
                            <Link to='/timeAnalysis' aria-current='page' className="navbarLink, nav-link">
                                Time analysis
                            </Link>
                        </MDBNavbarItem>
                    </MDBNavbarNav>
                    <LoginComponent/>
                </MDBCollapse>
            </MDBContainer>
        </MDBNavbar>
    );
}

export default AppMDBNavbar;