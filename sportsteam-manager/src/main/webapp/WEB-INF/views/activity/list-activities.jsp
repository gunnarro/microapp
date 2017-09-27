<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">

		<div class="page-header">
			<h4>
				<spring:url value="/listteams/{clubId}" var="viewUrl">
					<spring:param name="clubId" value="${team.club.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}"><c:out value="${team.name}" /></a>
			</h4>
			<h5>
				Activities for
				<c:out value="${season.period}" />
			</h5>
		</div>

		<datatables:table id="activity" data="${activityList}" row="activity" theme="bootstrap3" cssClass="table table-striped" pageable="false" info="false">

			<c:choose>
				<c:when test="${activity.match}">
					<c:set var="activityViewLink" value="/match/{matchId}" />
					<c:set var="activityDeleteLink" value="/match/delete/{matchId}" />
					<c:set var="activityEditLink" value="/match/edit/{matchId}" />
					<c:set var="paramIdName" value="matchId" />
					<c:set var="typeClass" value="glyphicon glyphicon-tag match-color" />
				</c:when>
				<c:when test="${activity.training}">
					<c:set var="activityViewLink" value="/training/{trainingId}" />
					<c:set var="activityDeleteLink" value="/training/delete/{trainingId}" />
					<c:set var="activityEditLink" value="/training/edit/{trainingId}" />
					<c:set var="paramIdName" value="trainingId" />
					<c:set var="typeClass" value="glyphicon glyphicon-pushpin training-color" />
				</c:when>
				<c:when test="${activity.cup}">
					<c:set var="activityViewLink" value="/cup/{cupId}" />
					<c:set var="activityDeleteLink" value="/cup/delete/{cupId}" />
					<c:set var="activityEditLink" value="/cup/edit/{cupId}" />
					<c:set var="paramIdName" value="cupId" />
					<c:set var="typeClass" value="glyphicon glyphicon-tags cup-color" />
				</c:when>
				<c:otherwise>
					<c:set var="activityViewLink" value="#" />
					<c:set var="activityDeleteLink" value="#" />
					<c:set var="paramIdName" value="" />
					<c:set var="typeClass" value="glyphicon glyphicon-remove-circle error-color" />
				</c:otherwise>
			</c:choose>

			<datatables:column title="#">
				<span class="${typeClass}"></span>
			</datatables:column>
			<datatables:column title="Date" cssStyle="width: 60px;">
				<fmt:formatDate value="${activity.startDate}" pattern="dd.MM.yyyy" />
			</datatables:column>
			<datatables:column title="Time" cssStyle="width: 60px;">
				<fmt:formatDate value="${activity.startDate}" pattern="HH:mm" />
			</datatables:column>
			<datatables:column title="Description">
				<spring:url value="${activityViewLink}" var="viewUrl">
					<spring:param name="${paramIdName}" value="${activity.id}" />
					<spring:param name="teamId" value="${team.id}" />
				</spring:url>
				<!-- 
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-link btn-sm"><c:out value="${activity.description}" /></a>
				 -->
				<c:out value="${activity.description}" />
			</datatables:column>
			<datatables:column title="Place">
				<c:out value="${activity.place}" />
			</datatables:column>
			<datatables:column title="#">
				<spring:url value="${activityEditLink}" var="editUrl">
					<spring:param name="${paramIdName}" value="${activity.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

				<spring:url value="${activityDeleteLink}" var="deleteUrl">
					<spring:param name="${paramIdName}" value="${activity.id}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

				<spring:url value="${activityViewLink}" var="editUrl">
					<spring:param name="${paramIdName}" value="${activity.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">registrer <span class="badge"><c:out value="${activity.numberOfSignedPlayers}" /></span></a>

			</datatables:column>
		</datatables:table>

		<spring:url value="/training/new/{teamId}" var="addUrl">
			<spring:param name="teamId" value="${team.id}" />
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">Add Training</a>

		<spring:url value="/match/new/{teamId}" var="addUrl">
			<spring:param name="teamId" value="${team.id}" />
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">Add match</a>

		<spring:url value="/cup/new" var="addUrl">
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">Add Cup</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>