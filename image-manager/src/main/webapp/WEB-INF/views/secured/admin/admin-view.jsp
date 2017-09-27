<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../../public/head-tag.jsp" />

<body>
	<jsp:include page="../../public/body-header.jsp" />
	<div class="container">

		<c:if test="${infoMsg != null}">
			<div class="alert alert-info" role="alert">${infoMsg}</div>
		</c:if>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title">Administration</h1>
			</div>

			<div class="panel-body center-block">
				<table class="table borderless">
					<thead>
						<tr>
							<th>Key</th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="keyValue" items="${statisticList}" varStatus="loop">
							<tr>
								<td>${keyValue.key}</td>
								<td>${keyValue.value}</td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td></td>
							<td><spring:url value="/admin/deletedata" var="deleteUrl">
								</spring:url> <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a></td>
						</tr>
					</tfoot>
				</table>
			</div>

		</div>

		<jsp:include page="../../public/footer.jsp" />
	</div>
</body>

</html>