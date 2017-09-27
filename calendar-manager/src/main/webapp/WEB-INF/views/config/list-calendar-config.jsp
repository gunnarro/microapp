<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<style type="text/css">
	.table-condensed>thead>tr>th, 
	.table-condensed>tbody>tr>th,
	.table-condensed>tfoot>tr>th, 
	.table-condensed>thead>tr>td,
	.table-condensed>tbody>tr>td, 
	.table-condensed>tfoot>tr>td {padding: 3px;}
</style>

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">

		<ul class="breadcrumb">
        	<li><a href="<spring:url value="/calendar/listevents/false/day/0/all/all" htmlEscape="true" />" >Calendar</a></li>
        	<li class="active">Configuration</li>
    	</ul>
		
		<div class="page-header">
			<h4>Calendar</h4>
			<h5>Configuration</h5>
		</div>

		<table id="imagesTbl" class="table table-condensed">
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>
			<thead>
				<tr class="info">
					<th colspan="7" class="text-left">Configuration</th>
				</tr>
				<tr>
					<th>#</th>
					<th>Name</th>
					<th>Url</th>
					<th>Format</th>
					<th>Description</th>
					<th>Team</th>
					<th>#</th>
				</tr>
			</thead>
			</tbody>
			<c:forEach var="configuration" items="${calendarConfigList}">
				<c:set var="statusIconCls" value="glyphicon glyphicon-unchecked unchecked-color" />
				<c:if test="${configuration.enabled}">
					<c:set var="statusIconCls" value="glyphicon glyphicon-check checked-color" />
				</c:if>
				<tr>
					<td class="text-left"><span class="${statusIconCls}"></span></td>
					<td>${configuration.name}</td>
					<td><a href="${configuration.url}">${configuration.url}</a></td>
					<td>${configuration.format}</td>
					<td>${configuration.description}</td>
					<td>${configuration.teamName}</td>
					<td>
						<spring:url value="/config/calendar/edit/{configId}" var="editUrl">
							<spring:param name="configId" value="${configuration.id}" />
						</spring:url>
						<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>
		
						<spring:url value="/config/calendar/verify/{configId}" var="verifyUrl">
							<spring:param name="configId" value="${configuration.id}" />
						</spring:url>
						<a href="${fn:escapeXml(verifyUrl)}" class="btn btn-primary btn-xs">verify</a>
						
						<spring:url value="/config/calendar/delete/{configId}" var="deleteUrl">
							<spring:param name="configId" value="${configuration.id}" />
						</spring:url>
						<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr class="active">
					<td colspan="7" class="text-left">
					</td>
				</tr>
			</tfoot>
		</table>
		
		<spring:url value="/config/calendar/new" var="addUrl">
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Configuration</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>