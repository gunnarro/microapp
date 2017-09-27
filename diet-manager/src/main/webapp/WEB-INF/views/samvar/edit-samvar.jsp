<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<jsp:include page="../public/head-includes.jsp" />
	<!-- Bootstrap Date-Picker Plugin -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />
	
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
  google.charts.load("current", {packages:["timeline"]});
  google.charts.setOnLoadCallback(drawChart);
  function drawChart() {
    var container = document.getElementById('example2.1');
    var chart = new google.visualization.Timeline(container);
    var dataTable = new google.visualization.DataTable();

    dataTable.addColumn({ type: 'string', id: 'Term' });
    dataTable.addColumn({ type: 'string', id: 'Name' });
    dataTable.addColumn({ type: 'date', id: 'Start' });
    dataTable.addColumn({ type: 'date', id: 'End' });

    dataTable.addRows([
      [ 'Pappa', 'Emilie', new Date(2016, 9, 25), new Date(2016, 10, 2) ],
      [ 'Pappa', 'Andreas', new Date(2016, 9, 25), new Date(2016, 10, 2) ],
      [ 'Mamma', 'Emilie',  new Date(2016, 10, 3),  new Date(2016, 10, 6) ],
      [ 'Mamma', 'Andreas',  new Date(2016, 10, 3),  new Date(2016, 10, 6) ]]);

    chart.draw(dataTable);
  }
</script>
	
	<script type="text/javascript">
	function selectControlledByUser(selectedBtnId,btnId2,btnId3,userId) {
		 var currentValue = $("#controlledByInputId").val();
		 if (currentValue != userId) {
		 	$("#controlledByInputId").val(userId);
		 	$('#' + selectedBtnId).toggleClass("glyphicon-unchecked glyphicon-check");
		 	$('#' + btnId2).removeClass("glyphicon-check").addClass("glyphicon-unchecked");
		 	$('#' + btnId3).removeClass("glyphicon-check").addClass("glyphicon-unchecked")
		 }
	}
	
	function selectConflict(btnId, value) {
		 var currentValue = $("#conflictInputId").val();
		 if (currentValue != value) {
		 	$("#conflictInputId").val(value);
		 	$('#icoBtn4').toggleClass("glyphicon-unchecked glyphicon-check");
		 	$('#icoBtn5').toggleClass("glyphicon-unchecked glyphicon-check");

		 	if (value == 0) {
		 		 $('#conflictDescriptInputId').attr('disabled', 'disabled');
			 } else {
		 		 $('#conflictDescriptInputId').removeAttr('disabled');
			 }
		 }
	}   
	
	$(document).ready(function(){
      var date_input=$('input[name="createdDate"]');
      var container=$('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
      var options={
        format: 'dd.mm.yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
      };
      date_input.datepicker(options);
    })
	</script>
	
	<div class="container">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/togetherness" htmlEscape="true" />" >Mitt samvær</a></li>
        	<li class="active">Registrer samvær</li>
    	</ul>
		
		<c:choose>
			<c:when test="${menuItem['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty menuItem.id}">
					<div class="pull-right">
						<spring:url value="/togetherness/delete/{menuItemId}/{date}" var="deleteUrl">
							<spring:param name="menuItemId" value="${menuItem.id}" />
							<spring:param name="date" value="${menuItem.id}" />
						</spring:url>
						<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
					</div>
				</c:if>
				<h3 class="panel-title">
					<c:if test="${menuItem['new']}">Registrer Samvær</c:if>
				</h3>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="menuItem" method="${method}" id="diet-my-choice-form">
					<form:hidden path="fkDietMenuId" value="${menuItem.fkDietMenuId}" />
					<form:hidden path="name" value="null" />
					<form:hidden path="description" value="null" />
					
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					-->
					<form:label path="controlledByUserId">Foreldere</form:label>
					<div class="form-group">
						<c:choose>
							<c:when test="${menuItem.controlledByUserId == 5}">
								<div class="btn-group" data-toggle="buttons">
			    					<label class="btn btn-default" onclick="selectControlledByUser('icoBtn1','icoBtn2','icoBtn3','5');">
										<c:set var="checkIconBtn1" value="glyphicon glyphicon-unchecked" />
										<c:if test="${menuItem.controlledByUserId == 5}">
											<c:set var="checkIconBtn1" value="glyphicon glyphicon-check" />
										</c:if>
										<i id="icoBtn1" class="${checkIconBtn1}" ></i>
			        					<input type="radio" name="options" id="option1" value="5" />Pappa
			    					</label>
								    <label class="btn btn-default" onclick="selectControlledByUser('icoBtn2','icoBtn1','icoBtn3','6');">
								    	<c:set var="checkIconBtn2" value="glyphicon glyphicon-unchecked" />
										<c:if test="${menuItem.controlledByUserId == 6}">
											<c:set var="checkIconBtn2" value="glyphicon glyphicon-check" />
										</c:if>
										<i id="icoBtn2" class="${checkIconBtn2}"></i>
								        <input type="radio" name="options" id="option2" value="6" />Mamma
								    </label>
								</div>
							</c:when>
							<c:otherwise>
								<p class="form-control-static">${menuItem.controlledByUsername}</p>
							</c:otherwise>
						</c:choose>
						<form:hidden id="controlledByInputId" path="controlledByUserId" value="${controlledByUserId}" />
					</div>
					
					<form:label path="controlledByUserId">Samvær med</form:label>
					<div class="form-group">
					<div class="btn-group" data-toggle="buttons">
    					<label class="btn btn-default">
							<c:set var="checkIconBtn1" value="glyphicon glyphicon-unchecked" />
							<c:if test="${menuItem.controlledByUserId == 5}">
								<c:set var="checkIconBtn1" value="glyphicon glyphicon-check" />
							</c:if>
							<i id="icoBtn1" class="${checkIconBtn1}" ></i>
        					<input type="checkbox" name="options" id="option1" value="5" />Emilie
    					</label>
					    <label class="btn btn-default">
					    	<c:set var="checkIconBtn2" value="glyphicon glyphicon-unchecked" />
							<c:if test="${menuItem.controlledByUserId == 6}">
								<c:set var="checkIconBtn2" value="glyphicon glyphicon-check" />
							</c:if>
							<i id="icoBtn2" class="${checkIconBtn2}"></i>
					        <input type="checkbox" name="options" id="option2" value="6" />Andreas
					    </label>
					</div>
					<form:hidden id="controlledByInputId" path="controlledByUserId" value="${controlledByUserId}" />
					</div>

					<div class="form-group">
						<label class="control-label">Fra - til Dato</label>
						<div class="input-group input-daterange">
						    <input type="text" class="form-control" value="2012-04-05">
						    <span class="input-group-addon">to</span>
						    <input type="text" class="form-control" value="2012-04-19">
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label">Fra - til tidspunkt</label>
						<div class="input-group input-daterange">
						    <input type="text" class="form-control" value="2012-04-05">
						    <span class="input-group-addon">to</span>
						    <input type="text" class="form-control" value="2012-04-19">
						</div>
					</div>

					<div class="form-group">
						<form:label path="conflictDescription">Kommentar</form:label>
						<form:textarea id="conflictDescriptInputId" disabled="true" cssClass="form-control" placeholder="Kommentarer..." path="conflictDescription" value="${menuItem.conflictDescription}" />
						<form:errors path="conflictDescription" cssClass="help-block error-color" />
					</div>
					
					<p />

					<c:choose>
						<c:when test="${menuItem['new']}">
							<button type="submit" class="btn btn-primary">Registrer</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Oppdater</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/diet/mychoices" var="cancelUrl">
					</spring:url>
					<a href="${fn:escapeXml(cancelUrl)}" class="btn btn-primary btn-sm">Avbryt</a>
				</div>
			</div>
			<!-- end panel footer -->
		</div>
		<!-- end panel -->
		
		<div id="example2.1" style="height: 200px;"></div>
		
	</div>
	<!-- end container -->

	<jsp:include page="../public/footer.jsp" />
</body>
</html>