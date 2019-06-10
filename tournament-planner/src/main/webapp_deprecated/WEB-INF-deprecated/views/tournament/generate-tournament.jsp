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
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<h1 class="panel-title">
				Generate Tournament
				</h1>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="tournament" method="POST" id="tournament-generate-form">
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<div class="form-group">
						<form:label path="name">Tournament name</form:label>
						<form:input cssClass="form-control" path="name"
							value="${tournament.name}" />
					</div>

					<div class="form-group">
						<form:label path="type">Tournament type</form:label>
						<form:select cssClass="form-control" path="type">
							<form:options items="${tournamentTypeOptions}" itemValue="value" itemLabel="type" />
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="numberOfGroups">Number of groups</form:label>
						<form:input cssClass="form-control" path="numberOfGroups" value="${tournament.numberOfGroups}" />
					</div>

					<div class="form-group">
						<form:label path="numberOfFields">Number of fields</form:label>
						<form:input cssClass="form-control" path="numberOfFields" value="${tournament.numberOfFields}" />
					</div>
					
					<div class="form-group">
						<form:label path="playTime">Play time (minutes)</form:label>
						<form:input cssClass="form-control" path="playTime" value="${tournament.playTime}" />
					</div>
					
					<div class="form-group">
						<form:label path="pauseTimeBetweenMatches">Pause time between matches (minutes)</form:label>
						<form:input cssClass="form-control" path="pauseTimeBetweenMatches" value="${tournament.pauseTimeBetweenMatches}" />
					</div>
					
					<div class="form-group">
						<form:label path="teams">Team names</form:label>
						<form:textarea class="form-control" rows="5" id="teams" path="teams" ></form:textarea>
					</div>

					<button type="submit" class="btn btn-primary">Generate Tournament</button>
				</form:form>
			</div>
			<!-- end panle body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listtournaments/token_x" var="listUrl">
					</spring:url>
					<a href="${fn:escapeXml(listUrl)}" class="btn btn-default btn-sm">Cancel</a>
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