<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>

	<jsp:include page="../public/body-header.jsp" />

	<div class="container">

		<div class="page-header">
			<h4>SportTeams</h4>
			<h5>Seasons</h5>
		</div>

		<datatables:table id="season" data="${seasonList}" row="season" theme="bootstrap3" cssClass="table table-striped" pageable="false" info="false">
			<datatables:column title="Period">
				${season.period}
			</datatables:column>
			
			<datatables:column title="Start date">
				<fmt:formatDate value="${season.startTimeDate}" pattern="dd.MM.yy" />
			</datatables:column>
			
			<datatables:column title="End date">
				<fmt:formatDate value="${season.endTimeDate}" pattern="dd.MM.yy" />
			</datatables:column>
			
			<datatables:column title="#">
				<spring:url value="/season/delete/{seasonId}" var="deleteUrl">
					<spring:param name="seasonId" value="${season.id}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
			
				<spring:url value="listmatches/league/{leagueId}/{season}" var="viewUrl">
				    <spring:param name="leagueId" value="1" />
					<spring:param name="season" value="${season.period}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">matches <span class="badge"><c:out value="x" /></span></a>
				
				<spring:url value="listcups/{seasonId}" var="viewUrl">
					<spring:param name="seasonId" value="${season.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">cups <span class="badge"><c:out value="x" /></span></a>
			
			</datatables:column>
		</datatables:table>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>