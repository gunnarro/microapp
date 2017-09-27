<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>

<!-- 
	<script type="text/javascript">
	 $(function () {
         $('#startDateDatePicker').datetimepicker();
     });

	 $(function () {
         $('#endDateDatePicker').datetimepicker();
     });
	</script>
 -->
	<jsp:include page="../public/body-header.jsp" />

	<div class="container">

		<c:choose>
			<c:when test="${training['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<!-- Panel -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">
					<c:if test="${training['new']}">New </c:if>
					Training, season
					<c:out value=" ${training.season.period}" />
				</h1>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="training" method="${method}" id="training-form">
					<form:hidden path="id" value="${training.id}" />
					<form:hidden path="fkSeasonId" value="${training.fkSeasonId}" />

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<spring:bind path="startDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="startDate">Start Date</form:label>
							<fmt:formatDate var="formattedStartDate" value="${training.startDate}" pattern="dd.MM.yyyy" />
							<form:input type="date" min="01.10.2014" max="31.03.2015" cssClass="form-control" path="startDate" value="${formattedStartDate}" placeholder="dd.MM.yyyy"/>
							<form:errors path="startDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<div class="form-group">
						<form:label path="endDate">Start Time</form:label> 
						<form:input type="time" cssClass="form-control" path="startTime" value="${training.startTime}" placeholder="HH:mm" />
					</div>
					
					<div class="form-group">
						<form:label path="endDate">End Time</form:label> 
						<form:input type="time" cssClass="form-control" path="endTime" value="${training.endTime}" placeholder="HH:mm"/>
					</div>

					<c:choose>
						<c:when test="${training['new']}">
							<div class="form-group">
								<form:label path="fkTeamId">Team</form:label>
								<form:select cssClass="form-control" path="fkTeamId">
									<form:options items="${teamList}" itemValue="id" itemLabel="name" />
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<form:hidden path="fkTeamId" value="${training.fkTeamId}" />
							<div class="form-group">
								<label>Team</label>
								<p class="form-control-static">${training.team.name}</p>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="form-group">
						<form:label path="venue">Place</form:label>
						<form:input cssClass="form-control" path="venue" value="${training.venue}" />
					</div>

					<div class="form-group">
						<form:label path="reiterations">Repeat same day and time for number of weeks</form:label>
						<form:select cssClass="form-control" path="reiterations">
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
					
					<c:choose>
						<c:when test="${training['new']}">
							<button type="submit" class="btn btn-primary">Add Training</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update Training</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listactivities/{teamId}/{seasonId}" var="editUrl">
						<spring:param name="teamId" value="${training.fkTeamId}" />
						<spring:param name="seasonId" value="${training.fkSeasonId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">Cancel</a>
					<spring:url value="/listactivities/{teamId}/{seasonId}" var="editUrl">
						<spring:param name="teamId" value="${training.fkTeamId}" />
						<spring:param name="seasonId" value="${training.fkSeasonId}" />
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