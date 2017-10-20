<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<jsp:include page="../public/head-includes.jsp" />
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/log/events" htmlEscape="true" />" >Log</a></li>
			<li><a href="<spring:url value="/diet/log/event/view/${log.id}" htmlEscape="true" />" >Hendelse</a></li>
        	<li class="active">Legg til kommentar</li>
    	</ul>
		
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty comment.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/diet/log/event/comment/delete/{logCommentId}" var="deleteUrl">
								<spring:param name="logCommentId" value="${comment.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
						</div>
					</div>
				</c:if>
				<h3 class="panel-title">
					Legg til ny kommentar
				</h3>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<!-- View log -->
				
				<form:form modelAttribute="comment" method="POST" id="event-comment-log-form">
					<form:hidden path="id" value="${comment.id}" />
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					 
					<fmt:formatDate var="formattedCreatedDate" value="${comment.createdDate}" pattern="dd.MM.yyyy HH:mm" />

					<div class="form-group">
						<form:label path="content">Kommentar</form:label>
						<form:textarea cssClass="form-control" path="content" value="${comment.contentHtml}" />
						<form:errors path="content" cssClass="help-block" />
					</div>
					
					<p />

					<button type="submit" class="btn btn-primary">Legg til</button>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/diet/log/events" var="cancelUrl">
					</spring:url>
					<a href="${fn:escapeXml(cancelUrl)}" class="btn btn-primary btn-sm"><spring:message code="btn.cancel"/></a>
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