import React, {useEffect, useState} from 'react';
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
    MDBInput,
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
import {useNavigate} from "react-router-dom";
import {Alert, CircularProgress, FormControl, MenuItem, Select, Snackbar} from "@mui/material";

export default function UserProfile() {

    const navigate = useNavigate();
    const [user, setUser] = useState(JSON.parse(sessionStorage.getItem("user")));

    const [hoursModal, setHoursModal] = useState(false);
    const [snackbarOpen, setSnackbarOpen] = useState(false);

    const [startWorkingTime, setStartWorkingTime] = useState(dayjs(user?.startWorkingTime, 'HH:mm'));
    const [endWorkingTime, setEndWorkingTime] = useState(dayjs(user?.endWorkingTime, 'HH:mm'));
    const [startLunchTime, setStartLunchTime] = useState(dayjs(user?.startLunchTime, 'HH:mm'));
    const [endLunchTime, setEndLunchTime] = useState(dayjs(user?.endLunchTime, 'HH:mm'));
    const [timeBetweenMeetings, setTimeBetweenMeetings] = useState(user?.timeBetweenMeetings);
    const [daysToAnalyze, setDaysToAnalyze] = useState(user?.daysToAnalyze);

    const [userCalendars, setUserCalendars] = useState([]);
    const [selectedCalendar, setSelectedCalendar] = useState(null);

    const backendHost = process.env.REACT_APP_BACKEND_HOST;

    useEffect(() => {
        if (user === null || user === "null") {
            navigate("/");
        } else if (userCalendars.length === 0) {
            loadUserCalendars();
        }
    }, [user, navigate]);

    useEffect(() => {
        if (user !== null && user !== "null") {
            if (userCalendars.length > 0 && user.calendarId === "primary") {
                setSelectedCalendar(userCalendars.find(calendar => calendar.isPrimary))
            } else if (userCalendars.length > 0 && user.calendarId !== "primary") {
                setSelectedCalendar(userCalendars.find(calendar => calendar.id === user.calendarId));
            }
        }
    }, [userCalendars, user]);

    function loadUserCalendars() {
        console.log("Asking backend for user calendars");
        axios.get(backendHost + '/user/calendars', {
            auth: {
                username: null,
                password: user?.idToken
            }
        }).then((res) => {
            console.log("User calendars: " + res.data);
            setUserCalendars(res.data);
        }).catch((err) => {
            console.log(err);
        });
    }

    const openModalHours = () => setHoursModal(true);
    const closeModalHours = () => setHoursModal(false);

    function saveConfigurationChanges() {
        axios.patch(backendHost + '/user', {
                startWorkingTime: startWorkingTime.format('HH:mm'),
                endWorkingTime: endWorkingTime.format('HH:mm'),
                startLunchTime: startLunchTime.format('HH:mm'),
                endLunchTime: endLunchTime.format('HH:mm'),
                timeBetweenMeetings: timeBetweenMeetings,
                calendarId: selectedCalendar.id
            },
            {
                auth: {
                    username: null,
                    password: user?.idToken
                }
            })
            .then(res => {
                let updatedUser = res.data;
                updatedUser.idToken = user?.idToken;
                setUser(updatedUser);
                sessionStorage.setItem("user", JSON.stringify(updatedUser));
                setSnackbarOpen(true);
            })
        closeModalHours();
    }

    return (
        <section style={{backgroundColor: 'rgba(238,238,238,0.5)'}}>
            <Snackbar open={snackbarOpen} autoHideDuration={3000} onClose={() => setSnackbarOpen(false)}>
                <Alert onClose={() => setSnackbarOpen(false)} severity="success" sx={{width: '100%'}}>
                    Changes saved successfully!
                </Alert>
            </Snackbar>
            <MDBModal show={hoursModal} setShow={setHoursModal} tabIndex='-1'>
                <MDBModalDialog>
                    <MDBModalContent>
                        <MDBModalHeader>
                            <MDBModalTitle>Edit working hours</MDBModalTitle>
                            <MDBBtn className='btn-close' color='none' onClick={closeModalHours}></MDBBtn>
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
                                                    defaultValue={startLunchTime}
                                                    onChange={(newTime) => setStartLunchTime(newTime)}
                                        />
                                    </MDBCol>
                                    <MDBCol>
                                        <TimePicker label={'End lunch time'}
                                                    ampm={false}
                                                    minutesStep={5}
                                                    defaultValue={endLunchTime}
                                                    onChange={(newTime) => setEndLunchTime(newTime)}
                                        />
                                    </MDBCol>
                                </MDBRow>
                            </MDBContainer>
                        </MDBModalBody>
                        <MDBModalFooter>
                            <MDBBtn color='danger' onClick={closeModalHours}>
                                Close
                            </MDBBtn>
                            <MDBBtn onClick={saveConfigurationChanges}>
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
                                <a href='/'>Home</a>
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
                                    src={user?.urlPhoto}
                                    referrerPolicy={"no-referrer"}
                                    alt="user profile image"
                                    className="rounded-circle"
                                    style={{width: '150px'}}
                                    fluid/>
                                <p className="my-3">{user?.name}</p>
                                <p className="text-muted mb-4">{user?.startWorkingTime} - {user?.endWorkingTime}</p>
                                <div className="d-flex justify-content-center mb-2">
                                    <MDBBtn onClick={openModalHours} rounded color="primary">Edit working
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
                                        <MDBCardText className="text-muted">{user?.name}</MDBCardText>
                                    </MDBCol>
                                </MDBRow>
                                <hr/>
                                <MDBRow>
                                    <MDBCol start>
                                        <MDBCardText>Email</MDBCardText>
                                    </MDBCol>
                                    <MDBCol end>
                                        <MDBCardText className="text-muted">{user?.email}</MDBCardText>
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
                                                {user?.startWorkingTime}
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
                                                {user?.startLunchTime} - {user?.endLunchTime}
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
                                                {user?.endWorkingTime}
                                            </MDBCardText>
                                        </MDBCol>
                                    </MDBRow>
                                </MDBCardBody>
                            </MDBCard>
                            <MDBCard className="mb-4">
                                <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4"> Configuration </MDBBreadcrumb>
                                <MDBCardBody>
                                    <MDBRow center>
                                        <MDBCol style={{marginBottom: '10px'}}>
                                            <MDBCardText style={{paddingTop: '4px'}}>
                                                Time between meetings
                                            </MDBCardText>
                                        </MDBCol>
                                        <MDBCol>
                                            <MDBInput label="Minutes" type='number' id="numberSelector"
                                                      onChange={newTime => setTimeBetweenMeetings(newTime.target.value)}
                                                      defaultValue={timeBetweenMeetings}/>
                                        </MDBCol>
                                    </MDBRow>
                                    <hr/>
                                    <MDBRow center>
                                        <MDBCol style={{marginBottom: '10px'}}>
                                            <MDBCardText style={{paddingTop: '4px'}}>
                                                Number of previous days to analyze
                                            </MDBCardText>
                                        </MDBCol>
                                        <MDBCol>
                                            <MDBInput label="Days" type='number' id="numberSelector"
                                                      onChange={newTime => setDaysToAnalyze(newTime.target.value)}
                                                      defaultValue={daysToAnalyze}/>
                                        </MDBCol>
                                    </MDBRow>
                                    <hr/>
                                    <MDBRow center>
                                        <MDBCol center>
                                            <MDBCardText style={{paddingTop: '4px'}}>
                                                Selected calendar
                                            </MDBCardText>
                                        </MDBCol>
                                        <MDBCol>
                                            <FormControl fullWidth margin="dense">
                                                {userCalendars.length > 0 ?
                                                    <Select sx={{minHeight: '30px', height: '37px'}}
                                                            id="selector-calendar"
                                                            value={selectedCalendar}
                                                            onChange={(e) => setSelectedCalendar(e.target.value)}
                                                    >
                                                        {userCalendars.map((calendar) => (
                                                            <MenuItem key={calendar.id} value={calendar}>
                                                                {calendar.name}
                                                            </MenuItem>
                                                        ))}
                                                    </Select> : <CircularProgress sx={{alignSelf: "center"}}/>
                                                }
                                            </FormControl>
                                        </MDBCol>
                                    </MDBRow>
                                    <MDBRow>
                                        <MDBCol id="saveButton">
                                            <MDBBtn color='secondary' rounded onClick={saveConfigurationChanges}>
                                                Save changes
                                            </MDBBtn>
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