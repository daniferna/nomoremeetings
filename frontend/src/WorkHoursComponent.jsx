import React from 'react';
import {ResponsivePie} from '@nivo/pie';
import {
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

function WorkHoursComponent({data}) {

    const isMobile = useMediaQuery('(max-width:500px)');

    const totalWorkTime = data.totalWorkTime;
    const freeTime = totalWorkTime - data.busyHours - data.tentativeHours - data.oooHours;
    let tableData = [
        {id: 'Busy Hours', value: data.busyHours},
        {id: 'Tentative Hours', value: data.tentativeHours},
        {id: 'OOO Hours', value: data.oooHours},
        {id: 'Free Hours', value: freeTime},
    ];

    // Filtrar los datos para eliminar elementos con valor 0
    const pieChartData = tableData.filter((item) => item.value !== 0);
    const colors = ['#c56161', '#ffd54f', '#b39ddb', '#4de192']; // Tono pastel para Morado, Amarillo, Verde, Azul

    return (
        <Grid container spacing={2}>
            <Grid item xs={12} margin='12px'>
                <Typography variant="h4" component="h1">
                    Time analysis (MOCK DATA)
                </Typography>
            </Grid>
            <Grid item xs={12} md={6}>
                <TableContainer component={Paper}>
                    <Typography variant="h6" component="div">
                        My work hours
                    </Typography>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell style={{fontWeight: "bolder", fontStyle: "italic"}} align="center">Time
                                    type</TableCell>
                                <TableCell style={{fontWeight: "bolder", fontStyle: "italic"}}
                                           align="right">Count</TableCell>
                                <TableCell style={{fontWeight: "bolder", fontStyle: "italic"}}
                                           align="right">Percentage</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {tableData.map((row) => (
                                <TableRow key={row.id}>
                                    <TableCell component="th" scope="row" align="center">
                                        {row.id}
                                    </TableCell>
                                    <TableCell align="right">{row.value}</TableCell>
                                    <TableCell
                                        align="right">{((row.value / totalWorkTime) * 100).toFixed(1)}%</TableCell>
                                </TableRow>
                            ))}
                            <TableRow>
                                <TableCell component="th" scope="row" align="center" style={{fontWeight: "bold"}}>
                                    Total Work Time
                                </TableCell>
                                <TableCell align="right" style={{fontWeight: "bold"}}>{totalWorkTime}</TableCell>
                                <TableCell align="right" style={{fontWeight: "bold"}}>100%</TableCell>
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
    );
}

export default WorkHoursComponent;
