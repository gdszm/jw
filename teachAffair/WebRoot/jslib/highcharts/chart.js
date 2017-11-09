	//折线图
	function line(id, title, xAxis, data) {
	    $('#'+ id).highcharts({
	        title: {
	            text: title,
	            x: -20 //center
	        },
	        xAxis: {
	            categories: xAxis
	        },
	        yAxis: {
	            title: {
	                text: '百分比%'
	            }	            
	        },
	        tooltip: {
	            valueSuffix: '%'
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true
	                }
	            }
	        },
	        series:data
	    });
	}

	//type: 'column'柱形图
	function column(id, title, xAxis, data) {
	    $('#'+ id).highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: title
	        },
	        xAxis: {
	            categories: xAxis,
	            crosshair: true
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '百分比%'
	            }
	        },
	        tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
	        },
	        plotOptions: {
	            column: {
	                pointPadding: 0.2,
	                borderWidth: 0
	            }
	        },
	        series: data
	    });
	}

	//type: 'area'面积图
	function area(id, title, xAxis, data) {
	    $('#'+ id).highcharts({
	        chart: {
            	type: 'area'
        	},
        	title: {
	            text: title,
	            x: -20 //center
	        },
	        xAxis: {
	            categories: xAxis
	        },
	        yAxis: {
	            title: {
	                text: '百分比%'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: data
	    });
	}
    
	//type: 'column2'柱形图2
	function column2(id, title, xAxis, data) {
		$('#'+ id).highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: title
	        },
	        xAxis: {
	            categories: xAxis
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '百分比%'
	            }
	        },
	        tooltip: {
	            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.percentage:.0f}%)<br/>',
	            shared: true
	        },
	        plotOptions: {
	            column: {
	                stacking: 'percent'
	            }
	        },
	        series: data
	    });
	}

	//半圆图
	function donut(id, title, xAxis, data) {
	    $('#'+ id).highcharts({
	        chart: {
	            type: 'pie',
	            innerSize: '50%',
	            plotBackgroundColor: null,
	            plotBorderWidth: 0,
	            plotShadow: false
	        },
	        title: {
	            text: title,
	            align: 'center',
	            verticalAlign: 'middle',
	            y: 50
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                dataLabels: {
	                    enabled: true,
	                    distance: -50,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    style: {
	                        fontWeight: 'bold',
	                        color: 'white',
	                        textShadow: '0px 1px 2px black'
	                    }
	                },
	                startAngle: -90,
	                endAngle: 90,
	                center: ['50%', '75%']
	            }
	        },
	        series: data
	    });
	}

	//雷达图
	function spider(id, title, xAxis, data) {
	    $('#'+ id).highcharts({
	        chart: {
	            polar: true,
	            type: 'line'
	        },
	
	        title: {
	            text: title,
	            x: -80
	        },
	
	        pane: {
	            size: '80%'
	        },
	
	        xAxis: {
	            categories: xAxis,
	            tickmarkPlacement: 'on',
	            lineWidth: 0
	        },
	
	        yAxis: {
	            gridLineInterpolation: '百分比%',
	            lineWidth: 0,
	            min: 0
	        },
	
	        tooltip: {
	            shared: true,
	            pointFormat: '<span style="color:{series.color}">{series.name}: <b>${point.y:,.0f}</b><br/>'
	        },
	
	        legend: {
	            align: 'right',
	            verticalAlign: 'top',
	            y: 70,
	            layout: 'vertical'
	        },
	
	        series: data
        });
	}	
	
	//type: 'donut3D'  3D圆环图
	function donut3D(id, title, xAxis, data) {
		
		$('#'+ id).highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45
	            }
	        },
	        title: {
	            text: title
	        },
	        xAxis: {
	            categories: xAxis
	        },
	        plotOptions: {
	            pie: {
	                innerSize: 100,
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                },
                    connectorColor: 'silver'
}
	        },
	        series: data
	    });
	}
	