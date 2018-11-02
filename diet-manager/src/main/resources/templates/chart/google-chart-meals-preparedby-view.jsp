 <style type="text/css">
 .chart {
  width: 100%; 
  height: 100%;
  min-height: 20%;
}
</style>
 <script type="text/javascript" charset="utf-8">
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
	  var options = {
      	title: 'Antall Måltider Tilberedt av',
      	curveType: 'function',
      	legend: { position: 'bottom' },
    };
    var jsonResponse = loadDataSynchronous("/diet-manager/rest/chart/data/preparedby");
    jsonData = JSON.parse(jsonResponse);
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn('string', 'Uke (2017)');
    dataTable.addColumn('number', 'Pappa');
    dataTable.addColumn('number', 'Mamma');
    dataTable.addColumn('number', 'Pepilie');
    dataTable.addColumn('number', 'Total');
    for (i=0; i<jsonData.length; i++){
    	var total = jsonData[i].value1 + jsonData[i].value2 + jsonData[i].value3;
		dataTable.addRow([jsonData[i].label, //week
		                  parseFloat(jsonData[i].value1), // pappa
		                  parseFloat(jsonData[i].value2),  // mamma	
		                  parseFloat(jsonData[i].value3),// pepilie
		                  total]); // total
	}
	var chart = new google.visualization.LineChart(document.getElementById('linechart_preparedby'));
    chart.draw(dataTable, options);
  }
  </script>
 
  <div id="linechart_preparedby" class="chart"></div>

  