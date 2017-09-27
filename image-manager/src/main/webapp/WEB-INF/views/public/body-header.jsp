<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
	.navbar-default .navbar-nav > li > a:hover,
	.navbar-default .navbar-nav > li > a:active {
	 	border-bottom: 4px solid #9A2EFE !important;
	}
	.navbar-active {
		border-bottom: 4px solid #9A2EFE !important;
	}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$(".nav a").click(function() {
	  $(".nav a").removeClass("navbar-active");
	  $(this).children().addClass("navbar-active");
	  return true;
	});
});
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
			
			 <img src="<spring:url value="/resources/images/logo-pepilie.gif" htmlEscape="true" />" align="middle" />
		</div>
		<!-- Collection of nav links and other content for toggling -->
		<div id="navbarCollapse" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="/diet-manager/diet/dietplan/1/1"><span><i class="glyphicon glyphicon-home"></i> Diet Plan</span></a></li>
				<li><a href="/diet-manager/diet/menu/1"><span><i class="glyphicon glyphicon-list"></i> Meny</span></a></li>
				<li><a href="/diet-manager/diet/recipes"><span><i class="glyphicon glyphicon-list"></i> Oppskrifter</span></a></li>
				<li><a href="/diet-manager/diet/changelist"><span><i class="glyphicon glyphicon-list"></i> Bytteliste</span></a></li>
				<li><a href="/diet-manager/diet/mychoices/1"><span><i class="glyphicon glyphicon-check"></i> Mine Valg</span></a></li>
				<li><a class="navbar-active" href="<spring:url value="/gallery" htmlEscape="true" />"><span><i class="glyphicon glyphicon-picture"></i> Bilder</span></a></li>
				<li><a href="/diet-manager/diet/body/measurement/chart/1"><span><i class="glyphicon glyphicon-stats"></i> Statestikk</span></a></li>
				<li><a href="/calendar-manager/calendar/listevents/false/week/0/all/all"><span><i class="glyphicon glyphicon-calendar"></i> Kalender</span></a></li>
				<sec:authorize access="hasRole('ROLE_USER')">
					<li><a href="/diet-manager/diet/log/events"><i class="glyphicon glyphicon-list-alt"></i> Log</a></li>
				</sec:authorize>
			</ul>
			
			<sec:authorize access="isFullyAuthenticated()">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><sec:authentication property="principal.username" /></a></li> 
					<li><a href="#">|</a></li>
					<li><a href="<spring:url value="/perform-logout" htmlEscape="true" />"><i class="glyphicon glyphicon-record signout"></i> Sign out</a></li>
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
					<button name="submit" type="submit" class="btn btn-default btn-sm">Sign In</button>
				</form>
			</sec:authorize>
		</div>
	</nav>
</div>
