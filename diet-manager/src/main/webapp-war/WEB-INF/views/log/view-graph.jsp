<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en">
<head>
	<jsp:include page="../public/head-includes.jsp" />
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container-fluid">
		<ul class="breadcrumb">
        	<li><a href="<spring:url value="/diet/body/measurement/log" htmlEscape="true" />" >Oversikt vekt og høyde</a></li>
        	<li class="active">Utvikling vekt, høyde og bmi</li>
    	</ul>
    	
		<div class="page-header">
			<h4>Utvikling vekt, høyde og bmi</h4>
		</div>
		<jsp:include page="../chart/google-chart-view.jsp" /> 
	</div>
	<!-- end container -->
	<jsp:include page="../public/footer.jsp" />
</body>
</html>