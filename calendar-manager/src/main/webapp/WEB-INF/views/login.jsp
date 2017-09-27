<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<jsp:include page="public/head-tag.jsp" />

<body style="background: #eee !important;">

	<div class="container">
      <form class="form-signin" name="form" action="<spring:url value="/perform-login" htmlEscape="true" />" method="POST">
        <sec:csrfInput />
        <img class="profile-img" src="<spring:url value="/resources/images/signin.jpg?sz=120" htmlEscape="true" />" alt="Pepilie - LogIn">
		
		<c:if test="not empty ${request.getParameter('cause')}">
			<div class="alert alert-info" role="alert">
				You are redirected to login page because:
				<c:out value="${request.getParameter('cause')}" />
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
		  <div class="col-xs-6 col-md-4">
			<p class="text-right">
				<a href="<spring:url value="/home" htmlEscape="true" />">Home</a>
			</p>
		  </div>
		  <div class="col-xs-12 col-sm-6 col-md-8">
		  </div>
		  <div class="col-xs-6 col-md-4">
			<small><font style="color:green">Pep</font><font style="color:blue">ilie</font></small>
		  </div>
		</div>
      </form>
    </div> <!-- /container -->
</body>
</html>