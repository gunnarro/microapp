/*!
 * Name: bmi-chart.js
 * Version: 1.0.-SNAPSHOT
 * Release date: 29.10.2017
 * Copyright 2016 gunnarro
 */
  function loadDataSynchronous(restUrl) {
	  return $.ajax({
	      url: restUrl,
	      dataType: "json",
	      contentType : "application/json",
	      mimeType : "application/json",
	      cache : false,
	      async: false
	      }).responseText;
   }
  
  function drawLineChart() {
	
	var weightOptions = {
          title: 'Pepilie Vekt',
          curveType: 'function',
          legend: { position: 'bottom' }
    };
	
	var hightOptions = {
	          title: 'Pepilie Høyde',
	          curveType: 'function',
	          legend: { position: 'bottom' }
	    };

	var bmiOptions = {
	          title: 'Pepilie BMI',
	          curveType: 'function',
	          legend: { position: 'bottom' },
	          hAxis: { maxValue: 228 },
	          vAxis: { maxValue: 24 },
	          interpolateNulls: true,
	          lineWidth: 2,
	          series: {
	              0: { lineDashStyle: [4, 1] },
	              1: { lineDashStyle: [4, 1] },
	              2: { lineDashStyle: [4, 1] }
	            },
	          colors: ['#DF3446', '#88D413', '#344BDF', '#4608DF'],
	    };

    var jsonResponse = loadDataSynchronous("/rest/data/chart/data/bodymeasure");
    var jsonData = JSON.parse(jsonResponse);
    var weightDataTable = new google.visualization.DataTable();
    weightDataTable.addColumn('string', 'Dato');
    weightDataTable.addColumn('number', 'Pepilie Vekt (kg)');
    var heightDataTable = new google.visualization.DataTable();
    heightDataTable.addColumn('string', 'Dato');
	heightDataTable.addColumn('number', 'Pepilie Høyde (cm)');
	
	for (i=jsonData.length-1; i>=0; i--){
		weightDataTable.addRow([jsonData[i].label, parseFloat(jsonData[i].value1)]);
		heightDataTable.addRow([jsonData[i].label, parseFloat(jsonData[i].value2)]);
	}
	
	var bmiJsonResponse = loadDataSynchronous("/rest/data/chart/data/bmi");
	var bmiJsonData = JSON.parse(bmiJsonResponse);
	var bmiDataTable = new google.visualization.DataTable();
	bmiDataTable.addColumn('string', 'Alder (mnd)');
	bmiDataTable.addColumn('number', 'bmi percentile 25');
	bmiDataTable.addColumn('number', 'bmi percentile 50');
	bmiDataTable.addColumn('number', 'bmi percentile 75');
	bmiDataTable.addColumn('number', 'bmi pepilie');
	
	for (j=0; j<bmiJsonData.length; j++){
		bmiDataTable.addRow([bmiJsonData[j].label, parseFloat(bmiJsonData[j].value1),parseFloat(bmiJsonData[j].value2),parseFloat(bmiJsonData[j].value3),parseFloat(bmiJsonData[j].value4)]);
	}
	
	var weightChart = new google.visualization.LineChart(document.getElementById('linechart_weight'));
	weightChart.draw(weightDataTable, weightOptions);

	var heightChart = new google.visualization.LineChart(document.getElementById('linechart_height'));
	heightChart.draw(heightDataTable, hightOptions);

	var bmiChart = new google.visualization.LineChart(document.getElementById('linechart_bmi'));
	bmiChart.draw(bmiDataTable, bmiOptions);
  } 