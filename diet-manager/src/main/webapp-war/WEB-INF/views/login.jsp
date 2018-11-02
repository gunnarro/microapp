<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
	<jsp:include page="public/head-includes.jsp" />
	
	<style type="text/css">
	.jumbotron {
	    background: #fff url("images/background.jpg") no-repeat center center fixed;;
		background-size: cover;
	    -moz-background-size: cover;
	    -webkit-background-size: cover;
	    -o-background-size: cover;
	}
	.input-group-margin {
		margin-bottom: 10px;
	}
	
	</style>
</head>
<body class="jumbotron">
<div>  
	<div class="container-fluid">
      <form class="form-signin" name="form" action="<spring:url value="/j_spring_security_check" htmlEscape="true" />" method="POST">
        <sec:csrfInput />
        
        <img src="<spring:url value="/resources/images/logo-pepilie.gif" htmlEscape="true" />" >
		
		<c:if test="${param.loggedout != null}">
			<div class="alert alert-success" role="alert">
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				You are successfully logged out!
			</div>
		</c:if>
		<c:if test="${param.cause != null}">
			<div class="alert alert-info" role="alert">
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				You are redirected to login page because:
				<c:out value="${param.cause}" />
			</div>
		</c:if>
		<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
			<div class="alert alert-danger" role="alert">
				<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
				Login failed:
				<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
			</div>
		</c:if>
       
		<div class="input-group input-group-margin">
			<span class="input-group-addon">
 		       <i class="glyphicon glyphicon-user"></i>
			</span>
			<label for="inputUserName" class="sr-only">User name</label>
			<input id="inputUserName" type="text" name="username" class="form-control input-lg" placeholder="username" required autofocus />
		</div>
		
		<div class="input-group input-group-margin" >
			<span class="input-group-addon">
				<i class="glyphicon glyphicon-lock"></i>
			</span>
			<label for="inputPassword" class="sr-only">Password</label>
			<input id="inputPassword" type="password" name="password" class="form-control input-lg" placeholder="password" required />
		</div>
		
		<button class="btn-primary btn-lg btn-block input-group-margin" type="submit">Sign In</button>
		<input type="checkbox" value="_spring_security_remember_me"> Remember me
      </form>

		
		<form class="form-social" name='facebookSocialloginForm' action="<spring:url value="/auth/facebook?scope=public_profile,email" htmlEscape="true" />" method='POST'>
			<button class="btn btn-block btn-social btn-facebook"><span class="fa fa-facebook"></span> Sign in with Facebook</button>
		</form>
		<!-- 
		<form class="form-social" name='googleSocialloginForm' action="<spring:url value="/auth/google?scope=email,user_about_me,user_birthday" htmlEscape="true" />" method='POST'>
			<button class="btn btn-block btn-social btn-google"><span class="fa fa-google"></span> Sign in with Google</button>
		</form>
		<form class="form-social" name='yahooSocialloginForm' action="<spring:url value="/auth/yahoo?scope=email,user_about_me,user_birthday" htmlEscape="true" />" method='POST'>
			<button class="btn btn-block btn-social btn-yahoo"><span class="fa fa-yahoo"></span> Sign in with Yahoo</button>
        </form>
        <form class="form-social" name='githubSocialloginForm' action="<spring:url value="/auth/github?scope=email,user" htmlEscape="true" />" method='POST'>
			<button class="btn btn-block btn-social btn-github"><span class="fa fa-github"></span> Sign in with GitHub</button>
        </form>
         -->
    </div> <!-- /container -->
 </div>
</body>
</html>
