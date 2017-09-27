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
			<h4>League</h4>
			<h5>Bandy</h5>
		</div>

		<datatables:table id="league" data="${leagueList}" row="league"
			theme="bootstrap3" cssClass="table table-striped" pageable="false"
			info="false">
			<datatables:column title="#">
				<c:choose>
					<c:when test="${league.leagueCategory.leagueRule.male}">
						<span class="glyphicon glyphicon-user male-color"></span>
					</c:when>
					<c:otherwise>
						<span class="glyphicon glyphicon-user female-color"></span>
					</c:otherwise>
				</c:choose>
			</datatables:column>

			<datatables:column title="Type">
				${league.leagueCategory.sportType}
			</datatables:column>

			<datatables:column title="Name">
				<spring:url value="/league/{leagueId}" var="viewUrl">
					<spring:param name="leagueId" value="${league.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}">${league.name}</a>
			</datatables:column>

			<datatables:column title="Age">
				${league.leagueCategory.leagueRule.playerAgeMin} to ${league.leagueCategory.leagueRule.playerAgeMax}
			</datatables:column>

			<datatables:column title="#">
				<spring:url value="/league/edit/{leagueId}" var="editUrl">
					<spring:param name="leagueId" value="${league.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

				<spring:url value="/league/delete/{leagueId}" var="deleteUrl">
					<spring:param name="leagueId" value="${league.id}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

				<spring:url value="/league/table/{leagueId}/{season}" var="viewUrl">
					<spring:param name="leagueId" value="${league.id}" />
					<spring:param name="season" value="current" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">Table</a>

			</datatables:column>
		</datatables:table>

		<spring:url value="/league/new" var="addUrl">
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New
			League</a>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>