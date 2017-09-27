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
					<spring:url value="/team/edit/{teamId}" var="editUrl">
						<spring:param name="teamId" value="${team.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>

					<spring:url value="/listactivities/{teamId}/{season}" var="viewUrl">
						<spring:param name="teamId" value="${team.id}" />
						<spring:param name="season" value="current" />
					</spring:url>
					<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-sm">Activities</a>
					
					<spring:url value="/listactivities/{teamId}/{season}" var="viewUrl">
						<spring:param name="teamId" value="${team.id}" />
						<spring:param name="season" value="current" />
					</spring:url>
					<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-sm">Tasks</a>
				</div>
				<h1 class="panel-title">${team.name}</h1>
			</div>

			<div class="panel-body center-block">
				<dl class="dl-horizontal">
					<dt>Club</dt>
					<dd>
						<spring:url value="/club/{clubId}" var="viewUrl">
							<spring:param name="clubId" value="${team.fkClubId}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}">${team.club.fullName}</a>
					</dd>

					<dt>Team</dt>
					<dd>${team.name}</dd>

					<dt>Gender</dt>
					<dd>${team.gender}</dd>

					<dt>Year of birth</dt>
					<dd>${team.teamYearOfBirth}</dd>

					<dt>League</dt>
					<dd>
						<spring:url value="/league/{leagueId}" var="viewUrl">
							<spring:param name="leagueId" value="${team.league.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}">${team.league.name}</a>
					</dd>

					<dt>Number of players</dt>
					<dd>${team.numberOfPlayers}
						<spring:url value="/listplayers/{teamId}" var="viewUrl">
							<spring:param name="teamId" value="${team.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}"><span class="glyphicon glyphicon-user player-color"></span></a>
					</dd>

					<dt>Number of contacts</dt>
					<dd>${team.numberOfContacts}
						<spring:url value="/listcontacts/{teamId}" var="viewUrl">
							<spring:param name="teamId" value="${team.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}"><span class="glyphicon glyphicon-user contact-color"></span></a>
					</dd>

					<dt>Teamlead</dt>
					<c:choose>
						<c:when test="${empty team.teamLead}">
							<dd></dd>
						</c:when>
						<c:otherwise>
							<dd>
								<spring:url value="/contact/{contactId}" var="viewUrl">
									<spring:param name="contactId" value="${team.teamLead.id}" />
								</spring:url>
								<a href="${fn:escapeXml(viewUrl)}">${team.teamLead.fullName}</a>
							</dd>
							<c:if test="${not empty team.teamLead.mobileNumber}">
								<dd>
									<a href="tel://${team.teamLead.mobileNumber}"><i class="glyphicon glyphicon-earphone"></i> ${team.teamLead.mobileNumber}</a>
								</dd>
								<dd>
									<a href="sms://${team.teamLead.mobileNumber}"><i class="glyphicon glyphicon-envelope"></i> ${team.teamLead.mobileNumber}</a>
								</dd>
							</c:if>

							<c:if test="${not empty team.teamLead.emailAddress}">
								<dd>
									<a href="mailto://${team.teamLead.emailAddress}"><i class="glyphicon glyphicon-envelope"></i> ${team.teamLead.emailAddress}</a>
								</dd>
							</c:if>
						</c:otherwise>
					</c:choose>

					<dt>Coach</dt>
					<c:choose>
						<c:when test="${empty team.coach}">
							<dd></dd>
						</c:when>
						<c:otherwise>
							<dd>
								<spring:url value="/contact/{contactId}" var="viewUrl">
									<spring:param name="contactId" value="${team.coach.id}" />
								</spring:url>
								<a href="${fn:escapeXml(viewUrl)}">${team.coach.fullName}</a>
							</dd>
							<c:if test="${not empty team.coach.mobileNumber}">
								<dd>
									<a href="tel://${team.coach.mobileNumber}"><i class="glyphicon glyphicon-earphone"></i> ${team.coach.mobileNumber}</a>
								</dd>
								<dd>
									<a href="sms://${team.coach.mobileNumber}"><i class="glyphicon glyphicon-envelope"></i> ${team.coach.mobileNumber}</a>
								</dd>
							</c:if>
							<c:if test="${not empty team.coach.emailAddress}">
								<dd>
									<a href="mailto://${team.coach.emailAddress}"><i class="glyphicon glyphicon-envelope"></i> ${team.coach.emailAddress}</a>
								</dd>
							</c:if>
						</c:otherwise>
					</c:choose>
				</dl>
			</div>

			<!-- 
			<div class="list-group">
				<a class="list-group-item active">
					<h4 class="list-group-item-heading">Upcoming Activities</h4>
				</a>
				<c:forEach var="activity" items="${upcomingActivityList}">
					<a href="#" class="list-group-item">
						<h4 class="list-group-item-heading">
							<fmt:formatDate value="${activity.startDate}" pattern="dd.MM.yyyy HH:mm" />
						</h4>
						<p class="list-group-item-text">${activity.description}</p>
						<p class="list-group-item-text">${activity.place}</p>
					</a>
				</c:forEach>
			</div>
 -->
			<!-- 
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th>Date</th>
							<th>Time</th>
							<th>Activity</th>
							<th>Place</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="activity" items="${upcomingActivityList}">
							<tr>
								<td><fmt:formatDate value="${activity.startDate}" pattern="dd.MM.yyyy HH:mm" /></td>
								<td><fmt:formatDate value="${activity.startDate}" pattern="HH:mm" /></td>
								<td>${activity.description}</td>
								<td>${activity.place}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
 -->
			<div class="panel-footer clearfix">
				<div class="pull-left">
					<spring:url value="/player/new/{teamId}" var="addUrl">
						<spring:param name="teamId" value="${team.id}" />
					</spring:url>
					<a href="${fn:escapeXml(addUrl)}" class="btn btn-success btn-sm">New Player</a>

					<spring:url value="/contact/new/{teamId}" var="addUrl">
						<spring:param name="teamId" value="${team.id}" />
					</spring:url>
					<a href="${fn:escapeXml(addUrl)}" class="btn btn-success btn-sm">New Contact</a>

					<spring:url value="/training/new/{teamId}" var="addUrl">
						<spring:param name="teamId" value="${team.id}" />
					</spring:url>
					<a href="${fn:escapeXml(addUrl)}" class="btn btn-success btn-sm">New Training</a>
				</div>
				<div class="pull-right">
					<spring:url value="/listteams/{clubId}" var="editUrl">
						<spring:param name="clubId" value="${team.fkClubId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-sm">Back</a>
				</div>
			</div>
		</div>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>
