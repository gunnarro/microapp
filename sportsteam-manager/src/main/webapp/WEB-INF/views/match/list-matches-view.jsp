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

		<div class="page-header">
			<h4>Matches</h4>
			<ol class="breadcrumb">
				<li class="active">${selectedLeagueName}</li>
				<li class="active">${season.period}</li>
				<li class="active">${selectedfilterBy}</li>
				<li class="active">${selectedFromDate}</li>
				<li class="active">${selectedToDate}</li>
			</ol>
		</div>

		<div class="panel-body">

			<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle"
					data-toggle="dropdown" aria-expanded="false">
					${selectedLeagueName} <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<c:forEach var="type" items="${leagueTypes}">
						<li><a href="/listmatches/league/${type.id}/current">${type.name}
								<span class="badge alert-info">${type.description}</span>
						</a></li>
					</c:forEach>
					<li class="divider"></li>
					<li><a href="/listmatches/league/0/${season.period}">All</a>
				</ul>
			</div>

			<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle"
					data-toggle="dropdown" aria-expanded="false">
					${selectedfilterBy} <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li><a href="<spring:url value="/listmatches/search/${selectedLeagueId}/${season.period}/0/todays/none/none" htmlEscape="true" />">todays</a></li>
					<li><a href="<spring:url value="/listmatches/search/${selectedLeagueId}/${season.period}/0/upcoming/none/none" htmlEscape="true" />">upcoming</a></li>
					<li><a href="<spring:url value="/listmatches/search/${selectedLeagueId}/${season.period}/0/postponed/none/none" htmlEscape="true" />">postponed</a></li>
					<li><a href="<spring:url value="/listmatches/search/${selectedLeagueId}/${season.period}/0/cancelled/none/none" htmlEscape="true" />">cancelled</a></li>
					<li><a href="<spring:url value="/listmatches/search/${selectedLeagueId}/${season.period}/0/played/none/none" htmlEscape="true" />">played</a></li>
				</ul>
			</div>
			
			<div class="btn-group">
				<spring:url value="/league/table/{leagueId}/{season}" var="viewLeagueTableUrl">
					<spring:param name="leagueId" value="${selectedLeagueId}" />
					<spring:param name="season" value="${season.period}" />
				</spring:url>
				<a href="${fn:escapeXml(viewLeagueTableUrl)}" class="btn btn-primary">table</a>
			</div>
			<p />

			<jsp:include page="list-matches-plain2.jsp">
				<jsp:param name="isTeamView" value="false" />
			</jsp:include>

			<spring:url value="/match/new" var="addUrl">
			</spring:url>
			<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New
				Match</a>
		</div>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>