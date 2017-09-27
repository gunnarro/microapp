<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<div class="container">
		<jsp:include page="../public/body-header.jsp" />

		<div class="page-header">
			<h4>
				Result, teamId:
				<c:out value="${itemList.activityId}" />
			</h4>
		</div>

		<table>
			<tr>
				<th>id</th>
				<th>value</th>
				<th>selected</th>
				<th>action</th>
			</tr>
			<c:forEach var="item" items="${itemList.items}">
				<tr>
					<td><c:out value="${item.id}" /></td>
					<td><c:out value="${item.value}" /></td>
					<td><c:out value="${item.selected}" /></td>
					<td><c:out value="${item.actionResult}" /></td>
				</tr>
			</c:forEach>
		</table>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>