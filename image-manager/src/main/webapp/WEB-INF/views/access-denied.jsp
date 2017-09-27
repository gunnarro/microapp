<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<jsp:include page="public/head-tag.jsp" />

<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">Pepilie Access Denied</h1>
			</div>

			<div class="panel-body center-block">
				<div class="alert alert-danger" role="alert">Access denied
				<br>Exception: 
				<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
				<br>Servlet:
				<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
				<c:out value="${exception.message}" />
				
				</div>
				Bring me back <a href="<spring:url value="/home" htmlEscape="true" />"> Pepilie</a>
			</div>
		</div>
		<jsp:include page="public/footer.jsp" />
	</div>
</body>

</html>