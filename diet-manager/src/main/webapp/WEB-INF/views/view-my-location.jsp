<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<jsp:include page="public/head-includes.jsp" />
	
	<spring:url value="/resources/css/geolocation.css" var="geolocationCss" />
	<link href="${geolocationCss}" rel="stylesheet" />

	<spring:url value="/resources/js/geolocation.js" var="geolocationJs" />
	<script src="${geolocationJs}"></script>
	
</head>
<body>

	<div class="page-margin">
		<nav role="navigation" class="navbar navbar-default">
			<div class="navbar-header">
				<button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbarCollapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a href="<spring:url value="/login" htmlEscape="true" />">
					<img src="<spring:url value="/resources/images/logo-pepilie.gif" htmlEscape="true" />" align="middle" />
				</a>
			</div>
		</nav>
	</div>

	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<h3 class="panel-title">
					My location!
				</h3>
			</div>
			<!-- end panel header -->
			<div class="panel-body">
				<form id="geocoding_form" class="form-horizontal">
    				<div class="form-group">
      					<div class="col-xs-12 col-md-6 col-md-offset-3">
        					<button type="button" class="find-me btn btn-info btn-block">Find My Location</button>
      						<button type="button" class="btn btn-info btn-block" onclick="getLocation();">Get Location</button>
      					</div>
    				</div>
  				</form>

  				<p class="no-browser-support">Sorry, the Geolocation API isn't supported in Your browser.</p>
  				<p class="coordinates">Latitude: <b class="latitude">42</b> Longitude: <b class="longitude">32</b></p>

  				<div class="map-overlay">
    				<div id="map"></div>
  				</div>
			</div>
			<!-- end panel body -->
			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/login" var="cancelUrl" /> <a href="${fn:escapeXml(cancelUrl)}" class="btn btn-primary btn-sm"><spring:message code="btn.cancel" /></a>
				</div>
			</div>
		</div>
		<!-- end panel footer -->
	</div>
	<!-- end container -->
	<jsp:include page="public/footer.jsp" />
</body>
</html>