<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>


<datatables:table id="cup" data="${cupList}" row="cup" theme="bootstrap3" cssClass="table table-striped" pageable="true" info="true">
	<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-not-played-color" />
	<c:if test="${cup.finished}">
		<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-played-color" />
	</c:if>
	
	<datatables:column title="#">
		<span class="${statusIconCls}"></span>
	</datatables:column>
	
	<datatables:column title="Date">
		<span class="label label-primary">${cup.startWeekDayName}</span>
		<span class="label label-primary"><fmt:formatDate value="${cup.startDate}" pattern="dd.MM.yy" /></span>
		<span class="label label-primary"><fmt:formatDate value="${cup.startDate}" pattern="HH:mm" /></span>
	</datatables:column>

	<datatables:column title="Name">
		<c:out value="${cup.cupName}" />
	</datatables:column>

	<datatables:column title="Venue">
		<c:out value="${cup.venue}" />
	</datatables:column>

	<datatables:column title="#" sortable="false">
		<spring:url value="/cup/edit/{cupId}" var="editUrl">
			<spring:param name="cupId" value="${cup.id}" />
			<spring:param name="teamId" value="${cup.fkTeamId}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

		<spring:url value="/cup/delete/{cupId}/{teamId}" var="deleteUrl">
			<spring:param name="cupId" value="${cup.id}" />
			<spring:param name="teamId" value="${cup.fkTeamId}" />
		</spring:url>
		<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

		<c:choose>
			<c:when test="${isTeamView == 'true'}">
				<spring:url value="/cup/{cupId}/{teamId}" var="regPlayerUrl">
					<spring:param name="cupId" value="${cup.id}" />
					<spring:param name="teamId" value="${cup.fkTeamId}" />
				</spring:url>
				<a href="${fn:escapeXml(regPlayerUrl)}" class="btn btn-primary btn-xs">registrer player <span class="badge"><c:out value="${cup.numberOfSignedPlayers}" /></span></a>
			</c:when>
			<c:otherwise>
				<spring:url value="/cup/{cupId}" var="regTeamUrl">
					<spring:param name="cupId" value="${cup.id}" />
				</spring:url>
				<a href="${fn:escapeXml(regTeamUrl)}" class="btn btn-primary btn-xs">registrer team <span class="badge"><c:out value="${cup.numberOfRegistreredTeams}" /></span></a>
			</c:otherwise>
		</c:choose>

	</datatables:column>
</datatables:table>