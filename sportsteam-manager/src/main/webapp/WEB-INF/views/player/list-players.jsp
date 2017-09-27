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
				<spring:url value="/listteams/{clubId}" var="viewUrl">
					<spring:param name="clubId" value="${team.club.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}"><c:out value="${team.name}" /></a>
			</h4>
			<h5>Players</h5>
		</div>

		<datatables:table id="player" data="${playerList}" row="player" theme="bootstrap3" cssClass="table table-striped" pageable="false" info="false">
			<c:set var="genderIcon" value="/resources/images/female.png" />
			<c:if test="${player.male}">
				<c:set var="genderIcon" value="/resources/images/male.png" />
			</c:if>
			<c:set var="statusClass" value="text-muted" />
			<c:if test="${player.active}">
				<c:set var="statusClass" value="text-primary" />
			</c:if>
			<datatables:column title="#">
				<img src="${genderIcon}" class="img-circle">
			</datatables:column>
			<datatables:column title="Name">
				<spring:url value="/player/{playerId}" var="viewUrl">
					<spring:param name="playerId" value="${player.id}" />
				</spring:url>
				<a class="${statusClass}" href="${fn:escapeXml(viewUrl)}">${player.fullName}</a>
			</datatables:column>
			<datatables:column title="Mobile">
				<c:if test="${not empty player.mobileNumber}">
					<a href="tel://${player.mobileNumber}"><i class="glyphicon glyphicon-earphone"></i> ${player.mobileNumber}</a>
				</c:if>
			</datatables:column>
			<datatables:column title="Email">
				<c:if test="${not empty player.emailAddress}">
					<a href="mailto://${player.emailAddress}"><i class="glyphicon glyphicon-envelope"></i> ${player.emailAddress}</a>
				</c:if>
			</datatables:column>
			<datatables:column title="#">
				<spring:url value="/player/edit/{playerId}" var="editUrl">
					<spring:param name="playerId" value="${player.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

				<spring:url value="/player/delete/{playerId}" var="deleteUrl">
					<spring:param name="playerId" value="${player.id}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

				<spring:url value="/player/edit/{playerId}" var="editUrl">
					<spring:param name="playerId" value="${player.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">parents</a>
			</datatables:column>
		</datatables:table>

		<spring:url value="/player/new/{teamId}" var="addUrl">
			<spring:param name="teamId" value="${team.id}" />
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New Player</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>