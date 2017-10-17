<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<head>
	<jsp:include page="../public/head-includes.jsp" />
	<style type="text/css">
		.table-condensed>thead>tr>th, 
		.table-condensed>tbody>tr>th,
		.table-condensed>tfoot>tr>th, 
		.table-condensed>thead>tr>td,
		.table-condensed>tbody>tr>td, 
		.table-condensed>tfoot>tr>td {padding: 3px;}
	</style>
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />

	<jsp:useBean id="toDay" class="java.util.Date" />

	<div class="container-fluid">

		<ul class="breadcrumb">
        	<li><a href="<spring:url value="/diet/listdietplans" htmlEscape="true" />" >Diet Plans</a></li>
        	<li class="active">Diet Plan</li>
    	</ul>
    	
		<div class="page-header">
			<h4>${dietPlan.name} ${dietPlan.periode}</h4>
			<h5>
				<c:set var="status" value="Ikke aktiv" />
				<c:if test="${dietPlan.active}" >
					<c:set var="status" value="Aktiv" />
				</c:if>
				${status}
			</h5>
		</div>

		<table id="dietPlanTbl" class="table table-condensed">
			<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
			<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>

			<thead>
				<tr class="info">
					<th colspan="2" class="text-left">${dietPlan.description}</th>
					<%--
					<th class="text-right"><spring:url value="/diet/dietplan/edit/{dietPlanId}" var="editUrl" htmlEscape="true">
							<spring:param name="dietPlanId" value="${dietPlan.id}" />
						</spring:url> <a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-xs">
							<span class="${editIcon}"></span>
					</a></th>
					 --%>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="keyValuePair" items="${dietPlan.planItems}">
					<c:set var="nameColor" value="label label-primary" />
					<c:set var="txtColor" value="text-primary" />
					<c:set var="eventIcon" value="glyphicon glyphicon-user" />
					<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
					<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />

					<c:choose>
						<c:when test="${fn:startsWith(keyValuePair.key, 'Frokost')}">
							<c:set var="nameColor" value="label label-danger" />
						</c:when>
						<c:when test="${fn:startsWith(keyValuePair.key, 'Lunsj')}">
							<c:set var="nameColor" value="label label-primary" />
						</c:when>
						<c:when test="${fn:startsWith(keyValuePair.key, 'Middag')}">
							<c:set var="nameColor" value="label label-success" />
						</c:when>
						<c:when test="${fn:startsWith(keyValuePair.key, 'Kvelds')}">
							<c:set var="nameColor" value="label label-info" />
						</c:when>
						<c:otherwise>
							<c:set var="nameColor" value="label label-warning" />
						</c:otherwise>
					</c:choose>

					<tr id="date_${agenda.id}" class="active">
						<td colspan="2" class="text-left">
							<span class="${nameColor}">${keyValuePair.key}</span>
						</td>
					</tr>

					<c:forEach var="agendaItem" items="${keyValuePair.value}">
						<c:set var="txtColor" value="text-muted" />
						<c:set var="playedIcon" value="glyphicon glyphicon-check" />
						<c:set var="editIcon" value="glyphicon glyphicon-edit muted-color" />
						<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle muted-color" />
						<tr id="row_${agenda.id}">
							<td colspan="2" class="text-left" style="border-top: none;">
								<small><i class="${playedIcon}"></i><span class="${txtColor}">&nbsp;${agendaItem}</span></small>
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr class="active">
						<td colspan="2" class="text-left">
							<span class="label label-default">Generelt/Regler</span>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<small>
							<ul>
								<c:forEach var="keyValuePair" items="${dietPlan.dietPlanRules}">
									<li><span class="${txtColor}">${keyValuePair.value}</span></li>
								</c:forEach>
							</ul>
						</small>
							<spring:url value="/diet/changelist" var="changeListUrl" htmlEscape="true">
								<spring:param name="dietPlanId" value="${dietPlan.id}" />
							</spring:url> 
							<small>
								Se <a href="${fn:escapeXml(changeListUrl)}" >bytteliste</a> for bytte av mat. 
						</small>
					</td>
				</tr>
				<tr class="info">
					<td colspan="2" class="text-right">
						<spring:url value="/diet/listdietplans" var="backUrl" >
						</spring:url>
						<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm"><spring:message code="btn.cancel"/></a>
					</td>
				</tr>
			</tfoot>
		</table>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>