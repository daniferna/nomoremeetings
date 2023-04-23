import React from 'react';
import {
    MDBBreadcrumb,
    MDBBreadcrumbItem,
    MDBBtn,
    MDBCard,
    MDBCardBody,
    MDBCardImage,
    MDBCardText,
    MDBCol,
    MDBContainer,
    MDBIcon,
    MDBListGroup,
    MDBListGroupItem,
    MDBProgress,
    MDBProgressBar,
    MDBRow
} from 'mdb-react-ui-kit';

export default function UserProfile({user}) {
    return (
        <section style={{backgroundColor: '#eee'}}>
            <MDBContainer className="py-5">
                <MDBRow>
                    <MDBCol>
                        <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4">
                            <MDBBreadcrumbItem>
                                <a href='#'>Home</a>
                            </MDBBreadcrumbItem>
                            <MDBBreadcrumbItem>
                                <a href="#">User</a>
                            </MDBBreadcrumbItem>
                            <MDBBreadcrumbItem active>User Profile</MDBBreadcrumbItem>
                        </MDBBreadcrumb>
                    </MDBCol>
                </MDBRow>

                <MDBRow>
                    <MDBCol lg="4">
                        <MDBCard className="mb-4">
                            <MDBCardBody className="text-center">
                                <MDBCardImage
                                    src={user.urlPhoto}
                                    referrerPolicy={"no-referrer"}
                                    alt="user profile image"
                                    className="rounded-circle"
                                    style={{width: '150px'}}
                                    fluid/>
                                <p className="my-3">{user.name}</p>
                                <p className="text-muted mb-4">Lorem ipsum</p>
                                <div className="d-flex justify-content-center mb-2">
                                    <MDBBtn disabled>Button 1</MDBBtn>
                                    <MDBBtn disabled outline className="ms-1">Button 2</MDBBtn>
                                </div>
                            </MDBCardBody>
                        </MDBCard>

                        <MDBCard className="mb-4 mb-lg-0">
                            <MDBCardBody className="p-0">
                                <MDBListGroup flush className="rounded-3">
                                    <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fas icon="globe fa-lg text-warning"/>
                                        <MDBCardText>https://example.com</MDBCardText>
                                    </MDBListGroupItem>
                                    <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fab icon="github fa-lg" style={{color: '#333333'}}/>
                                        <MDBCardText>Example</MDBCardText>
                                    </MDBListGroupItem>
                                    <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fab icon="twitter fa-lg" style={{color: '#55acee'}}/>
                                        <MDBCardText>@Example</MDBCardText>
                                    </MDBListGroupItem>
                                    <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fab icon="instagram fa-lg" style={{color: '#ac2bac'}}/>
                                        <MDBCardText>Example</MDBCardText>
                                    </MDBListGroupItem>
                                    <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fab icon="facebook fa-lg" style={{color: '#3b5998'}}/>
                                        <MDBCardText>Example</MDBCardText>
                                    </MDBListGroupItem>
                                </MDBListGroup>
                            </MDBCardBody>
                        </MDBCard>
                    </MDBCol>
                    <MDBCol lg="8">
                        <MDBCard className="mb-4">
                            <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4"> User information </MDBBreadcrumb>
                            <MDBCardBody>
                                <MDBRow>
                                    <MDBCol sm="3">
                                        <MDBCardText>Full Name</MDBCardText>
                                    </MDBCol>
                                    <MDBCol sm="9">
                                        <MDBCardText className="text-muted">{user.name}</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                                <hr/>
                                <MDBRow>
                                    <MDBCol sm="3">
                                        <MDBCardText>Email</MDBCardText>
                                    </MDBCol>
                                    <MDBCol sm="9">
                                        <MDBCardText className="text-muted">{user.email}</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                                <hr/>
                                <MDBRow>
                                    <MDBCol sm="3">
                                        <MDBCardText>Other row</MDBCardText>
                                    </MDBCol>
                                    <MDBCol sm="9">
                                        <MDBCardText className="text-muted">¿?</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                            </MDBCardBody>
                        </MDBCard>
                        <MDBCol>
                            <MDBCard className="mb-4">
                                <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4"> Working Hours </MDBBreadcrumb>
                                <MDBCardBody>
                                    <MDBRow>
                                        <MDBCol sm="3">
                                            <MDBCardText>Start working time</MDBCardText>
                                        </MDBCol>
                                        <MDBCol sm="9">
                                            <MDBCardText className="text-muted">{user.startWorkingTime}</MDBCardText>
                                        </MDBCol>
                                    </MDBRow>
                                    <hr/>
                                    <MDBRow>
                                        <MDBCol sm="3">
                                            <MDBCardText>Lunch break</MDBCardText>
                                        </MDBCol>
                                        <MDBCol sm="9">
                                            <MDBCardText className="text-muted">{user.lunchTime}</MDBCardText>
                                        </MDBCol>
                                    </MDBRow>
                                    <hr/>
                                    <MDBRow>
                                        <MDBCol sm="3">
                                            <MDBCardText>End working time</MDBCardText>
                                        </MDBCol>
                                        <MDBCol sm="9">
                                            <MDBCardText className="text-muted">{user.endWorkingTime}</MDBCardText>
                                        </MDBCol>
                                    </MDBRow>
                                </MDBCardBody>
                            </MDBCard>
                        </MDBCol>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>
        </section>
    );
}