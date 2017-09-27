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

		<c:choose>
			<c:when test="${player['new']}">
				<c:set var="method" value="POST" />
				<c:set var="isInputFieldDisabled" value="false" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
				<c:set var="isInputFieldDisabled" value="true" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">

			<div class="panel-heading clearfix">
				<c:if test="${not empty player.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/player/delete/{playerId}" var="deleteUrl">
								<spring:param name="playerId" value="${player.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${player['new']}">New </c:if>
					Player
				</h1>
			</div>

			<div class="panel-heading">
				<h1 class="panel-title">
					<c:if test="${player['new']}">New </c:if>
					Player
				</h1>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="player" method="${method}" id="player-form">

					<!-- only for debug -->
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>

					<!-- 
					<c:choose>
						<c:when test="${player['new']}">
							<div class="form-group">
								<form:label path="fkTeamId">Team</form:label>
								<form:select cssClass="form-control" path="fkTeamId">
									<form:options items="${teamList}" itemValue="id" itemLabel="name" />
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label for="team" class="control-label col-xs-2">Team</label>
								<div class="col-xs-10">
									<p class="form-control-static">${player.team.name}</p>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					 -->
					<form:hidden path="fkTeamId" value="${team.id}" />

					<div class="form-group">
						<label>Team</label>
						<p class="form-control-static">${team.name}</p>
					</div>

					<div class="form-group">
						<form:label path="fkStatusId">Status</form:label>
						<form:select cssClass="form-control" path="fkStatusId">
							<form:options items="${statusOptions}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="firstName">First name</form:label>
						<c:choose>
							<c:when test="${player['new']}">
								<form:input cssClass="form-control" path="firstName" value="${player.firstName}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="firstName" value="${player.firstName}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="middleName">Middle name</form:label>
						<c:choose>
							<c:when test="${player['new']}">
								<form:input cssClass="form-control" path="middleName" value="${player.middleName}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="middleName" value="${player.middleName}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="lastName">Last name</form:label>
						<c:choose>
							<c:when test="${player['new']}">
								<form:input cssClass="form-control" path="lastName" value="${player.lastName}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="lastName" value="${player.lastName}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="dateOfBirth">Date of birth</form:label>
						<fmt:formatDate var="formattedDateOfBirth" value="${player.dateOfBirth}" pattern="dd.MM.yyyy" />
						<c:choose>
							<c:when test="${player['new']}">
								<form:input cssClass="form-control" path="dateOfBirth" value="${formattedDateOfBirth}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="dateOfBirth" value="${formattedDateOfBirth}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="gender">Gender</form:label>
						<form:select cssClass="form-control" path="gender">
							<form:options items="${genderOptions}" />
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="mobileNumber">Mobilenumber</form:label>
						<form:input cssClass="form-control" path="mobileNumber" value="${player.mobileNumber}" />
					</div>

					<div class="form-group">
						<form:label path="emailAddress">Emailaddress</form:label>
						<form:input cssClass="form-control" path="emailAddress" value="${player.emailAddress}" />
					</div>

					<div class="form-group">
						<form:label path="schoolName">School Name</form:label>
						<form:input cssClass="form-control" path="schoolName" value="${player.schoolName}" />
					</div>

					<div class="form-group">
						<form:label path="fkPlayerPositionId">Position</form:label>
						<form:select cssClass="form-control" path="fkPlayerPositionId">
							<form:options items="${playerPositionOptions}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>
					
					<div class="form-group">
						<form:label path="jerseyNumber">Jersey Number</form:label>
						<form:input cssClass="form-control" path="jerseyNumber" value="${player.jerseyNumber}" />
					</div>

					<c:choose>
						<c:when test="${player['new']}">
							<button type="submit" class="btn btn-primary">Add player</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update player</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listplayers/{teamId}" var="editUrl">
						<spring:param name="teamId" value="${player.fkTeamId}" />
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