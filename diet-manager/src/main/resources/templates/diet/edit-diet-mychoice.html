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
	
	<script type="text/javascript">
	function selectUser(inputElementId, selectedBtnId, btnId2, btnId3, btnId4, userId) {
		 var currentValue = $("#" + inputElementId).val();
		 if (currentValue != userId) {
		 	$("#" + inputElementId).val(userId);
		 	$('#' + selectedBtnId).toggleClass("glyphicon-unchecked glyphicon-check");
		 	$('#' + btnId2).removeClass("glyphicon-check").addClass("glyphicon-unchecked");
		 	$('#' + btnId3).removeClass("glyphicon-check").addClass("glyphicon-unchecked")
		 	$('#' + btnId4).removeClass("glyphicon-check").addClass("glyphicon-unchecked")
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

	function refreshMealSelectionList() {
		var selectedDate = $("#createdDate").val(); 
		$("#meal-selection-id").load(getBaseUrl() + "/diet-manager/diet/mychoices/new/1/" + selectedDate + "/ #meal-selection-id");
	}
	
	function getBaseUrl() {
	    return window.location.href.split("/diet-manager")[0];
	}
	
	function calendarDateSelection() {
		 var date_input=$('input[name="createdDate"]');
	      var container=$('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
	      var options={
	        format: 'dd.mm.yyyy',
	        container: container,
	        todayHighlight: true,
	        autoclose: true,
	      };
	      date_input.datepicker(options);
	}
	
	$(document).ready(function(){
		calendarDateSelection();
    })
	</script>
	
	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/mychoices" htmlEscape="true" />" ><spring:message code="topmenu.mychoices"/></a></li>
        	<li class="active"><spring:message code="mychoice.label.newchoice"/></li>
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
						<spring:url value="/diet/mychoices/delete/{menuItemId}/{date}" var="deleteUrl">
							<spring:param name="menuItemId" value="${menuItem.id}" />
							<spring:param name="date" value="${menuItem.id}" />
						</spring:url>
						<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs"><spring:message code="btn.delete"/></a>
					</div>
				</c:if>
				<h3 class="panel-title">
					<c:if test="${menuItem['new']}"><spring:message code="mychoice.label.addnew"/> </c:if>
				    <spring:message code="mychoice.label.mealforuser"/>
				</h3>
			</div>
			<!-- end panel header -->
			<div class="panel-body">
				<form:form modelAttribute="menuItem" method="${method}" id="diet-my-choice-form">
					<form:hidden path="fkDietMenuId" value="${menuItem.fkDietMenuId}" />
					<form:hidden path="name" value="null" />
					<form:hidden path="description" value="null" />
				
					<fmt:formatDate var="formattedCreatedDate" value="${menuItem.createdDate}" pattern="dd.MM.yyyy" />
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label for="createdDate" class="control-label"><spring:message code="label.date"/></label>
						<div class="controls">
							<div class="input-group">
								<input id="createdDate" name="createdDate" type="text" class="date-picker form-control" value="${formattedCreatedDate}" placeholder="dd.MM.yyyy" onchange="refreshMealSelectionList();" />
								<label for="createdDate" class="input-group-addon btn">
									<span class="glyphicon glyphicon-calendar"></span>
								</label>
							</div>
						</div>
						<form:errors path="createdDate" cssClass="help-block error-color" />
					</div>

					<div id="meal-selection-id" class="form-group">
						<c:set var="checkedmark" value="&#x2714;&nbsp;"/>
						<c:set var="space" value="&nbsp;&nbsp;"/>
						<form:label path="id"><spring:message code="mychoice.label.choosemeal"/></form:label>
						<form:select cssClass="form-control" path="id" >
							<c:if test="${breakfastMenuItems == null && lunchMenuItems == null && dinnerMenuItems == null && dessertMenuItems == null && eveningMenuItems == null && mealBetweenMenuItems == null}">
							<option selected disabled>Alle måltider er valgt</option>
							</c:if>
							<optgroup label="${breakfastMenuItems == null ? checkedmark : space}Frokost" class="breakfast-color" >
								<form:options items="${breakfastMenuItems}" itemValue="id" itemLabel="description" class="default-txt-color" />
						 	</optgroup>
						 	<optgroup label="${lunchMenuItems == null ? checkedmark : space}Lunsj" class="lunch-color">
								<form:options items="${lunchMenuItems}" itemValue="id" itemLabel="description" class="default-txt-color" />
						 	</optgroup>
						 	<optgroup label="${dinnerMenuItems == null ? checkedmark : space}Middag" class="dinner-color">
								<form:options items="${dinnerMenuItems}" itemValue="id" itemLabel="description" class="default-txt-color" />
							</optgroup>
							<optgroup label="${dessertMenuItems == null ? checkedmark : space}Dessert" class="dessert-color">
								<form:options items="${dessertMenuItems}" itemValue="id" itemLabel="description" class="default-txt-color" />
							</optgroup>
							<optgroup label="${eveningMenuItems == null ? checkedmark : space}Kveldsmat" class="evening-color">
								<form:options items="${eveningMenuItems}" itemValue="id" itemLabel="description" class="default-txt-color" />
						 	</optgroup>
						 	<optgroup label="${mealBetweenMenuItems == null ? checkedmark : space}Mellom måltid" class="meal-between-color">
								<form:options items="${mealBetweenMenuItems}" itemValue="id" itemLabel="description" class="default-txt-color" />
						 	</optgroup>
						</form:select>
						<form:errors path="id" cssClass="help-block error-color" />
					</div>
					
					<form:label path="controlledByUserId"><spring:message code="mychoice.label.controlledby"/></form:label>
					<div class="form-group">
						<c:choose>
							<c:when test="${menuItem.controlledByUserId == 5}">
								<div class="btn-group" data-toggle="buttons">
			    					<label class="btn btn-default" onclick="selectUser('controlledByInputId','icoBtn1','icoBtn2','icoBtn3','5');">
										<c:set var="checkIconBtn1" value="glyphicon glyphicon-unchecked" />
										<c:if test="${menuItem.controlledByUserId == 5}">
											<c:set var="checkIconBtn1" value="glyphicon glyphicon-check" />
										</c:if>
										<i id="icoBtn1" class="${checkIconBtn1}" ></i>
			        					<input type="radio" name="options" id="option1" value="5" />Pappa
			    					</label>
								    <label class="btn btn-default" onclick="selectUser('controlledByInputId','icoBtn2','icoBtn1','icoBtn3','6');">
								    	<c:set var="checkIconBtn2" value="glyphicon glyphicon-unchecked" />
										<c:if test="${menuItem.controlledByUserId == 6}">
											<c:set var="checkIconBtn2" value="glyphicon glyphicon-check" />
										</c:if>
										<i id="icoBtn2" class="${checkIconBtn2}"></i>
								        <input type="radio" name="options" id="option2" value="6" />Mamma
								    </label>
								    <label class="btn btn-default" onclick="selectUser('controlledByInputId','icoBtn3','icoBtn1','icoBtn2','4');">
										<c:set var="checkIconBtn3" value="glyphicon glyphicon-unchecked" />
										<c:if test="${menuItem.controlledByUserId == 4}">
											<c:set var="checkIconBtn3" value="glyphicon glyphicon-check" />
										</c:if>
										<i id="icoBtn3" class="${checkIconBtn3}"></i>
								        <input type="radio" name="options" id="option3" value="4" />Pepilie
								    </label>
								</div>
							</c:when>
							<c:otherwise>
								<p class="form-control-static">${menuItem.controlledByUsername}</p>
							</c:otherwise>
						</c:choose>
						<form:hidden id="controlledByInputId" path="controlledByUserId" value="${controlledByUserId}" />
					</div>
					
					<form:label path="preparedByUserId"><spring:message code="mychoice.label.preparedby"/></form:label>
					<div class="form-group">
						<c:choose>
							<c:when test="${menuItem.preparedByUserId == 5}">
								<div class="btn-group" data-toggle="buttons">
			    					<label class="btn btn-default" onclick="selectUser('preparedByInputId','icoBtn10','icoBtn20','icoBtn30','icoBtn40','5');">
										<c:set var="checkIconBtn10" value="glyphicon glyphicon-unchecked" />
										<c:if test="${menuItem.preparedByUserId == 5}">
											<c:set var="checkIconBtn10" value="glyphicon glyphicon-check" />
										</c:if>
										<i id="icoBtn10" class="${checkIconBtn10}" ></i>
			        					<input type="radio" name="options" id="option10" value="5" />Pappa
			    					</label>
								    <label class="btn btn-default" onclick="selectUser('preparedByInputId','icoBtn20','icoBtn10','icoBtn30','icoBtn40','6');">
								    	<c:set var="checkIconBtn20" value="glyphicon glyphicon-unchecked" />
										<c:if test="${menuItem.preparedByUserId == 6}">
											<c:set var="checkIconBtn20" value="glyphicon glyphicon-check" />
										</c:if>
										<i id="icoBtn20" class="${checkIconBtn20}"></i>
								        <input type="radio" name="options" id="option20" value="6" />Mamma
								    </label>
								    <label class="btn btn-default" onclick="selectUser('preparedByInputId','icoBtn30','icoBtn10','icoBtn20','icoBtn40','4');">
										<c:set var="checkIconBtn30" value="glyphicon glyphicon-unchecked" />
										<c:if test="${menuItem.preparedByUserId == 4}">
											<c:set var="checkIconBtn30" value="glyphicon glyphicon-check" />
										</c:if>
										<i id="icoBtn30" class="${checkIconBtn30}"></i>
								        <input type="radio" name="options" id="option30" value="4" />Pepilie
								    </label>
								    <label class="btn btn-default" onclick="selectUser('preparedByInputId','icoBtn40','icoBtn30','icoBtn10','icoBtn20','7');">
										<c:set var="checkIconBtn40" value="glyphicon glyphicon-unchecked" />
										<c:if test="${menuItem.preparedByUserId == 7}">
											<c:set var="checkIconBtn40" value="glyphicon glyphicon-check" />
										</c:if>
										<i id="icoBtn40" class="${checkIconBtn40}"></i>
								        <input type="radio" name="options" id="option40" value="3" />Extern
								    </label>
								</div>
							</c:when>
							<c:otherwise>
								<p class="form-control-static">${menuItem.preparedByUserId}</p>
							</c:otherwise>
						</c:choose>
						<form:hidden id="preparedByInputId" path="preparedByUserId" value="${preparedByUserId}" />
					</div>
					
					<form:label path="notFollowedRule">Har ikke fulgt reglene</form:label>	
					<div class="form-group">
						<c:forEach var="rule" items="${dietRules}">
								<div class="checkbox">
						  			<label><input type="checkbox" value="${rule.name}">${rule.description}</label>
								</div>
						</c:forEach>
					</div>
					
					<form:label path="causedConflict"><spring:message code="mychoice.label.causedconflict"/></form:label>					
					<div class="form-group">
						<div class="btn-group" data-toggle="buttons">
	    					<label class="btn btn-default" onclick="selectConflict('icoBtn4','1');">
								<i id="icoBtn4" class="glyphicon glyphicon-unchecked"></i>
	        					<input type="radio" name="options" id="option10" value="1" /><spring:message code="label.yes"/>
	    					</label>
						    <label class="btn btn-default" onclick="selectConflict('icoBtn5','0');">
						        <i id="icoBtn5" class="glyphicon glyphicon-check"></i>
						        <input type="radio" name="options" id="option11" value="0" /><spring:message code="label.no"/>
						    </label>
						</div>
						<form:hidden path="causedConflict" id="conflictInputId" value="0" />
					</div>
					
					<div class="form-group">
						<form:label path="conflictDescription"><spring:message code="mychoice.label.conflictdescription"/></form:label>
						<form:textarea id="conflictDescriptInputId" disabled="true" cssClass="form-control" placeholder="Gi en kort beskivelse av konflikten her" path="conflictDescription" value="${menuItem.conflictDescription}" />
						<form:errors path="conflictDescription" cssClass="help-block error-color" />
					</div>
					
					<p />

					<c:choose>
						<c:when test="${menuItem['new']}">
							<button type="submit" class="btn btn-primary"><spring:message code="mychoice.btn.add"/></button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary"><spring:message code="mychoice.btn.update"/></button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/diet/mychoices" var="cancelUrl">
					</spring:url>
					<a href="${fn:escapeXml(cancelUrl)}" class="btn btn-primary btn-sm"><spring:message code="btn.cancel"/></a>
				</div>
			</div>
			<!-- end panel footer -->
		</div>
		<!-- end panel -->
	</div>
	<!-- end container -->

	<jsp:include page="../public/footer.jsp" />
</body>
</html>