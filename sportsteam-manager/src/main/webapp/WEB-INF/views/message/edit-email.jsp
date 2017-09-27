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
			<c:when test="${email['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<!-- Panel -->
		<div class="panel panel-default">
		
			<div class="panel-heading clearfix">
				<h1 class="panel-title">
					Send Email					
				</h1>
			</div>

			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="email" method="${method}" id="send-email-form">
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					<div class="form-group">
						<form:label path="to">To</form:label> 
						<form:input cssClass="form-control" path="to" value="${email.to}" placeholder="email address" />
					</div>
					<div class="form-group">
						<form:label path="subject">Subject</form:label> 
						<form:input cssClass="form-control" path="subject" value="${email.subject}" placeholder="Subject" />
					</div>
					<div class="form-group">
						<form:label path="msg">Message</form:label>
						<form:textarea rows="5" cssClass="form-control" path="msg" value="${email.msg}" placeholder="Message" />
					</div>

					<button type="submit" class="btn btn-primary">Send Email</button>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/calendar/listevents/false/week/0/all/all" var="cancelUrl">
					</spring:url>
					<a href="${fn:escapeXml(cancelUrl)}" class="btn btn-primary btn-sm">Cancel</a>
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