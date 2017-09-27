<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>


<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">

		<div class="page-header">
			<h4>${league.leagueCategory.sportType}</h4>
			<h5>
				<spring:url value="/league/{leagueId}" var="leaguesViewUrl">
					<spring:param name="leagueId" value="${league.id}" />
				</spring:url>
				<a href="${fn:escapeXml(leaguesViewUrl)}">${league.name}</a>
				
				<spring:url value="/listmatches/league/{leagueId}/{seasonId}" var="matchesViewUrl">
					<spring:param name="leagueId" value="${league.id}" />
					<spring:param name="seasonId" value="current" />
				</spring:url>
				<a href="${fn:escapeXml(matchesViewUrl)}">matches</a>
			</h5>
		</div>

		<datatables:table id="stat" data="${leagueStatistics}" row="stat"
			theme="bootstrap3" cssClass="table table-striped" pageable="false" 
			info="false">
			
			<datatables:column title="#">
				${stat.position}
			</datatables:column>
			
			<datatables:column title="Team">
				${stat.teamName}
			</datatables:column>

			<datatables:column title="M">
				${stat.numberOfPlayedMatches}
			</datatables:column>

			<datatables:column title="W">
				${stat.won}
			</datatables:column>

			<datatables:column title="D">
				${stat.draw}
			</datatables:column>

			<datatables:column title="L">
				${stat.loss}
			</datatables:column>
			
			<datatables:column title="G">
				${stat.goalsScored} - ${stat.goalsAgainst} 
			</datatables:column>
			
			<datatables:column title="P">
				${stat.scores}
			</datatables:column>
			
		</datatables:table>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>