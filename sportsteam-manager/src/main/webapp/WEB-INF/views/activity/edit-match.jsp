<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container">

		<c:choose>
			<c:when test="${match['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">
					<c:if test="${match['new']}">New </c:if>
					match
					<c:out value="${match.season.period}" />
				</h1>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="match" method="${method}" id="match-form">
					<form:hidden path="id" value="${match.id}" />
					<form:hidden path="fkSeasonId" value="${match.fkSeasonId}" />
					<form:hidden path="fkTeamId" value="${match.fkTeamId}" />

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<spring:bind path="startDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="startDate">Start Date</form:label>
							<fmt:formatDate var="formattedStartDate"
								value="${match.startDate}" pattern="dd.MM.yyyy HH:mm" />
							<form:input cssClass="form-control" path="startDate"
								value="${formattedStartDate}" placeholder="dd.MM.yyyy HH:mm" />
							<form:errors path="startDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<form:label path="matchStatus.id">Status</form:label>
					<form:select cssClass="form-control" path="matchStatus.id">
						<form:options items="${matchStatusTypes}" itemValue="id" itemLabel="name" />
					</form:select>

					<c:choose>
						<c:when test="${match['new']}">
							<div class="form-group">
								<form:label path="fkTeamId">Team</form:label>
								<form:select cssClass="form-control" path="fkTeamId">
									<form:options items="${teamList}" itemValue="id" itemLabel="name" />
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<c:if test="${match.team != null}">
								<div class="form-group">
									<label>Team</label>
									<p class="form-control-static">${match.team.name}</p>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>

					<div class="form-group">
						<label>League</label>
						<p class="form-control-static">${match.league.name}</p>
					</div>

					<div class="form-group">
						<form:label path="homeTeam.name">Home Team</form:label>
						<form:input cssClass="form-control" path="homeTeam.name" value="${match.homeTeam.name}" />
						<form:label path="numberOfGoalsHome">Goals</form:label>
						<form:select cssClass="form-control" path="numberOfGoalsHome">
							<form:option value="0">0</form:option>
							<form:option value="1">1</form:option>
							<form:option value="2">2</form:option>
							<form:option value="3">3</form:option>
							<form:option value="4">4</form:option>
							<form:option value="5">5</form:option>
							<form:option value="6">6</form:option>
							<form:option value="7">7</form:option>
							<form:option value="8">8</form:option>
							<form:option value="9">9</form:option>
							<form:option value="10">10</form:option>
							<form:option value="11">11</form:option>
							<form:option value="12">12</form:option>
							<form:option value="13">13</form:option>
							<form:option value="14">14</form:option>
							<form:option value="15">15</form:option>
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="awayTeam.name">Away Team</form:label>
						<form:input cssClass="form-control" path="awayTeam.name" value="${match.awayTeam.name}" />
						<form:label path="numberOfGoalsAway">Goals</form:label>
						<form:select cssClass="form-control" path="numberOfGoalsAway">
							<form:option value="0">0</form:option>
							<form:option value="1">1</form:option>
							<form:option value="2">2</form:option>
							<form:option value="3">3</form:option>
							<form:option value="4">4</form:option>
							<form:option value="5">5</form:option>
							<form:option value="6">6</form:option>
							<form:option value="7">7</form:option>
							<form:option value="8">8</form:option>
							<form:option value="9">9</form:option>
							<form:option value="10">10</form:option>
							<form:option value="11">11</form:option>
							<form:option value="12">12</form:option>
							<form:option value="13">13</form:option>
							<form:option value="14">14</form:option>
							<form:option value="15">15</form:option>
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="venue">Place</form:label>
						<form:input cssClass="form-control" path="venue"
							value="${match.venue}" />
					</div>

					<form:label path="referee">Referee</form:label>
					<form:select cssClass="form-control" path="fkRefereeId">
						<form:options items="${refereeList}" itemValue="id"
							itemLabel="name" />
					</form:select>

					<div class="form-group">
						<form:label path="information">Information</form:label>
						<form:input cssClass="form-control" path="information" value="${match.information}" />
					</div>
					
					<p />

					<c:choose>
						<c:when test="${match['new']}">
							<button type="submit" class="btn btn-primary">Add match</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update match</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listactivities/{teamId}/{seasonId}"
						var="editUrl">
						<spring:param name="teamId" value="${match.fkTeamId}" />
						<spring:param name="seasonId" value="${match.fkSeasonId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">Cancel</a>
					<spring:url value="/listactivities/{teamId}/{seasonId}"
						var="editUrl">
						<spring:param name="teamId" value="${match.fkTeamId}" />
						<spring:param name="seasonId" value="${match.fkSeasonId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">Back</a>
				</div>
			</div>

			<!-- end panel footer -->
		</div>
		<!-- end panel -->
	</div>
	<!-- end container -->

	<jsp:include page="../public/footer.jsp" />
</body>
</html>