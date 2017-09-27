<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../../../public/head-tag.jsp" />

<body>
	<jsp:include page="../../../public/body-header.jsp" />
	<div class="container">

		<div class="page-header">
			<h4>Users</h4>
		</div>

		<datatables:table id="user" data="${userList}" row="user"
			theme="bootstrap3" cssClass="table table-striped" pageable="false"
			info="false">

			<datatables:column title="User name">
				<spring:url value="/admin/useraccount/{userId}" var="viewUrl">
					<spring:param name="userId" value="${user.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}">${user.userName}</a>
			</datatables:column>
			
			<datatables:column title="Role">
				${user.roles}
			</datatables:column>

			<datatables:column title="#">
				<spring:url value="/admin/useraccount/edit/{userId}" var="editUrl">
					<spring:param name="userId" value="${user.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>

				<spring:url value="/admin/useraccount/delete/{userId}" var="deleteUrl">
					<spring:param name="userId" value="${user.id}" />
				</spring:url>
				<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>

			</datatables:column>
		</datatables:table>

		<spring:url value="/admin/useraccount/new" var="addUrl">
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-success">New User</a>

		<jsp:include page="../../../public/footer.jsp" />
	</div>
</body>

</html>