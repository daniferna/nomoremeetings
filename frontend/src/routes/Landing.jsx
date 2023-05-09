import {useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import {Alert, Snackbar, Typography} from "@mui/material";

const item = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    px: 5,
};

function Landing({error = false, snackbarMessage = null}) {

    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [message, setMessage] = useState('There has been an error');

    useEffect(() => {
        if (error) {
            setSnackbarOpen(true);
            if (snackbarMessage != null)
                setMessage(snackbarMessage);
        }
    }, [error, snackbarMessage]);

    return (
        <>
            <Snackbar
                anchorOrigin={{vertical: 'top', horizontal: 'left'}}
                open={snackbarOpen}
                autoHideDuration={2000}
                onClose={() => setSnackbarOpen(false)}
                key={message}
            >
                <Alert variant="filled" onClose={() => setSnackbarOpen(false)} severity="warning">
                    {message}
                </Alert>
            </Snackbar>
            <Container sx={{mt: 15, mb: 30, display: 'flex', position: 'relative'}}>
                <Grid container spacing={5}>
                    <Grid item xs={12} md={4}>
                        <Box sx={item}>
                            <Box
                                component="img"
                                src="calendar.svg"
                                alt="calendar"
                                sx={{height: 65}}
                            />
                            <Typography variant="h6" sx={{my: 4}}>
                                Analyze your meetings
                            </Typography>
                            <Typography variant="h5">
                                Analyze your calendar and find out how much time you spend in meetings.
                            </Typography>
                        </Box>
                    </Grid>
                    <Grid item xs={12} md={4}>
                        <Box sx={item}>
                            <Box
                                component="img"
                                src="google.svg"
                                alt="google"
                                sx={{height: 65}}
                            />
                            <Typography variant="h6" sx={{my: 5}}>
                                Use your Google account
                            </Typography>
                            <Typography variant="h5">
                                Start using the app is as easy as signing in with your Google account.
                            </Typography>
                        </Box>
                    </Grid>
                    <Grid item xs={12} md={4}>
                        <Box sx={item}>
                            <Box
                                component="img"
                                src="graph.svg"
                                alt="graph"
                                sx={{height: 65}}
                            />
                            <Typography variant="h6" sx={{my: 5}}>
                                Understand your work time
                            </Typography>
                            <Typography variant="h5">
                                See your data in a visual way and understand how you spend your time.
                            </Typography>
                        </Box>
                    </Grid>
                </Grid>
            </Container>
        </>
    );
}

export default Landing;
