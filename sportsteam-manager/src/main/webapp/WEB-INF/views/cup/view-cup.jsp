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
				<h1 class="panel-title">${cup.cupName}</h1>
			</div>

			<div class="panel-body">

				<dl class="dl-horizontal">
					<dt>Start date</dt>
					<dd>
						<fmt:formatDate value="${cup.startDate}" type="both" pattern="dd.MM.yyyy HH:mm" />
					</dd>

					<dt>End date</dt>
					<dd>
						<fmt:formatDate value="${cup.endDate}" type="both" pattern="dd.MM.yyyy HH:mm" />
					</dd>

					<dt>Deadline date</dt>
					<dd>
						<fmt:formatDate value="${cup.deadlineDate}" type="both" pattern="dd.MM.yyyy" />
					</dd>

					<dt>Name</dt>
					<dd>${cup.cupName}</dd>

					<dt>Organizing Club</dt>
					<dd>${cup.clubName}</dd>

					<dt>Place</dt>
					<dd>
						<a href="https://maps.google.com/?q=${cup.venue}"><i class="glyphicon glyphicon-map-marker"></i> ${cup.venue}</a><br>
					</dd>

					<dt>Contact person</dt>
					<dd>${cup.email}</dd>

					<dt>HomePage</dt>
					<dd>
						<a href="<c:out value="${cup.homePage}"/>">${cup.homePage}</a>
					</dd>
				</dl>

				<jsp:include page="cup-registrer-teams.jsp">
					<jsp:param name="cupId" value="${cup.id}" />
					<jsp:param name="numberOfRegistreredTeams" value="${cup.numberOfRegistreredTeams}" />
					<jsp:param name="numberOfTeams" value="10" />
					<jsp:param name="finished" value="${cup.finished}" />
				</jsp:include>

			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listcups/current" var="backUrl">
					</spring:url>
					<a href="${fn:escapeXml(backUrl)}" class="btn btn-default btn-sm">back</a>
				</div>
			</div>
		</div>
		<!-- end panel -->
		<jsp:include page="../public/footer.jsp" />
	</div>
	<!-- end container -->
</body>

</html>