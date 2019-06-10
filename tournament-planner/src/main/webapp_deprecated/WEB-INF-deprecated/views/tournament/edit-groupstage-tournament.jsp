<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />
<style type="text/css">
.common-cell {
	text-align: left;
}

.arrow-cell {
	border-bottom: solid;
}

.empty-cell {
	
}

.goals-cell {
	background-color: #eeeeee;
}

.quarterfinal1 {
	background-color: #be4dff;
}

.quarterfinal2 {
	background-color: #e5b8ff;
}

.quarterfinal3 {
	background-color: #4d62ff;
}

.quarterfinal4 {
	background-color: #b8c0ff;
}

.semifinal1 {
	
}

.semifinal2 {
	
}

.bronsefinal {
	background-color: #01fd62;
}

.goldfinal {
	background-color: #02a942;
}

.table-condensed>thead>tr>th, .table-condensed>tbody>tr>th,
	.table-condensed>tfoot>tr>th, .table-condensed>thead>tr>td,
	.table-condensed>tbody>tr>td, .table-condensed>tfoot>tr>td {
	padding: 3px;
}
</style>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<h1 class="panel-title">Group Stage ${group.tournamentId} - ${group.name}
					${group.name}</h1>
			</div>

			<div class="panel-body center-block">
				<form:form modelAttribute="group" method="POST"
					id="tournament-groupstage-form">
					<table class="table table-condensed">
						<thead>
							<tr class="info">
								<th colspan="4" class="text-right">
									<button type="submit" class="btn btn-primary btn-xs">Save</button>
								</th>
							</tr>
							<tr class="info">
								<th colspan="1" class="col-md-1 text-left">Start</th>
								<th colspan="1" class="col-md-2 text-left">Match</th>
								<th colspan="1" class="col-md-1 text-left">Result</th>
								<th colspan="1" class="col-md-1 text-left">Venue</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="match" items="${group.matches}">
								<tr>
									<td><fmt:formatDate value="${match.startDate}" pattern="HH:mm" /></td>
									<td>${match.teamVersus}</td>
									<td><input type="text" value="${match.numberOfGoalsHome}" size="2" /> - <input type="text" value="${match.numberOfGoalsAway}" size="2" /></td>
									<td>${match.venue}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="3"></td>
								<td><button type="submit" class="btn btn-primary">Save</button></td>
							</tr>
						</tfoot>
					</table>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/tournament/{tournamentId}" var="backUrl">
						<spring:param name="tournamentId" value="${group.tournamentId}"/>
					</spring:url>
					<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm">Cancel</a>
				</div>
			</div>
		</div>
		<!-- end panel -->
		<jsp:include page="../public/footer.jsp" />
	</div>
	<!-- end container -->
</body>

</html>