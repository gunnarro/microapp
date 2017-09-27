<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container">

		<!-- Panel -->
		<div class="panel panel-default">

			<div class="panel-heading clearfix">
				<h1 class="panel-title">Copy Event</h1>
			</div>

			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="inputData" method="POST" id="calendar-event-copy-by-id-form">
					<form:hidden path="id" value="${inputData.id}" />
					<form:hidden path="name" value="${inputData.name}" />
					<fmt:formatDate var="fromDate_ddMMyyyy" value="${inputData.fromDate}" pattern="dd.MM.yyyy" />
					<form:hidden path="fromDate" value="${fromDate_ddMMyyyy}" />

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					  -->
					  
					<div class="form-group">
							<label>Event</label>
							<fmt:formatDate var="formattedFromDate" value="${inputData.fromDate}" pattern="EEEE, dd.MM.yyyy hh:mm" />
							<p class="form-control-static">${formattedFromDate} ${inputData.name}</p>
					</div>
					
					<spring:bind path="toDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="toDate">To Date</form:label>
							<fmt:formatDate var="formattedToDate" value="${inputData.toDate}" pattern="dd.MM.yyyy" />
							<form:input min="01.10.2014" max="31.03.2017" cssClass="form-control" path="toDate" value="${formattedToDate}" placeholder="dd.MM.yyyy"/>
							<form:errors path="toDate" cssClass="help-block" />
						</div>
					</spring:bind>
					
					<button type="submit" class="btn btn-primary">Copy Event</button>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/calendar/listevents/false/week/0/all/all"
						var="cancelUrl">
					</spring:url>
					<a href="${fn:escapeXml(cancelUrl)}" class="btn btn-primary btn-sm">Cancel</a>
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