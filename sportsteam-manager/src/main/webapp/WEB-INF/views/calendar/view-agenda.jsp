<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
	<jsp:useBean id="toDay" class="java.util.Date" />

	<jsp:include page="../public/diet-body-header.jsp" />

	<div class="container-fluid">

		<div class="page-header">
			<h4>Agenda</h4>
		</div>

		<table id="calendarEventTbl" class="table table-condensed">
			<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
			<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>

			<thead>
				<tr class="info">
					<th class="text-left">${agenda.name}</th>
					<th class="text-right"><spring:url value="/calendar/event/agenda/edit/{agendaId}" var="editUrl" htmlEscape="true">
							<spring:param name="agendaId" value="${agenda.id}" />
						</spring:url> <a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-xs">
							<span class="${editIcon}"></span>
					</a></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="keyValuePair" items="${agenda.items}">
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
							<c:set var="nameColor" value="label label-muted" />
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
								<spring:url value="/calendar/event/{eventId}" var="viewUrl" htmlEscape="true">
									<spring:param name="eventId" value="${agenda.eventId}" />
								</spring:url> 
								<a href="${fn:escapeXml(viewUrl)}"> <i  class="${playedIcon}"></i></a> 
								<small><span class="${txtColor}">&nbsp;${agendaItem}</span></small>
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2">
						<small>
							<span class="${txtColor}">500 ml vann/saft hver dag</span><br>
							<span class="${txtColor}">Ikke lett produkter</span><br>
							<span class="${txtColor}">Spisetid 30 minutter</span>
						</small>
					</td>
				</tr>
			</tfoot>
		</table>

		<jsp:include page="../public/footer.jsp" />
	</div>

</body>

</html>