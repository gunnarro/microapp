<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container">
		
		<ul class="breadcrumb">
        	<li><a href="<spring:url value="/calendar/listevents/false/day/0/all/all" htmlEscape="true" />" >Calendar</a></li>
        	<li><a href="<spring:url value="/config/listcalendarconfig" htmlEscape="true" />">Configuration</a></li>
        	<c:choose>
	        	<c:when test="${config['new']}">
					<li class="active">New</li>
				</c:when>
				<c:otherwise>
					<li class="active">Update</li>
				</c:otherwise>
			</c:choose>
    	</ul>
		
		<c:choose>
			<c:when test="${config['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
		
			<div class="panel-heading clearfix">
				<c:if test="${not empty config.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/config/calendar/delete/{configId}" var="deleteUrl">
								<spring:param name="configId" value="${config.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${config['new']}">New </c:if>
					Calendar Configuration
				</h1>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="config" method="${method}" id="calendar-config-form">

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					<div class="form-group">
						<form:label path="name">Activate</form:label>
						<form:checkbox cssClass="form-control" path="name" value="${config.enabled}" />
					</div>
					
					<div class="form-group">
						<form:label path="name">Name</form:label>
						<form:input cssClass="form-control" path="name" value="${config.name}" />
					</div>

					<div class="form-group">
						<form:label path="url">Url</form:label>
						<form:input cssClass="form-control" path="url" value="${config.url}" />
					</div>

					<div class="form-group">
						<form:label path="format">Format</form:label>
						<form:input cssClass="form-control" path="format" value="${config.format}" />
					</div>
					
					<div class="form-group">
						<form:label path="description">Description</form:label>
						<form:input cssClass="form-control" path="description" value="${config.description}" />
					</div>
					
					<div class="form-group">
						<form:label path="teamName">Team name</form:label>
						<form:input cssClass="form-control" path="teamName" value="${config.teamName}" />
					</div>
					
					<c:choose>
						<c:when test="${config['new']}">
							<button type="submit" class="btn btn-primary">Add configuration</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update configuration</button>
						</c:otherwise>
					</c:choose>
										
				</form:form>
			</div>
			<!-- end panle body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/config/listcalendarconfig" var="listUrl">
					</spring:url>
					<a href="${fn:escapeXml(listUrl)}" class="btn btn-default btn-sm">Cancel</a>
				</div>
			</div>
			<!-- end panel footer -->
		</div>
		<!-- end panel -->
	</div>
	<!-- end container -->

	<jsp:include page="../public/footer.jsp" />
</body>
</html>