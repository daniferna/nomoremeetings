import React, {useEffect, useState} from 'react';
import {ResponsivePie} from '@nivo/pie';
import {Link, useNavigate} from "react-router-dom";
import {
    CircularProgress,
    Grid,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
    useMediaQuery
} from '@mui/material';
import axios from "axios";
import {MDBBreadcrumb, MDBBreadcrumbItem, MDBCol, MDBContainer, MDBRow} from "mdb-react-ui-kit";

function WorkHoursComponent() {

    const backendHost = process.env.REACT_APP_BACKEND_HOST;
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(true);

    const [user] = useState(JSON.parse(sessionStorage.getItem("user")));

    useEffect(() => {
        if (user === null || user === "null") {
            navigate("/");
        } else if (timeAnalysisData === null) {
            loadTimeAnalysisData();
        }
        setIsLoading(false);
    }, [user, navigate]);

    const [timeAnalysisData, setTimeAnalysisData] = useState(null);
    const [tableData, setTableData] = useState([]);
    const [pieChartData, setPieChartData] = useState([]);

    function loadTimeAnalysisData() {
        console.log("Asking backend for time analysis data");
        axios.get(backendHost + '/user/timeAnalysis', {
            auth: {
                username: null,
                password: user?.idToken
            }
        }).then((res) => {
            setTimeAnalysisData(res.data);
        }).catch((err) => {
            console.log(err);
        });
    }

    useEffect(() => {
        if (timeAnalysisData !== null) {
            setTableData([
                {id: 'Busy Hours', value: timeAnalysisData.busyHours},
                {id: 'Tentative Hours', value: timeAnalysisData.tentativeHours},
                {id: 'OOO Hours', value: timeAnalysisData.oooHours},
                {
                    id: 'Free Hours',
                    value: timeAnalysisData.totalWorkTime - timeAnalysisData.busyHours -
                        timeAnalysisData.tentativeHours - timeAnalysisData.oooHours
                },
            ]);
            // Filtrar los datos para eliminar elementos con valor 0
            setPieChartData(tableData.filter((item) => item.value !== 0));
        }
    }, [timeAnalysisData])

    const isMobile = useMediaQuery('(max-width:500px)');
    const colors = ['#c56161', '#ffd54f', '#b39ddb', '#4de192'];

    function loadingSpinner() {
        return (
            <div className="myLoadingSpinnerContainer">
                <CircularProgress className="myLoadingSpinner" size="7vh"/>
                <Typography variant="h5">
                    Loading data...
                </Typography>
            </div>
        );
    }

    if (isLoading) {
        return null;
    }

    return (
        <MDBContainer className="py-5">
            <MDBRow>
                <MDBCol>
                    <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4">
                        <MDBBreadcrumbItem>
                            <Link to='/'>Home</Link>
                        </MDBBreadcrumbItem>
                        <MDBBreadcrumbItem active>Time analysis</MDBBreadcrumbItem>
                    </MDBBreadcrumb>
                </MDBCol>
            </MDBRow>
            {pieChartData.length === 0 ? loadingSpinner() : (
                <Grid container spacing={2}>
                    <Grid item xs={12} md={6}>
                        <TableContainer component={Paper}>
                            <Typography variant="h6" component="div">
                                My work hours
                            </Typography>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell style={{fontWeight: "bolder", fontStyle: "italic"}}
                                                   align="center">Time
                                            type</TableCell>
                                        <TableCell style={{fontWeight: "bolder", fontStyle: "italic"}}
                                                   align="center">Count</TableCell>
                                        <TableCell style={{fontWeight: "bolder", fontStyle: "italic"}}
                                                   align="center">Percentage</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {tableData.map((row) => (
                                        <TableRow key={row.id}>
                                            <TableCell component="th" scope="row" align="center">
                                                {row.id}
                                            </TableCell>
                                            <TableCell align="center">{row.value}</TableCell>
                                            <TableCell
                                                align="center">{((row.value / timeAnalysisData?.totalWorkTime) * 100).toFixed(1)}%</TableCell>
                                        </TableRow>
                                    ))}
                                    <TableRow>
                                        <TableCell component="th" scope="row" align="center"
                                                   style={{fontWeight: "bold"}}>
                                            Total Work Time
                                        </TableCell>
                                        <TableCell align="center"
                                                   style={{fontWeight: "bold"}}>{timeAnalysisData?.totalWorkTime}</TableCell>
                                        <TableCell align="center" style={{fontWeight: "bold"}}>100%</TableCell>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <div style={{height: '100%', minHeight: '250px'}}>
                            <ResponsivePie
                                data={pieChartData}
                                margin={{top: 30, bottom: 30, left: 30, right: 30}}
                                innerRadius={0.5}
                                padAngle={0.7}
                                cornerRadius={3}
                                colors={colors}
                                borderWidth={1}
                                borderColor={{from: 'color', modifiers: [['darker', 0.2]]}}
                                radialLabelsSkipAngle={10}
                                radialLabelsTextColor="#333333"
                                radialLabelsLinkColor={{from: 'color'}}
                                sliceLabelsSkipAngle={10}
                                sliceLabelsTextColor="#333333"
                                animate={true}
                                motionStiffness={90}
                                motionDamping={15}
                                startAngle={90}
                                endAngle={-90}
                                enableArcLinkLabels={false}
                                activeOuterRadiusOffset={8}
                                legends={!isMobile ? [
                                    {
                                        anchor: 'bottom',
                                        direction: 'row',
                                        justify: false,
                                        translateX: 0,
                                        translateY: 20,
                                        itemsSpacing: 15,
                                        itemWidth: 100,
                                        itemHeight: 18,
                                        itemTextColor: '#999',
                                        itemDirection: 'left-to-right',
                                        itemOpacity: 1,
                                        symbolSize: 18,
                                        symbolShape: 'circle',
                                        effects: [
                                            {
                                                on: 'hover',
                                                style: {
                                                    itemTextColor: '#000'
                                                }
                                            }
                                        ]
                                    }
                                ] : []}
                            />
                        </div>
                    </Grid>
                </Grid>
            )}
        </MDBContainer>
    );
}

export default WorkHoursComponent;
