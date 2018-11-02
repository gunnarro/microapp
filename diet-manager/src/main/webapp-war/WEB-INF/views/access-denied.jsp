<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<jsp:include page="public/head-includes.jsp" />

<body>
	<div class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">Pepilie Access Denied</h1>
			</div>

			<div class="panel-body center-block">
				<div class="alert alert-danger" role="alert">
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
				</div>
				<c:if test="requestScope['javax.servlet.error.exception'] != null">
					<div class="alert alert-danger" role="alert">Servlet
						<br>
						<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
						<c:out value="${exception.message}" />
					</div>
				</c:if>
				<a href = "javascript:history.back()" class="btn btn-primary btn-sm">Bring me back</a>
			</div>
		</div>
		<jsp:include page="public/footer.jsp" />
	</div>
</body>

</html>