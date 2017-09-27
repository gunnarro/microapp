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
					<spring:url value="/league/edit/{leagueId}" var="editUrl">
						<spring:param name="leagueId" value="${league.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>
					<spring:url value="/league/table/{leagueId}/current" var="editUrl">
						<spring:param name="leagueId" value="${league.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">table</a>
				</div>
				<h1 class="panel-title">${league.name}</h1>
			</div>

			<div class="panel-body center-block">
				<dl class="dl-horizontal">
					<dt>Status</dt>
					<dd>${league.status.name}</dd>

					<dt>League type</dt>
					<dd>${league.leagueCategory.sportType}</dd>					

					<dt>League category</dt>
					<dd>${league.leagueCategory.name}</dd>
					
					<dt>League</dt>
					<dd>${league.name}</dd>

					<dt>Gender</dt>
					<dd>${league.leagueCategory.leagueRule.gender}</dd>

					<dt>Description</dt>
					<dd>${league.leagueCategory.leagueRule.description}</dd>

					<dt>Age</dt>
					<dd>${league.leagueCategory.leagueRule.playerAgeMin} to ${league.leagueCategory.leagueRule.playerAgeMax}</dd>

					<dt>Number of Players</dt>
					<dd>${league.leagueCategory.leagueRule.numberOfPlayers}</dd>
					
					<dt>Match Period</dt>
					<dd>${league.leagueCategory.leagueRule.matchPeriodTimeMinutes} minutes</dd>
					
					<dt>Match Extra Period</dt>
					<dd>${league.leagueCategory.leagueRule.matchExtraPeriodTimeMinutes} minutes</dd>
				</dl>
			</div>

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listleagues" var="editUrl">
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-sm">Back</a>
				</div>
			</div>
		</div>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>
