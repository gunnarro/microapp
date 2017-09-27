<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
			
			 <img src="<spring:url value="/resources/images/logo.png" htmlEscape="true" />" align="middle" />
		</div>
		<!-- Collection of nav links and other content for toggling -->
		<div id="navbarCollapse" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="<spring:url value="/log/events" htmlEscape="true" />"><i class="glyphicon glyphicon-list-alt"></i> Log</a></li>
				<li><a href="<spring:url value="/listvolunteertasks/1/current" htmlEscape="true" />"><i class="glyphicon glyphicon-tasks"></i> Volunteer tasks</a></li>
				<li><a href="/calendar-manager/calendar/listevents/false/week/0/all/all"><i class="glyphicon glyphicon-calendar"></i> Kalender</a></li>
			</ul>	
			
			<sec:authorize access="isFullyAuthenticated()">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><sec:authentication property="principal.username" /></a></li> 
					<li><a href="#">|</a></li>
					<li><a href="<spring:url value="/perform-logout" htmlEscape="true" />"><i class="glyphicon glyphicon-record signout"></i> Sign out</a></li>
				</ul>
			</sec:authorize>
				
			<sec:authorize access="isAnonymous()">
				<form class="navbar-form navbar-right" name="userLoginform" action="/perform-login" method="POST">
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
