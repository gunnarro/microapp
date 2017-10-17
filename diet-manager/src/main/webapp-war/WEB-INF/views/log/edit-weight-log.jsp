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
	$(document).ready(function(){
	      var date_input=$('input[name="logDate"]');
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

	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/body/measurement/log" htmlEscape="true" />" >Mine data</a></li>
        	<li class="active">Ny Log</li>
    	</ul>
		
		<c:choose>
			<c:when test="${log['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty log.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/diet/body/measurement/delete/{logEntryId}" var="deleteUrl">
								<spring:param name="logEntryId" value="${log.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
						</div>
					</div>
				</c:if>
				<h3 class="panel-title">
					<c:if test="${log['new']}">Log vekt </c:if>
				    for bruker ${userId}
				</h3>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="log" method="${method}" id="diet-weight-form">
					<form:hidden path="userId" value="${log.userId}" />
					
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					
					 <fmt:formatDate var="formattedLogDate" value="${log.logDate}" pattern="dd.MM.yyyy" />
					 <div class="form-group ${status.error ? 'has-error' : ''}"">
						<label for="logDate" class="control-label">Dato</label>
						<div class="controls">
							<div class="input-group">
								<input id="logDate" name="logDate" type="text" class="date-picker form-control" value="${formattedLogDate}" placeholder="dd.MM.yyyy" />
								<label for="logDate" class="input-group-addon btn">
									<span class="glyphicon glyphicon-calendar"></span>
								</label>
							</div>
						</div>
						<form:errors path="logDate" cssClass="help-block error-color" />
					</div>
					
					<!-- 
					<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="logDate">Dato</form:label>
							<fmt:formatDate var="formattedLogDate" value="${log.logDate}" pattern="dd.MM.yyyy" />
							<form:input min="01.01.2016" max="31.12.2017" cssClass="form-control" path="logDate" value="${formattedLogDate}" placeholder="dd.MM.yyyy"/>
							<form:errors path="logDate" cssClass="help-block" />
					</div>
					 -->
					
					<div class="form-group">
						<form:label path="weight">Min vekt (kg)</form:label>
						<form:input cssClass="form-control" path="weight" value="${log.weight}" placeholder="0.0" size="5" />
						<form:errors path="weight" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<form:label path="height">Min Høyde (cm)</form:label>
						<form:input cssClass="form-control" path="height" value="${log.height}" placeholder="0.0" size="5" />
						<form:errors path="height" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<form:label path="comment">Kommentar</form:label>
						<form:input cssClass="form-control" path="comment" value="${log.comment}" />
					</div>
					<p />

					<c:choose>
						<c:when test="${log['new']}">
							<button type="submit" class="btn btn-primary">Legg til</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update log</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/diet/body/measurement/log" var="cancelUrl">
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