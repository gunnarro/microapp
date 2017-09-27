<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
// hack for facebook adding #_=_ when using oaut2 login
$(document).ready(function () {
	if (window.location.href.indexOf('#_=_') > 0) {
	window.location = window.location.href.replace(/#.*/, '');
}});	
</script>
<div class="page-margin">
	<nav role="navigation" class="navbar navbar-default">
		<!-- Brand and toggle get grouped for better mobile display -->
		<!-- 
		<div class="navbar-header">
			<button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
		</div>
		 -->
		<div class="navbar-header">
			<button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbarCollapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a href="<spring:url value="/diet/mystatus" htmlEscape="true" />">
				<img src="<spring:url value="/resources/images/logo-pepilie.gif" htmlEscape="true" />" align="middle" />
			</a>
		</div>
		<!-- Collection of nav links and other content for toggling -->
		<div id="navbarCollapse" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<!-- <li><a href="<spring:url value="/diet/rules" htmlEscape="true" />"><i class="glyphicon glyphicon-home"></i> <spring:message code="topmenu.home"/></a></li> -->
				<li><a href="<spring:url value="/diet/mystatus" htmlEscape="true" />"><i class="icon-happy2"></i> <spring:message code="topmenu.status"/></a></li>
				<li><a href="<spring:url value="/diet/dietplan/current" htmlEscape="true" />"><i class="glyphicon glyphicon-list"></i> <spring:message code="topmenu.dietplan"/></a></li>
				<li><a href="<spring:url value="/diet/menu/current" htmlEscape="true" />"><i class="glyphicon glyphicon-list"></i> <spring:message code="topmenu.menu"/></a></li>
				<li><a href="<spring:url value="/diet/recipes" htmlEscape="true" />"><i class="glyphicon glyphicon-list"></i> <spring:message code="topmenu.recipes"/></a></li>
				<li><a href="<spring:url value="/diet/changelist" htmlEscape="true" />"><i class="glyphicon glyphicon-list"></i> <spring:message code="topmenu.changelist"/></a></li>
				<li><a href="<spring:url value="/diet/mychoices" htmlEscape="true" />"><i class="glyphicon glyphicon-check"></i> <spring:message code="topmenu.mychoices"/></a></li>
				<li><a href="<spring:url value="/diet/body/measurement/log" htmlEscape="true" />"><i class="glyphicon glyphicon-stats"></i> <spring:message code="topmenu.measurements"/></a></li>
				<sec:authorize access="hasRole('ROLE_USER')">
					<li><a href="/image-manager/gallery"><i class="glyphicon glyphicon-picture"></i> <spring:message code="topmenu.gallery"/></a></li>
					<li><a href="/calendar-manager/calendar/listevents/false/week/0/all/all"><i class="glyphicon glyphicon-calendar"></i> <spring:message code="topmenu.calendar"/></a></li>
					<li><a href="<spring:url value="/diet/log/events" htmlEscape="true" />"><i class="glyphicon glyphicon-list-alt"></i> <spring:message code="topmenu.log"/></a></li>
				</sec:authorize>
			</ul>	
			
			<sec:authorize access="isFullyAuthenticated()">
				<ul class="nav navbar-nav navbar-right">
					<sec:authentication var="principal" property="principal" />
					<c:set var="userIcon" value="glyphicon glyphicon-user" />
					<c:if test="${principal.username == 'pappa'}">
						<c:set var="userIcon" value="glyphicon glyphicon-king king-color" />
					</c:if>
					<c:if test="${principal.username == 'mamma'}">
						<c:set var="userIcon" value="glyphicon glyphicon-queen queen-color" />
					</c:if>
					<c:if test="${principal.username == 'pepilie'}">
						<c:set var="userIcon" value="glyphicon glyphicon-pawn pawn-color" />
					</c:if>
					<li><a href="#"><i class="${userIcon}"></i> ${principal.username}</a></li> 
					<li><a href="#">|</a></li>
					<li><a href="<spring:url value="/perform-logout-user" htmlEscape="true" />"><i class="glyphicon glyphicon-record signout"></i> <spring:message code="btn.logout"/></a></li>
				</ul>
			</sec:authorize>
			
			<sec:authorize access="isAnonymous()">
				<form class="navbar-form navbar-right" name="userLoginform" action="<spring:url value="/perform-login" htmlEscape="true" />" method="POST">
					<div class="form-group">
						<input type="text" class="form-control input-sm" name="username" placeholder="Username">
					</div>
					<div class="form-group">
						<input type="password" class="form-control input-sm" name="password" placeholder="Password">
					</div>
					<button name="submit" type="submit" class="btn btn-default btn-sm"><spring:message code="btn.login"/></button>
				</form>
			</sec:authorize>
			
		</div>
	</nav>
</div>
