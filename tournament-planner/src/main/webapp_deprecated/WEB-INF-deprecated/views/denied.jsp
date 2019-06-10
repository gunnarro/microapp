<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">

<jsp:include page="public/head-tag.jsp" />

<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">SporTzTeam Access Denied</h1>
			</div>

			<div class="panel-body center-block">
				<div class="alert alert-danger" role="alert">Access denied</div>
				Bring me back <a href="<spring:url value="/listclubs" htmlEscape="true" />"> SporTzTeam</a>
			</div>
		</div>
		<jsp:include page="public/footer.jsp" />
	</div>
</body>

</html>