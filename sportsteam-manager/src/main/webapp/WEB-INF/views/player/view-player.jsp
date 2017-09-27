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
					<spring:url value="/player/edit/{playerId}" var="editUrl">
						<spring:param name="playerId" value="${player.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>
				</div>
				<h1 class="panel-title">${player.fullName}</h1>
			</div>

			<div class="panel-body">
				<dl class="dl-horizontal">
					<dt>Status</dt>
					<dd>${player.status.name}</dd>

					<dt>Team</dt>
					<dd>
						<spring:url value="/team/{teamId}" var="viewUrl">
							<spring:param name="teamId" value="${player.team.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}">${player.team.name}</a>
					</dd>

					<dt>Name</dt>
					<dd>${player.fullName}</dd>

					<dt>Date of birth</dt>
					<dd>
						<fmt:formatDate value="${player.dateOfBirth}" pattern="dd.MM.yyyy" />
					</dd>

					<dt>Address</dt>
					<dd>
						<c:if test="${player.address.addressValid}">
							<address>
								<a href="https://maps.google.com/?q=${player.address.fullAddress}"><i class="glyphicon glyphicon-map-marker"></i> ${player.address.fullAddress}</a> <br>
								${player.address.postCode} ${player.address.city}<br> ${player.address.country}
							</address>
						</c:if>
					</dd>

					<dt>Gender</dt>
					<dd>${player.gender}</dd>

					<dt>Mobile number</dt>
					<dd>
						<c:if test="${not empty player.mobileNumber}">
							<a href="tel://${player.mobileNumber}"><i class="glyphicon glyphicon-earphone"></i> ${player.mobileNumber}</a>
						</c:if>
					</dd>
					<dd>
						<c:if test="${not empty player.mobileNumber}">
							<a href="sms://${player.mobileNumber}"><i class="glyphicon glyphicon-envelope"></i> ${player.mobileNumber}</a>
						</c:if>
					</dd>

					<dt>Email address</dt>
					<dd>
						<c:if test="${not empty player.emailAddress}">
							<a href="mailto://${player.emailAddress}"><i class="glyphicon glyphicon-envelope"></i> ${player.emailAddress}</a>
						</c:if>
					</dd>

					<dt>School</dt>
					<dd>${player.schoolName}</dd>

					<dt>Position</dt>
					<dd>${player.position}</dd>
					
					<dt>Jersy number</dt>
					<dd>${player.jerseyNumber}</dd>
				</dl>

				<div class="page-header">
					<h5>Player statistic</h5>
				</div>

				<dl class="dl-horizontal">
					<dt>Trainings</dt>
					<dd>${playerStatistic.numberOfPlayerTrainings}/${playerStatistic.numberOfTeamTrainings}</dd>

					<dt>Matches</dt>
					<dd>${playerStatistic.numberOfPlayerMatches}/${playerStatistic.numberOfTeamMatches}</dd>

					<dt>Cups</dt>
					<dd>${playerStatistic.numberOfPlayerCups}/${playerStatistic.numberOfTeamCups}</dd>
				</dl>


				<div class="page-header">
					<h5>Parents</h5>
				</div>

				<ul class="list-group">
					<c:forEach var="item" items="${player.parentItemList}" varStatus="loop">
						<li class="list-group-item"><a href="/contact/${item.id}">${item.value}</a></li>
						<td></td>
					</c:forEach>
				</ul>
			</div>

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listplayers/{teamId}" var="editUrl">
						<spring:param name="teamId" value="${player.fkTeamId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-sm">Back</a>
				</div>
			</div>
		</div>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>