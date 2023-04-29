import React, {useState} from 'react';
import {TimePicker} from '@mui/x-date-pickers';
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
    MDBModal,
    MDBModalBody,
    MDBModalContent,
    MDBModalDialog,
    MDBModalFooter,
    MDBModalHeader,
    MDBModalTitle,
    MDBRow
} from 'mdb-react-ui-kit';
import dayjs from "dayjs";
import axios from "axios";

export default function UserProfile({user, setUser}) {

    const [hoursModal, setHoursModal] = useState(false);
    const [startWorkingTime, setStartWorkingTime] = useState(dayjs(user.startWorkingTime, 'HH:mm'));
    const [endWorkingTime, setEndWorkingTime] = useState(dayjs(user.endWorkingTime, 'HH:mm'));
    const [lunchTime, setLunchTime] = useState(dayjs(user.lunchTime, 'HH:mm'));

    const toggleModalHours = () => setHoursModal(!hoursModal);

    function saveNewTimes() {
        axios.patch('http://localhost:8080/user', {
                startWorkingTime: startWorkingTime.format('HH:mm'),
                endWorkingTime: endWorkingTime.format('HH:mm'),
                lunchTime: lunchTime.format('HH:mm')
            },
            {
                auth: {
                    username: null,
                    password: user.idToken
                }
            })
            .then(res => {
                let updatedUser = res.data;
                updatedUser.idToken = user.idToken;
                setUser(updatedUser);
            })
        toggleModalHours();
    }

    return (
        <section style={{backgroundColor: '#eee'}}>
            <MDBModal show={hoursModal} setShow={setHoursModal} tabIndex='-1'>
                <MDBModalDialog>
                    <MDBModalContent>
                        <MDBModalHeader>
                            <MDBModalTitle>Edit working hours</MDBModalTitle>
                            <MDBBtn className='btn-close' color='none' onClick={toggleModalHours}></MDBBtn>
                        </MDBModalHeader>
                        <MDBModalBody>
                            <MDBContainer>
                                <MDBRow>
                                    <MDBCol>
                                        <TimePicker label={'Start working time'}
                                                    ampm={false}
                                                    minutesStep={5}
                                                    defaultValue={startWorkingTime}
                                                    onChange={(newTime) => setStartWorkingTime(newTime)}
                                        />
                                    </MDBCol>
                                    <MDBCol>
                                        <TimePicker label={'End working time'}
                                                    ampm={false}
                                                    minutesStep={5}
                                                    defaultValue={endWorkingTime}
                                                    onChange={(newTime) => setEndWorkingTime(newTime)}
                                        />
                                    </MDBCol>
                                </MDBRow>
                                <hr/>
                                <MDBRow>
                                    <MDBCol>
                                        <TimePicker label={'Start lunch time'}
                                                    ampm={false}
                                                    minutesStep={5}
                                                    defaultValue={lunchTime}
                                                    onChange={(newTime) => setLunchTime(newTime)}
                                        />
                                    </MDBCol>
                                </MDBRow>
                            </MDBContainer>
                        </MDBModalBody>
                        <MDBModalFooter>
                            <MDBBtn color='danger' onClick={toggleModalHours}>
                                Close
                            </MDBBtn>
                            <MDBBtn onClick={saveNewTimes}>
                                Save changes
                            </MDBBtn>
                        </MDBModalFooter>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
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
                                <p className="text-muted mb-4">{user.startWorkingTime} - {user.endWorkingTime}</p>
                                <div className="d-flex justify-content-center mb-2">
                                    <MDBBtn onClick={toggleModalHours} rounded color="primary">Edit working
                                        hours</MDBBtn>
                                </div>
                            </MDBCardBody>
                        </MDBCard>

                        <MDBCard className="mb-4 mb-lg-0">
                            <MDBCardBody className="p-0">
                                <MDBListGroup flush className="rounded-3">
                                    <MDBListGroupItem
                                        className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fas icon="globe fa-lg text-warning"/>
                                        <MDBCardText>https://example.com</MDBCardText>
                                    </MDBListGroupItem>
                                    <MDBListGroupItem
                                        className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fab icon="github fa-lg" style={{color: '#333333'}}/>
                                        <MDBCardText>Example</MDBCardText>
                                    </MDBListGroupItem>
                                    <MDBListGroupItem
                                        className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fab icon="twitter fa-lg" style={{color: '#55acee'}}/>
                                        <MDBCardText>@Example</MDBCardText>
                                    </MDBListGroupItem>
                                    <MDBListGroupItem
                                        className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fab icon="instagram fa-lg" style={{color: '#ac2bac'}}/>
                                        <MDBCardText>Example</MDBCardText>
                                    </MDBListGroupItem>
                                    <MDBListGroupItem
                                        className="d-flex justify-content-between align-items-center p-3">
                                        <MDBIcon fab icon="facebook fa-lg" style={{color: '#3b5998'}}/>
                                        <MDBCardText>Example</MDBCardText>
                                    </MDBListGroupItem>
                                </MDBListGroup>
                            </MDBCardBody>
                        </MDBCard>
                    </MDBCol>
                    <MDBCol lg="8">
                        <MDBCard className="mb-4">
                            <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4"> User
                                information </MDBBreadcrumb>
                            <MDBCardBody>
                                <MDBRow>
                                    <MDBCol start>
                                        <MDBCardText>Full Name</MDBCardText>
                                    </MDBCol>
                                    <MDBCol end>
                                        <MDBCardText className="text-muted">{user.name}</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                                <hr/>
                                <MDBRow>
                                    <MDBCol start>
                                        <MDBCardText>Email</MDBCardText>
                                    </MDBCol>
                                    <MDBCol end>
                                        <MDBCardText className="text-muted">{user.email}</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                                <hr/>
                                <MDBRow>
                                    <MDBCol start>
                                        <MDBCardText>Other row</MDBCardText>
                                    </MDBCol>
                                    <MDBCol end>
                                        <MDBCardText className="text-muted">¿?</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                            </MDBCardBody>
                        </MDBCard>
                        <MDBCol>
                            <MDBCard className="mb-4">
                                <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4"> Working
                                    Hours </MDBBreadcrumb>
                                <MDBCardBody>
                                    <MDBRow center>
                                        <MDBCol center>
                                            <MDBCardText>Start working time</MDBCardText>
                                        </MDBCol>
                                        <MDBCol end>
                                            <MDBCardText className="text-muted">
                                                {user.startWorkingTime}
                                            </MDBCardText>
                                        </MDBCol>
                                    </MDBRow>
                                    <hr/>
                                    <MDBRow center>
                                        <MDBCol start>
                                            <MDBCardText>Lunch break</MDBCardText>
                                        </MDBCol>
                                        <MDBCol end>
                                            <MDBCardText className="text-muted">
                                                {user.lunchTime}
                                            </MDBCardText>
                                        </MDBCol>
                                    </MDBRow>
                                    <hr/>
                                    <MDBRow center>
                                        <MDBCol start>
                                            <MDBCardText>End working time</MDBCardText>
                                        </MDBCol>
                                        <MDBCol end>
                                            <MDBCardText className="text-muted">
                                                {user.endWorkingTime}
                                            </MDBCardText>
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