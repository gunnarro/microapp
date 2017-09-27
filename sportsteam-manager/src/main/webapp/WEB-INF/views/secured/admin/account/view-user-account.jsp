<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../../../public/head-tag.jsp" />

<body>
	<jsp:include page="../../../public/body-header.jsp" />
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<div class="pull-right">
					<spring:url value="/admin/useraccount/edit/{userId}	" var="editUrl">
						<spring:param name="userId" value="${user.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>
				</div>
				<h1 class="panel-title">${user.userName}</h1>
			</div>

			<div class="panel-body center-block">
				<dl class="dl-horizontal">
					<dt>User name</dt>
					<dd>${user.userName}</dd>

					<dt>Password</dt>
					<dd>xxxxxx</dd>
					
					<dt>Roles</dt>
					<dd>${user.roles}</dd>
					
					<dt>Activated</dt>
					<dd>${user.activated}</dd>
					
				</dl>
			</div>

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/admin/listuseraccounts" var="editUrl">
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-sm">Cancel</a>
				</div>
			</div>
		</div>

		<jsp:include page="../../../public/footer.jsp" />
	</div>
</body>

</html>
