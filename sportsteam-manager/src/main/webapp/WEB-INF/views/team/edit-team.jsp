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
			<c:when test="${team['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty team.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/team/delete/{teamId}" var="deleteUrl">
								<spring:param name="teamId" value="${team.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${team['new']}">New </c:if>
					Team
				</h1>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="team" method="${method}" id="team-form">
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					<c:choose>
						<c:when test="${team['new']}">
							<div class="form-group">
								<form:label path="fkClubId">Club</form:label>
								<form:select cssClass="form-control" path="fkClubId">
									<form:options items="${clubList}" itemValue="id" itemLabel="fullName" />
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Club</label>
								<p class="form-control-static">${team.club.fullName}</p>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="form-group">
						<form:label path="gender">Gender</form:label>
						<form:select cssClass="form-control" path="gender">
							<form:options items="${genderOptions}" />
						</form:select>
					</div>

					<div class="btn-group btn-group-sm">
						<button type="button" class="btn btn-default">Male</button>
						<button type="button" class="btn btn-default">Female</button>
					</div>

					<div class="form-group">
						<form:label path="name">Team name</form:label>
						<form:input cssClass="form-control" path="name" value="${team.name}" />
					</div>

					<div class="form-group">
						<form:label path="fkLeagueId">League</form:label>
						<form:select cssClass="form-control" path="fkLeagueId">
							<form:options items="${leagueOptions}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="fkTeamleadId">Teamlead</form:label>
						<form:select cssClass="form-control" path="fkTeamleadId">
							<form:option value="" />
							<form:options items="${contactList}" itemValue="id" itemLabel="fullName" />
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="fkCoachId">Coach</form:label>
						<form:select cssClass="form-control" path="fkCoachId">
							<form:option value="" />
							<form:options items="${contactList}" itemValue="id" itemLabel="fullName" />
						</form:select>
					</div>

					<div class="pull-left">
						<c:choose>
							<c:when test="${team['new']}">
								<button type="submit" class="btn btn-primary">New Team</button>
							</c:when>
							<c:otherwise>
								<button type="submit" class="btn btn-primary">Update Team</button>
							</c:otherwise>
						</c:choose>
					</div>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listteams/{clubId}" var="editUrl">
						<spring:param name="clubId" value="${team.fkClubId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-sm">Cancel</a>
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