<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<jsp:include page="public/head-tag.jsp" />

<style type="text/css">
.jumbotron {
    background: #fff url("/b39/resources/images/jumbotron-bg-b39.png") no-repeat center center fixed;;
	background-size: cover;
    -moz-background-size: cover;
    -webkit-background-size: cover;
    -o-background-size: cover;
}

</style>
</head>

<body class="jumbotron">
<div>  
	<div class="container">
      <form class="form-signin" name="form" action="<spring:url value="/perform-login" htmlEscape="true" />" method="POST">
        <sec:csrfInput />
		<c:if test="${param.loggedout != null}">
			<div class="alert alert-success" role="alert">
				You are successfully logged out!
			</div>
		</c:if>
		<c:if test="${param.cause != null}">
			<div class="alert alert-info" role="alert">
				You are redirected to login page because:
				<c:out value="${param.cause}" />
			</div>
		</c:if>
		<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
			<div class="alert alert-danger" role="alert">
				Login failed:
				<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
			</div>
		</c:if>
        
        <label for="inputUserName" class="sr-only">User name</label>
        <input type="text" id="inputUserName" name="username" class="form-control form-control-login input-lg" placeholder="Username" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="password" class="form-control form-control-login input-lg" placeholder="Password" required>
        <button class="btn-primary btn btn-lg btn-block" type="submit">Sign In</button>
       
        <div class="row">
          <div class="col-xs-12 col-sm-6 col-md-8">
            <input type="checkbox" value="_spring_security_remember_me"> Remember me
          </div>
		</div>
      </form>
    </div> <!-- /container -->
 </div>
</body>
</html>