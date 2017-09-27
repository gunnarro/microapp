<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/diet-body-header.jsp" />

	<div class="container">
		
		<c:choose>
			<c:when test="${calendarevent['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<!-- Panel -->
		<div class="panel panel-default">
		
			<div class="panel-heading clearfix">
				<c:if test="${not empty calendarevent.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/calendar/event/delete/{eventId}" var="deleteUrl">
								<spring:param name="eventId" value="${calendarevent.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${calendarevent['new']}">New </c:if>
					Event
				</h1>
			</div>

			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="calendarevent" method="${method}" id="calendar-event-form">
					<form:hidden path="id" value="${calendarevent.id}" />

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					<c:choose>
						<c:when test="${calendarevent['new']}">
							<div class="form-group">
								<form:label path="name">Name</form:label>
								<form:input cssClass="form-control" path="name" value="${calendarevent.name}" />
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Name</label>
								<p class="form-control-static">${calendarevent.name}</p>
							</div>
						</c:otherwise>
					</c:choose>
					
					<spring:bind path="startDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="startDate">Start Date</form:label>
							<fmt:formatDate var="formattedStartDate" value="${calendarevent.startDate}" pattern="dd.MM.yyyy" />
							<form:input cssClass="form-control" path="startDate" value="${formattedStartDate}" placeholder="dd.MM.yyyy"/>
							<form:errors path="startDate" cssClass="help-block" />
						</div>
					</spring:bind>

					<div class="form-group">
						<form:label path="endDate">Start Time</form:label> 
						<form:input cssClass="form-control" path="startTime" value="${calendarevent.startTime}" placeholder="HH:mm" />
					</div>
					
					<div class="form-group">
						<form:label path="endDate">End Time</form:label> 
						<form:input cssClass="form-control" path="endTime" value="${calendarevent.endTime}" placeholder="HH:mm"/>
					</div>

					<div class="form-group">
						<form:label path="type">Type</form:label>
						<form:select cssClass="form-control" path="type" >
							<form:option value="training">Training</form:option>
							<form:option value="match">Match</form:option>
							<form:option value="cup">Cup</form:option>
							<form:option value="tournament">Tournament</form:option>
							<form:option value="social">Social</form:option>
							<form:option value="vulunteer">Volunteer</form:option>
						</form:select>
					</div>
					
					<div class="form-group">
						<form:label path="location">Location</form:label>
						<form:input cssClass="form-control" path="location" value="${calendarevent.location}" />
					</div>
					
					<div class="form-group">
						<form:label path="description">Description</form:label>
						<form:input cssClass="form-control" path="description" value="${calendarevent.description}" />
					</div>
					
					<div class="form-group">
						<form:label path="summary">Summary</form:label>
						<form:input cssClass="form-control" path="summary" value="${calendarevent.summary}" />
					</div>
					
					<div class="form-group">
						<form:label path="Link">Link to external page</form:label>
						<form:input cssClass="form-control" path="link" value="${calendarevent.link}" />
					</div>
					
					<c:if test="${calendarevent['new']}">
						<spring:bind path="endDate">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<form:label path="endDate">Repeat event until end date</form:label>
								<fmt:formatDate var="formattedEndDate" value="${calendarevent.endDate}" pattern="dd.MM.yyyy" />
								<form:input cssClass="form-control" path="endDate" value="${formattedEndDate}" placeholder="dd.MM.yyyy"/>
								<form:errors path="endDate" cssClass="help-block" />
							</div>
						</spring:bind>
						
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
					</c:if>
					
					<c:choose>
						<c:when test="${calendarevent['new']}">
							<button type="submit" class="btn btn-primary">Add Event(s)</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update Event</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/calendar/listevents/false/week/0/all/all" var="cancelUrl">
					</spring:url>
					<a href="${fn:escapeXml(cancelUrl)}" class="btn btn-primary btn-sm">Cancel</a>
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