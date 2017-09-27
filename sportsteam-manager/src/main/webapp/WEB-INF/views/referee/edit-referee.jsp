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
			<c:when test="${referee['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty referee.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/referee/delete/{refereeId}" var="deleteUrl">
								<spring:param name="refereeId" value="${referee.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${referee['new']}">New </c:if>
					Referee
				</h1>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="referee" method="${method}" id="referee-form">
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					<c:choose>
						<c:when test="${referee['new']}">
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
								<p class="form-control-static">${referee.club.fullName}</p>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="form-group">
						<form:label path="firstName">First name</form:label>
						<c:choose>
							<c:when test="${referee['new']}">
								<form:input cssClass="form-control" path="firstName" value="${referee.firstName}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="firstName" value="${referee.firstName}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="middleName">Middle name</form:label>
						<c:choose>
							<c:when test="${referee['new']}">
								<form:input cssClass="form-control" path="middleName" value="${referee.middleName}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="middleName" value="${referee.middleName}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="lastName">Last name</form:label>
						<c:choose>
							<c:when test="${referee['new']}">
								<form:input cssClass="form-control" path="lastName" value="${referee.lastName}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="lastName" value="${referee.lastName}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="gender">Gender</form:label>
						<form:input cssClass="form-control" path="gender" value="${referee.gender}" />
					</div>

					<div class="form-group">
						<form:label path="mobileNumber">Mobilenumber</form:label>
						<form:input cssClass="form-control" path="mobileNumber" value="${referee.mobileNumber}" />
					</div>

					<div class="form-group">
						<form:label path="emailAddress">Emailaddress</form:label>
						<form:input cssClass="form-control" path="emailAddress" value="${referee.emailAddress}" />
					</div>
					
					<jsp:include page="../common/address-input.jsp" >
						<jsp:param name="address" value="${referee.address}" />
					</jsp:include>
					
					<c:choose>
						<c:when test="${referee['new']}">
							<button type="submit" class="btn btn-primary">Add Referee</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update Referee</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listreferees/{clubId}" var="editUrl">
						<spring:param name="clubId" value="${referee.fkClubId}" />
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