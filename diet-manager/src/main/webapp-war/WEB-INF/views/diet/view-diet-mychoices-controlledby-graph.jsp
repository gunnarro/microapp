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
        	<li><a href="<spring:url value="/diet/mychoices" htmlEscape="true" />" >Mine Valg</a></li>
        	<li class="active">Graf kontrollerte måltider</li>
    	</ul>
    	
		<div class="page-header">
			<h4>Graf for måltider</h4>
		</div>
		<jsp:include page="../chart/google-chart-meals-controlledby-view.jsp" /> 
		<jsp:include page="../chart/google-chart-meals-preparedby-view.jsp" /> 
		<jsp:include page="../chart/google-chart-meals-caused-conflict-view.jsp" />
		<jsp:include page="../chart/google-chart-meal-types-view.jsp" /> 
	</div>
	<!-- end container -->
	<jsp:include page="../public/footer.jsp" />
</body>
</html>