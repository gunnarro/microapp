<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>


<datatables:table id="tournament" data="${tournamentList}" row="tournament" theme="bootstrap3" cssClass="table table-striped" pageable="true" info="true">
	<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-not-played-color" />
	<c:if test="${tournament.finished}">
		<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-played-color" />
	</c:if>
	
	<datatables:column title="#">
		<span class="${statusIconCls}"></span>
	</datatables:column>
	
	<datatables:column title="Date">
		<span class="label label-primary">${tournament.startWeekDayName}</span>
		<span class="label label-primary"><fmt:formatDate value="${tournament.startDate}" pattern="dd.MM.yy" /></span>
		<span class="label label-primary"><fmt:formatDate value="${tournament.startDate}" pattern="HH:mm" /></span>
	</datatables:column>

	<datatables:column title="Name">
		<spring:url value="/tournament/{tournamentId}" var="viewUrl" htmlEscape="true">
			<spring:param name="tournamentId" value="${tournament.tournamentName}" />
		</spring:url>
		<a href="${fn:escapeXml(viewUrl)}" >${tournament.tournamentName}</a>
	</datatables:column>

	<datatables:column title="venue">
		<c:out value="${tournament.venue}" />
	</datatables:column>

	<datatables:column title="#" sortable="false">
		<spring:url value="/tournament/{tournamentId}" var="editUrl">
			<spring:param name="tournamentId" value="${tournament.tournamentName}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">View</a>

		<spring:url value="/tournament/delete/{tournamentId}" var="deleteUrl">
			<spring:param name="tournamentId" value="${tournament.tournamentName}" />
		</spring:url>
		<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
		
	</datatables:column>
</datatables:table>