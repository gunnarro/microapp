<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">

		<div class="page-header">
			<h4>
				<spring:url value="/listclubs" var="viewUrl">
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}">${clubName}</a>
			</h4>
			<h5>Teams</h5>
		</div>

		<datatables:table id="team" data="${teamList}" row="team" theme="bootstrap3" cssClass="table table-striped" pageable="false" info="false">
			<datatables:column title="#">
				<c:choose>
					<c:when test="${team.male}">
						<span class="glyphicon glyphicon-user male-color"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-heart female-color"></span>
					</c:otherwise>
				</c:choose>
			</datatables:column>
			
			<datatables:column title="Name">
				<spring:url value="/team/{teamId}" var="viewUrl">
					<spring:param name="teamId" value="${team.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}">${team.name}</a>
			</datatables:column>
			
			<datatables:column title="League">
				<spring:url value="/league/{leagueId}" var="viewUrl">
					<spring:param name="leagueId" value="${team.league.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}">${team.league.name}</a>
			</datatables:column>
			
			<datatables:column title="Team lead">
				<spring:url value="/contact/{contactId}" var="viewUrl">
					<spring:param name="contactId" value="${team.teamLead.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}">${team.teamLead.fullName}</a>
			</datatables:column>
			
			<datatables:column title="Coach">
				<spring:url value="/contact/{contactId}" var="viewUrl">
					<spring:param name="contactId" value="${team.coach.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}">${team.coach.fullName}</a>
			</datatables:column>
			
			<datatables:column title="#">
				<spring:url value="/team/edit/{teamId}" var="editUrl">
					<spring:param name="teamId" value="${team.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>
				
				<spring:url value="/team/delete/{teamId}" var="deleteUrl">
					<spring:param name="teamId" value="${team.id}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

				<spring:url value="/listplayers/{teamId}" var="viewUrl">
					<spring:param name="teamId" value="${team.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">players <span class="badge">${team.numberOfPlayers}</span></a>

				<spring:url value="/listcontacts/{teamId}" var="viewUrl">
					<spring:param name="teamId" value="${team.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">contacts <span class="badge">${team.numberOfContacts}</span></a>

				<spring:url value="/listactivities/{teamId}/{season}" var="viewUrl">
					<spring:param name="teamId" value="${team.id}" />
					<spring:param name="season" value="current" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">Activities</a>

			</datatables:column>
		</datatables:table>

		<spring:url value="/team/new" var="addUrl">
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Team</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>