<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">

<jsp:include page="public/head-tag.jsp" />

<body style="background: #eee !important;">
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">You have been Logged out!</h1>
			</div>

			<div class="panel-body center-block">
				<div class="alert alert-info" role="alert">You have been signed out.</div>
				Bring me back <a href="<spring:url value="/home" htmlEscape="true" />">Pepilie</a>
			</div>
		</div>

		<jsp:include page="public/footer.jsp" />
	</div>
</body>

</html>