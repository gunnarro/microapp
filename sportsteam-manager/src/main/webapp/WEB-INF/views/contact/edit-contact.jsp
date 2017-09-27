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
			<c:when test="${contact['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
		
			<div class="panel-heading clearfix">
				<c:if test="${not empty contact.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/contact/delete/{contactId}" var="deleteUrl">
								<spring:param name="contactId" value="${contact.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${contact['new']}">New </c:if>
					Contact
				</h1>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="contact" method="${method}" id="contact-form">

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<!-- 
					<c:choose>
						<c:when test="${contact['new']}">
							<div class="form-group">
								<form:label path="fkTeamId">Team</form:label>
								<form:select cssClass="form-control" path="fkTeamId">
									<form:options items="${teamList}" itemValue="id" itemLabel="name" />
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label for="team" class="control-label">Team</label>
								<div>
									<p class="form-control-static">${contact.team.name}</p>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					 -->

					<form:hidden path="fkTeamId" value="${contact.fkTeamId}" />
					<form:hidden path="fkAddressId" value="${contact.fkAddressId}" />

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
							<c:when test="${contact['new']}">
								<form:input cssClass="form-control" path="firstName" value="${contact.firstName}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="firstName" value="${contact.firstName}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="middleName">Middle name</form:label>
						<c:choose>
							<c:when test="${contact['new']}">
								<form:input cssClass="form-control" path="middleName" value="${contact.middleName}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="middleName" value="${contact.middleName}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="lastName">Last name</form:label>
						<c:choose>
							<c:when test="${contact['new']}">
								<form:input cssClass="form-control" path="lastName" value="${contact.lastName}" />
							</c:when>
							<c:otherwise>
								<form:input cssClass="form-control" path="lastName" value="${contact.lastName}" disabled="true" />
							</c:otherwise>
						</c:choose>
					</div>

					<div class="form-group">
						<form:label path="teamRolesStr">Roles</form:label>
						<form:select cssClass="form-control" path="teamRolesStr" multiple="true">
							<form:options items="${teamRoleOptions}" />
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="gender">Gender</form:label>
						<form:select cssClass="form-control" path="gender">
							<form:options items="${genderOptions}" />
						</form:select>
					</div>

					<div class="form-group">
						<form:label path="mobileNumber">Mobilenumber</form:label>
						<form:input cssClass="form-control" path="mobileNumber" value="${contact.mobileNumber}" />
					</div>

					<div class="form-group">
						<form:label path="emailAddress">Emailaddress</form:label>
						<form:input cssClass="form-control" path="emailAddress" value="${contact.emailAddress}" />
					</div>

					<div class="form-group">
						<form:label path="contact.address.streetName">Streetname</form:label>
						<form:input cssClass="form-control" path="contact.address.streetName" value="${contact.address.streetName}" />
					</div>
					
					<div class="form-group">
						<form:label path="contact.address.streetNumber">Street number</form:label>
						<form:input cssClass="form-control" path="contact.address.streetNumber" value="${contact.address.streetNumber}" />
					</div>
					
					<div class="form-group">
						<form:label path="contact.address.streetNumberPostfix">Street Number Postfix</form:label>
						<form:input cssClass="form-control" path="contact.address.streetNumberPostfix" value="${contact.address.streetNumberPostfix}" />
					</div>
					
					<div class="form-group">
						<form:label path="contact.address.postCode">Post Code</form:label>
						<form:input cssClass="form-control" path="address.postCode" value="${contact.address.postCode}" />
					</div>
					
					<div class="form-group">
						<form:label path="contact.address.city">City</form:label>
						<form:input cssClass="form-control" path="contact.address.city" value="${contact.address.city}" />
					</div>
					
					<div class="form-group">
						<form:label path="contact.address.country">Country</form:label>
						<form:input cssClass="form-control" path="contact.address.country" value="${contact.address.country}" />
					</div>

					<c:choose>
						<c:when test="${contact['new']}">
							<button type="submit" class="btn btn-primary">Add contact</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update contact</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panle body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listcontacts/{teamId}" var="editUrl">
						<spring:param name="teamId" value="${contact.fkTeamId}" />
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