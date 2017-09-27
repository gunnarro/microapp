<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../../../public/head-tag.jsp" />

<body>
	<jsp:include page="../../../public/body-header.jsp" />

	<div class="container">

		<c:choose>
			<c:when test="${user['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">
					<c:if test="${user['new']}">New </c:if>
					User
				</h1>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="user" method="${method}" id="edit-user-form">
					<form:hidden path="id" value="${user.id}" />

					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<spring:bind path="userName">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="userName">User name</form:label>
							<form:input  cssClass="form-control" path="userName" value="${user.userName}" />
							<form:errors path="userName" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="password">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="password">Password</form:label>
							<form:password cssClass="form-control" path="password" value="${user.password}" />
							<form:errors path="password" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="passwordRepeat">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="password">PasswordRepeat</form:label>
							<form:password cssClass="form-control" path="passwordRepeat" value="${user.passwordRepeat}" />
							<form:errors path="passwordRepeat" cssClass="help-block" />
						</div>
					</spring:bind>

					<spring:bind path="email">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="email">Email</form:label>
							<form:password cssClass="form-control" path="email" value="${user.email}" />
							<form:errors path="email" cssClass="help-block" />
						</div>
					</spring:bind>

					<div class="form-group">
						<form:label path="roles">Role</form:label>
						<form:select cssClass="form-control" path="roles">
							<form:options items="${userRoleOptions}" />
						</form:select>
					</div>
										
					<spring:bind path="activated">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:label path="activated">Activated</form:label>
							<form:input cssClass="form-control" path="activated" value="${user.activated}" />
							<form:errors path="activated" cssClass="help-block" />
						</div>
					</spring:bind>
					
					<c:choose>
						<c:when test="${user['new']}">
							<button type="submit" class="btn btn-primary">Add user</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Update user</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<spring:url value="/admin/listuseraccounts" var="editUrl">
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">Cancel</a>
			</div>
		</div>
		<!-- end panel footer -->
	</div>
	<!-- end container -->

	<jsp:include page="../../../public/footer.jsp" />
</body>
</html>