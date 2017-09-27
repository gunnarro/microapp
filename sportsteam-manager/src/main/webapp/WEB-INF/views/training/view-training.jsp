<!DOCTYPE html>
<html lang="en">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="../public/head-tag.jsp" />

<body>

	<jsp:include page="../public/body-header.jsp" />

	<div class="container">

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">${training.team.name}</h1>
			</div>

			<div class="panel-body">
				<dl class="dl-horizontal">
					<dt>Start date</dt>
					<dd>
						<fmt:formatDate value="${training.startDate}" type="both" pattern="dd.MM.yyyy" />
					</dd>

					<dt>Start time</dt>
					<dd>
						<span class="text-info"><fmt:formatDate value="${training.startDate}" type="time" pattern="HH:mm" /></span> 
						<span class="text-info"> - </span> 
						<span class="text-info"><fmt:formatDate value="${training.endDate}" type="time" pattern="HH:mm" /></span>
					</dd>

					<dt>Training</dt>
					<dd>${training.type}</dd>

					<dt>Place</dt>
					<dd>
						<a href="https://maps.google.com/?q=${training.venue}"><i class="glyphicon glyphicon-map-marker"></i> ${training.venue}</a><br>
					</dd>
				</dl>

				<jsp:include page="../activity/activity-registrer-players.jsp">
					<jsp:param name="activityId" value="${training.id}" />
					<jsp:param name="numberOfRegistreredPlayers" value="${training.numberOfSignedPlayers}" />
					<jsp:param name="numberOfPlayers" value="14" />
					<jsp:param name="finished" value="${training.finished}" />
				</jsp:include>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listactivities/{teamId}/{season}" var="backUrl">
						<spring:param name="teamId" value="${training.fkTeamId}" />
						<spring:param name="season" value="${training.season.id}" />
					</spring:url>
					<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm">back</a>
				</div>
			</div>
		</div>
		<!-- end panel -->
		<jsp:include page="../public/footer.jsp" />
	</div>
	<!-- end container -->
</body>

</html>