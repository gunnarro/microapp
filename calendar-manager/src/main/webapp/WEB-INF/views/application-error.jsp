<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="public/head-tag.jsp" />

<body>
	<jsp:include page="public/body-header.jsp" />
	<div class="container">

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">Pepilie Application Error</h1>
			</div>

			<div class="panel-body center-block">
				<div class="alert alert-danger" role="alert">Failed requesting URL: ${url}<br>${exception.message}</div>

				<!-- 
				<strong>Exception Stack Trace</strong><br>
				<c:forEach items="${exception.stackTrace}" var="ste">
    				${ste}
				</c:forEach>
				 -->
			</div>

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/home" var="viewUrl">
					</spring:url>
					<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-sm">Ok</a>
				</div>
			</div>

		</div>

		<jsp:include page="public/footer.jsp" />
	</div>
</body>

</html>