 <style type="text/css">
 .chart {
  width: 100%; 
  height: 100%;
  min-height: 20%;
}
</style>
 <script type="text/javascript" charset="utf-8">
  // for material chart, upcomming way to do it
  // google.charts.load('current', {'packages':['line']});
  // current way 	
  google.charts.load('current', {'packages':['corechart']});
  google.charts.setOnLoadCallback(drawLineChart);
	
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
	
	//	var materialOptions = {
	//	chart: {
	//		title: 'Pepilie Vekt og Høyde'
	//	},
    //  	curveType: 'function',
    //  	legend: {position: 'top'},
    //  	chartArea:{left:10,top:10,width:'50%',height:'75%'}
    //};
	
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


    var jsonResponse = loadDataSynchronous("/diet-manager/rest/chart/data/bmi");
    jsonData = JSON.parse(jsonResponse);
    var weightDataTable = new google.visualization.DataTable();
    weightDataTable.addColumn('string', 'Dato');
    weightDataTable.addColumn('number', 'Pepilie vekt (kg)');
    var heightDataTable = new google.visualization.DataTable();
    heightDataTable.addColumn('string', 'Dato');
	heightDataTable.addColumn('number', 'Pepilie Høyde (cm)');
	//for (i=0; i<jsonData.length; i++){
	for (i=jsonData.length-1; i>=0; i--){
		weightDataTable.addRow([jsonData[i].label, parseFloat(jsonData[i].value1)]);
		heightDataTable.addRow([jsonData[i].label, parseFloat(jsonData[i].value2)]);
	}
	
//	var weightChart = new google.charts.Line(document.getElementById('linechart_weight'));
//	weightChart.draw(weightDataTable, materialOptions);
	
//	var heightChart = new google.charts.Line(document.getElementById('linechart_height'));
//	heightChart.draw(heightDataTable, materialOptions);
	
	var weightChart = new google.visualization.LineChart(document.getElementById('linechart_weight'));
	weightChart.draw(weightDataTable, weightOptions);
	var heightChart = new google.visualization.LineChart(document.getElementById('linechart_height'));
	heightChart.draw(heightDataTable, hightOptions);
  }
  
// The chart must be redrawn in order to be responsive when resizing browser wiondow
//  $(window).resize(function(){
//	    drawChart();
//   });
  </script>
 
  <div id="linechart_weight"></div>
  <div id="linechart_height"></div>
