<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<div class="pull-right">
					<spring:url value="/club/edit/{clubId}" var="editUrl">
						<spring:param name="clubId" value="${club.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>

					<spring:url value="/listteams/{clubId}/" var="viewUrl">
						<spring:param name="clubId" value="${club.id}" />
					</spring:url>
					<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-sm">Teams</a>
					
					<spring:url value="/listmatches/{clubId}/{season}" var="viewUrl">
						<spring:param name="clubId" value="${club.id}" />
						<spring:param name="season" value="current" />
					</spring:url>
					<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-sm">Activities</a>
					
				</div>
				<h1 class="panel-title">${club.name}</h1>
			</div>

			<div class="panel-body center-block">
				<dl class="dl-horizontal">
					<dt>Name</dt>
					<dd>
						<span class="text-primary">${club.name}</span>
					</dd>

					<dt>Department</dt>
					<dd>${club.departmentName}</dd>

					<dt>Stadium</dt>
					<dd>${club.stadiumName}</dd>

					<dt>Street</dt>
					<dd>${club.address.fullStreetName}</dd>

					<dt>City</dt>
					<dd>${club.address.city}</dd>

					<dt>Country</dt>
					<dd>${club.address.country}</dd>

					<dt>HomePage</dt>
					<dd>
						<a href="<c:out value="${club.homePageUrl}"/>">${club.homePageUrl}</a>
					</dd>
					
					<dt>Number of teams</dt>
					<dd>${club.numberOfTeams}
						<spring:url value="/listteams/{clubId}" var="viewUrl">
							<spring:param name="clubId" value="${club.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}"><span class="glyphicon glyphicon-user player-color"></span></a>
					</dd>
					
					<dt>Number of players</dt>
					<dd>${club.numberOfPlayers}
						<spring:url value="/listplayers/{teamId}" var="viewUrl">
							<spring:param name="teamId" value="${club.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}"><span class="glyphicon glyphicon-user player-color"></span></a>
					</dd>
					
					<dt>Number of referees</dt>
					<dd>${club.numberOfReferees}
						<spring:url value="/listreferees/{clubId}" var="viewUrl">
							<spring:param name="clubId" value="${club.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}"><span class="glyphicon glyphicon-user player-color"></span></a>
					</dd>
				</dl>
			</div>

			<div class="panel-footer clearfix">
				<div class="pull-left">
					<spring:url value="/team/new/{clubId}" var="addUrl">
						<spring:param name="clubId" value="${club.id}" />
					</spring:url>
					<a href="${fn:escapeXml(addUrl)}" class="btn btn-success btn-sm">New Team</a>

					<spring:url value="/referee/new/{clubId}" var="addUrl">
						<spring:param name="clubId" value="${club.id}" />
					</spring:url>
					<a href="${fn:escapeXml(addUrl)}" class="btn btn-success btn-sm">New Referee</a>

					<spring:url value="/volunteertask/new/{clubId}" var="addUrl">
						<spring:param name="clubId" value="${club.id}" />
					</spring:url>
					<a href="${fn:escapeXml(addUrl)}" class="btn btn-success btn-sm">New Volunteer Task</a>
				</div>
				<div class="pull-right">
					<spring:url value="/listclubs" var="viewUrl">
					</spring:url>
					<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-sm">Back</a>
				</div>
			</div>
		</div>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>