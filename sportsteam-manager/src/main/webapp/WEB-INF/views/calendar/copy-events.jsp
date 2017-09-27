<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/diet-body-header.jsp" />

	<div class="container">

		<!-- Panel -->
		<div class="panel panel-default">

			<div class="panel-heading clearfix">
				<h1 class="panel-title">Copy Events</h1>
			</div>

			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="inputData" method="POST" id="calendar-event-copy-form">
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<div class="form-group">
						<form:label path="fromDate">From date</form:label>
						<form:select cssClass="form-control" path="fromDate">
							<form:options items="${inputData.items}" itemValue="startDate" itemLabel="startDate" />
						</form:select>
					</div>
					
					<spring:bind path="toDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="toDate">To Date</form:label>
							<fmt:formatDate var="formattedToDate" value="${inputData.toDate}" pattern="dd.MM.yyyy" />
							<form:input min="01.10.2014" max="31.03.2017" cssClass="form-control" path="toDate" value="${formattedToDate}" placeholder="dd.MM.yyyy"/>
							<form:errors path="toDate" cssClass="help-block" />
						</div>
					</spring:bind>
					
					<button type="submit" class="btn btn-primary">Copy Events</button>
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