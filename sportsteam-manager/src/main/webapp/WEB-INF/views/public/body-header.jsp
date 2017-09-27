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
			<!-- 
			<a class="navbar-brand" href="#"><font style="color:#336600;">Sports</font><font style="color:#336699;">Team</font></a>
			 -->
			 <img src="/resources/images/logo-sportsteam.png" align="middle" />
		</div>
		<!-- Collection of nav links and other content for toggling -->
		<div id="navbarCollapse" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="<spring:url value="/listclubs" htmlEscape="true" />"><i class="glyphicon glyphicon-home"></i> Clubs</a></li>
				<!-- 
				<li><a href="<spring:url value="/listteams/1" htmlEscape="true" />"><i class="glyphicon glyphicon-th-list"></i> Teams</a></li>
				<li><a href="<spring:url value="/listcontacts/1" htmlEscape="true" />"><i class="glyphicon glyphicon-th-list"></i> Contacts</a></li>
				 -->
				<!-- 
				<li><a href="<spring:url value="/listreferees/1" htmlEscape="true" />"><i class="glyphicon glyphicon-list"></i> Referees</a></li>
				 -->
				<!--  
				<li><a href="<spring:url value="/listvolunteertasks/1/current" htmlEscape="true" />"><i class="glyphicon glyphicon-tasks"></i> Volunteer tasks</a></li>
				-->
				<li><a href="<spring:url value="/listcups/current" htmlEscape="true" />"><i class="glyphicon glyphicon-list"></i> Cups</a></li>
				<li><a href="<spring:url value="/listmatches/league/1/current" htmlEscape="true" />"><i class="glyphicon glyphicon-list"></i> Matches</a></li>
				<li><a href="<spring:url value="/listseasons" htmlEscape="true" />"><i class="glyphicon glyphicon-list"></i> History</a></li>
				<li><a href="<spring:url value="/calendar/listevents/false/week/0/all/all" htmlEscape="true" />"><i class="glyphicon glyphicon-calendar"></i> Calendar</a></li>
				<li><a href="<spring:url value="/listtournaments/token_xxx" htmlEscape="true" />"><i class="glyphicon glyphicon-calendar"></i> Tournament</a></li>
				
				<!-- 
				<li><a href="<spring:url value="/search" htmlEscape="true" />"><i class="glyphicon glyphicon-search"></i> Search</a></li>
				-->
				<li><a href="#" title="not available yet. Work in progress!!"><i class="glyphicon glyphicon-question-sign"></i> Help</a></li>
	
				<li><a href="<spring:url value="/importdata" htmlEscape="true" />"><i class="glyphicon glyphicon-import"></i> Import</a></li>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li><a href="<spring:url value="/admin/listuseraccounts" htmlEscape="true" />"><i class="glyphicon glyphicon-user"></i> Users</a></li>
					<li><a href="<spring:url value="/admin/list-sessions" htmlEscape="true" />"><i class="glyphicon glyphicon-tasks"></i> Tasks</a></li>
					<li><a href="<spring:url value="/admin" htmlEscape="true" />"><i class="glyphicon glyphicon-cog"></i> Admin</a></li>
				</sec:authorize>
			</ul>
			<sec:authorize access="isFullyAuthenticated()">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<spring:url value="/logout" htmlEscape="true" />"><i class="glyphicon glyphicon-record signout"></i> Sign out</a></li>
					<li>|</li>
					<li><i><sec:authentication property="principal.username" /></i></li> 
				</ul>
			</sec:authorize>
				
			<sec:authorize access="isAnonymous()">
				<form class="navbar-form navbar-right" name="userLoginform" action="/login.do" method="POST">
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
