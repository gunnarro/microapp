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
				<h1 class="panel-title">${teamName}</h1>
			</div>

			<div class="panel-body">

				<dl class="dl-horizontal">
					<dt>Date</dt>
					<dd>
						<fmt:formatDate value="${cup.startDate}" type="both" pattern="dd.MM.yyyy HH:mm" />
					</dd>

					<dt>Deadline date</dt>
					<dd>
						<fmt:formatDate value="${cup.deadlineDate}" type="both" pattern="dd.MM.yyyy" />
					</dd>

					<dt>Name</dt>
					<dd>
						<spring:url value="/cup/{cupId}" var="viewUrl">
							<spring:param name="cupId" value="${cup.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}" class="btn btn-link btn-xs">${cup.cupName}</a>
					</dd>

					<dt>Organizing Club</dt>
					<dd>${cup.clubName}</dd>

					<dt>Place</dt>
					<dd>${cup.venue}</dd>
				</dl>

				<jsp:include page="../activity/activity-registrer-players.jsp">
					<jsp:param name="activityId" value="${cup.id}" />
					<jsp:param name="numberOfRegistreredPlayers" value="${cup.numberOfSignedPlayers}" />
					<jsp:param name="numberOfPlayers" value="14" />
					<jsp:param name="finished" value="${cup.finished}" />
				</jsp:include>

			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listactivities/{teamId}/{season}" var="backUrl">
						<spring:param name="teamId" value="${cup.fkTeamId}" />
						<spring:param name="season" value="${cup.season.id}" />
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