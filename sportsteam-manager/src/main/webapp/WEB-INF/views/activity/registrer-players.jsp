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
	<div class="container">
		<jsp:include page="../public/body-header.jsp" />

		<div class="page-header">
			<h4>
				Registrer/unregistrer player for
				<c:out value="${activity.type}" />
				(
				<c:out value="${numberOfPlayers}" />
				,
				<c:out value="${numberOfSignedPlayers}" />
				)
			</h4>
			<h5>
				<fmt:formatDate value="${activity.startDate}" pattern="dd.MM.yyyy" />
				<c:out value="${activity.description}" />
			</h5>
		</div>

		<form:form method="POST" action="/activity/registrer" modelAttribute="itemList" id="registrer-player-form">
			<fieldset>
				<legend>Registrer/Unregistrer Player(s)</legend>
				<table>
					<c:forEach var="item" items="${itemList.items}" varStatus="loop">
						<tr>
							<td><form:checkbox path="items[${loop.index}].selected" value="${item.selected}" /></td>
							<td><form:hidden path="items[${loop.index}].id" value="${item.id}" /></td>
							<td><form:hidden path="items[${loop.index}].value" value="${item.value}" /></td>
							<td><c:out value="${item.value}" /></td>
						</tr>
					</c:forEach>
				</table>
				<form:hidden path="activityId" value="${activity.id}" />
				<form:hidden path="seasonId" value="${activity.fkSeasonId}" />
				<input type="submit" value="Registrer" />
			</fieldset>
		</form:form>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>