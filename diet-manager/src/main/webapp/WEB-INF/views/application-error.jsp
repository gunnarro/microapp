<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">
<head>
	<jsp:include page="public/head-includes.jsp" />
</head>
<body>
	<div class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">DietManager Application Error</h1>
			</div>
			<div class="panel-body center-block">
				<div class="alert alert-danger" role="alert">Failed requesting URL: ${requestUrl}
				Error: <br>${exception.message}</div>
			</div>
			<div class="panel-footer clearfix">
				<div class="pull-right">
					<a href = "javascript:history.back()" class="btn btn-primary btn-sm">Ok</a>
				</div>
			</div>
		</div>
		<jsp:include page="public/footer.jsp" />
	</div>
</body>

</html>