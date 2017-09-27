<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<style type="text/css">
.panel-heading a:after {
	font-family: 'Glyphicons Halflings';
	content: "\e114";
	float: right;
	color: grey;
}

.panel-heading a.collapsed:after {
	content: "\e080";
}
</style>

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:useBean id="toDay" class="java.util.Date" />

	<jsp:include page="../public/body-header.jsp" />

	<div class="container-fluid">

		<ul class="breadcrumb">
			<li><a href="<spring:url value="/home" htmlEscape="true" />">B39 logs</a></li>
			<li class="active">Event Log</li>
		</ul>

		<div class="page-header">
			<h4>Event Log</h4>
			<spring:url value="/log/event/new" var="addUrl">
			</spring:url>
			<a href="${fn:escapeXml(addUrl)}" class="btn btn-primary btn-xs">Ny logg</a>
		</div>
		
		<div class="panel-group" id="accordion">
			<c:forEach var="log" items="${logs}" varStatus="loop">
				<c:set var="levelClass" value="label label-info" />
				<c:if test="${log.level == 'CONFLICT'}">
					<c:set var="levelClass" value="label label-danger" />		
				</c:if>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h6 class="panel-title">
							 <span class="label label-primary">
							 	<fmt:formatDate value="${log.createdDate}" pattern="EEEE dd.MM.yyyy" />
							 </span>
							 <span class="label label-success">
							 	<fmt:formatDate value="${log.createdDate}" pattern="HH:mm" />
							 </span>
							 <span class="${levelClass}">${log.level}</span>
							 <span>
							 	<a data-toggle="collapse" data-parent="#accordion" href="#collapse_${loop.index}">${log.title}</a>
							</span>
							</h6>
					</div>
					<div id="collapse_${loop.index}" class="panel-collapse collapse in">
						<div class="panel-body">
							<p>
								<small>${log.content}</small>
							</p>
						</div>
						<div class="panel-footer clearfix">
							<div class="pull-left">
								<small><span>Created by ${log.createdByUser}</span></small>
							</div>
							<div class="pull-right">
								 <spring:url value="/log/event/edit/{logEventId}" var="addUrl">
									<spring:param name="logEventId" value="${log.id}" />
								</spring:url>
								<a href="${fn:escapeXml(addUrl)}" class="btn btn-default btn-xs">
									<i class="glyphicon glyphicon-edit"></i>
								</a>
								<spring:url value="/log/event/delete/{logEventId}" var="deleteUrl">
									<spring:param name="logEventId" value="${log.id}" />
								</spring:url>
								<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default btn-xs">
									<i class="glyphicon glyphicon-trash"></i>
								</a>
							</div>
						</div>
					</div>
				</div>

			</c:forEach>
		</div>
	</div>
	<jsp:include page="../public/footer.jsp" />
</body>
</html>