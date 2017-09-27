<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<datatables:table id="match" data="${matchList}" row="match"
	theme="bootstrap3" cssClass="table table-striped" pageable="true"
	info="true">
	<c:set var="statusIconCls"
		value="glyphicon glyphicon-flag match-status-not-played-color" />
	<c:set var="lblCls" value="label label-primary" />
	<c:set var="dateColor" value="label label-primary" />

	<c:choose>
		<c:when test="${match.startWeekDayName == 'Sat'}">
			<c:set var="dateColor" value="label label-default" />
		</c:when>
		<c:when test="${match.startWeekDayName == 'Sun'}">
			<c:set var="dateColor" value="label label-primary" />
		</c:when>
		<c:when test="${match.startWeekDayName == 'Mon'}">
			<c:set var="dateColor" value="label label-success" />
		</c:when>
		<c:when test="${match.startWeekDayName == 'Tue'}">
			<c:set var="dateColor" value="label label-info" />
		</c:when>
		<c:when test="${match.startWeekDayName == 'Wen'}">
			<c:set var="dateColor" value="label label-warning" />
		</c:when>
		<c:when test="${match.startWeekDayName == 'Thu'}">
			<c:set var="dateColor" value="label label-danger" />
		</c:when>
		<c:when test="${match.startWeekDayName == 'Fri'}">
			<c:set var="dateColor" value="label label-default" />
		</c:when>
		<c:otherwise>
			<c:set var="dateColor" value="label label-default" />
		</c:otherwise>
	</c:choose>
	<c:if test="${match.played}">
		<c:set var="statusIconCls"
			value="glyphicon glyphicon-flag match-status-played-color" />
		<c:set var="lblCls" value="label label-success" />
	</c:if>
	<c:if test="${match.cancelled}">
		<c:set var="statusIconCls"
			value="glyphicon glyphicon-flag match-status-cancelled-color" />
		<c:set var="lblCls" value="label label-warning" />
	</c:if>
	<c:if test="${match.postponed}">
		<c:set var="statusIconCls"
			value="glyphicon glyphicon-flag match-status-postponed-color" />
		<c:set var="lblCls" value="label label-warning" />
	</c:if>
	<c:if test="${match.ongoing}">
		<c:set var="statusIconCls"
			value="glyphicon glyphicon-flag match-status-ongoing-color" />
		<c:set var="lblCls" value="label label-Primary" />
	</c:if>

	<datatables:column title="#" cssStyle="width: 15px !important;">
		<span class="${statusIconCls}"></span>
	</datatables:column>

	<datatables:column title="Date">
		<span class="label label-primary">${match.startWeekDayName}</span>
		<span class="label label-primary"><fmt:formatDate value="${match.startDate}" pattern="dd.MM.yy" /></span>
		<span class="label label-primary"><fmt:formatDate value="${match.startDate}" pattern="HH:mm" /></span>
	</datatables:column>

	<datatables:column title="Match">
		<c:out value="${match.teamVersus}" />
	</datatables:column>

	<datatables:column title="">
		<c:if test="${match.played}">
			<c:out value="${match.result}" />
		</c:if>
	</datatables:column>

	<datatables:column title="Venue">
		<c:out value="${match.venue}" />
	</datatables:column>

	<datatables:column title="Referee" cssStyle="width: 100px;">
		<spring:url value="/referee/{refereeId}" var="refereeUrl">
			<spring:param name="refereeId" value="${match.referee.id}" />
		</spring:url>
		<a href="${fn:escapeXml(refereeUrl)}"><c:out value="${match.referee.fullName}" /></a>
	</datatables:column>

	<datatables:column title="#" sortable="true">
		<spring:url value="/match/edit/{matchId}" var="editUrl">
			<spring:param name="matchId" value="${match.id}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

		<spring:url value="/match/delete/{matchId}" var="deleteUrl">
			<spring:param name="matchId" value="${match.id}" />
		</spring:url>
		<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

		<spring:url value="/match/{matchId}" var="viewUrl">
			<spring:param name="matchId" value="${match.id}" />
		</spring:url>
		<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">registrer
			<span class="badge"><c:out
					value="${match.numberOfSignedPlayers}" /></span>
		</a>

	</datatables:column>
</datatables:table>