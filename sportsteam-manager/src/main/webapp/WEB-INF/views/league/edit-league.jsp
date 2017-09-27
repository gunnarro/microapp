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
			<c:when test="${league['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty league.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/league/delete/{leagueId}" var="deleteUrl">
								<spring:param name="leagueId" value="${league.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-sm">delete</a>
						</div>
					</div>
				</c:if>
				<h1 class="panel-title">
					<c:if test="${league['new']}">New </c:if>
					League
				</h1>
			</div>

			<div class="panel-body">
				<form:form modelAttribute="league" method="${method}" id="league-form">
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					<div class="form-group">
						<form:label path="name">League name</form:label>
						<form:input cssClass="form-control" path="name" value="${league.name}" />
					</div>

					<div class="pull-left">
						<c:choose>
							<c:when test="${league['new']}">
								<button type="submit" class="btn btn-primary">New League</button>
							</c:when>
							<c:otherwise>
								<button type="submit" class="btn btn-primary">Update League</button>
							</c:otherwise>
						</c:choose>
					</div>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listleagues" var="editUrl">
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