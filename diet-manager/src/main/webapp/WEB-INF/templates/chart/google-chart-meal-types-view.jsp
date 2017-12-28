 <style type="text/css">
 .chart {
  width: 100%; 
  height: 100%;
  min-height: 20%;
}
</style>
 <script type="text/javascript" charset="utf-8">
  google.charts.load('current', {'packages':['corechart']});
  google.charts.setOnLoadCallback(drawPieChart);
	
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
  
  function drawPieChart() {
	var options = {
          title: 'Måltider fordeling',
          legend: { position: 'bottom' }
    };
    var jsonResponse = loadDataSynchronous("/diet-manager/rest/chart/data/mealtypes");
    jsonData = JSON.parse(jsonResponse);
    var mealTypesDataTable = new google.visualization.DataTable();
    mealTypesDataTable.addColumn('string', 'Meal type distribution');
    mealTypesDataTable.addColumn('number', 'count');
    for (i=0; i<jsonData.length; i++){
    	mealTypesDataTable.addRow([jsonData[i].label, parseFloat(jsonData[i].value1)]);
	}
	var pieChart = new google.visualization.PieChart(document.getElementById('piechart_mealtypes'));
	pieChart.draw(mealTypesDataTable, options);
  }  
  </script>
 
  <div id="piechart_mealtypes"></div>
