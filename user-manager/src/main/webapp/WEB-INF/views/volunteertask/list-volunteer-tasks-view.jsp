<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">

		<div class="page-header">
			<h4>
				<spring:url value="/listclubs" var="viewUrl">
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}"><c:out value="${clubName}" /></a>
			</h4>
			<h5>Volunteer tasks</h5>
			<h5>${season.period}</h5>
		</div>

		<jsp:include page="list-volunteer-tasks.jsp" />

		<spring:url value="/volunteertask/new" var="addUrl">
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Volunteer task</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>