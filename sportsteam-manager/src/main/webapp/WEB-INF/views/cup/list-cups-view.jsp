<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">

		<div class="page-header">
			<h4>Cups</h4>
			<h5>${season.period}</h5>
		</div>

		<jsp:include page="list-cups.jsp">
			<jsp:param name="isTeamView" value="false" />
		</jsp:include>

		<spring:url value="/cup/new" var="addUrl">
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Cup</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>