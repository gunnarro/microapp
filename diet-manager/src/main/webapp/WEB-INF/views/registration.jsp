<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<jsp:include page="public/head-includes.jsp" />
</head>
<body>
	
<script type="text/javascript">
jQuery(document).ready(function($) {
	$("#registerUserFormBtn").click(function(event) {
	var restUrl = "/diet-manager/register";
	var data = {};
	data["firstName"] = $("#firstName").val();
	data["lastName"] = $("#lastName").val();
	data["email"] = $("#email").val();
	data["password"] = $("#password").val();
	console.info("Form data: ..." + JSON.stringify(data));
	$.ajax({
		url : restUrl,
		type : "POST",
		data : JSON.stringify(data),
		dataType : "json",
		timeout : 10000,
		cache : false,
		contentType : "application/json",
		before : function() {
		},
		success : function(response) {
			console.log("Rest Response: " + response);
		},
		error : function(jqXhr, textStatus, errorThrown) {
			console.error("Error: " + JSON.stringify(jqXhr) + ", Status: " + textStatus + ", REST: " + restUrl + ", ErrThrown: " + errorThrown);
		}
	});
  });
});
</script>

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
					Registrer bruker 
					<c:if test="${user.userId != null}">
						for ${user.userId}
					</c:if>
				</h3>
			</div>
			<!-- end panel header -->
			<div id="infoMsg"></div>
			<div class="panel-body">
				<form id="registrerUserForm" >
						<div class="form-group">
							<label>Fistname</label>
							<input id="firstName" type="text" class="form-control" value="${user.firstName}" />
						</div>
					
						<div class="form-group">
							<label>Lastname</label>
							<input id="lastName" type="text" class="form-control" value="${user.lastName}" />
						</div>

						<div class="form-group">
							<label>Email</label>
							<input id="email" type="text" class="form-control" value="${user.email}" />
						</div>
					
						<div class="form-group">
							<label>Password</label>
							<input id="password" type="password" class="form-control" value="${user.password}" />
						</div>
				</form>
				<button id="registerUserFormBtn" class="btn btn-primary">Registrer</button>
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