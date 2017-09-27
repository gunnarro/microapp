<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<div class="container">
		<jsp:include page="../public/body-header.jsp" />

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<div class="pull-right">
					<spring:url value="/referee/edit/{refereeId}" var="editUrl">
						<spring:param name="refereeId" value="${referee.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>
				</div>
				<h1 class="panel-title">${referee.fullName}</h1>
			</div>

			<div class="panel-body">
				<dl class="dl-horizontal">
					<dt>Club</dt>
					<dd>${referee.club.fullName}</dd>

					<dt>Name</dt>
					<dd>${referee.fullName}</dd>

					<dt>Address</dt>
					<dd>
						<c:if test="${referee.address.addressValid}">
							<address>
								<a href="https://maps.google.com/?q=${referee.address.fullAddress}"><i class="glyphicon glyphicon-map-marker"></i> ${referee.address.fullAddress}</a> <br>
								${referee.address.postCode} ${referee.address.city}<br> ${referee.address.country}
							</address>
						</c:if>
					</dd>

					<dt>Gender</dt>
					<dd>${referee.gender}</dd>

					<dt>Mobile number</dt>
					<dd>
						<c:if test="${not empty referee.mobileNumber}">
							<a href="tel://${referee.mobileNumber}"><i class="glyphicon glyphicon-earphone"></i> ${referee.mobileNumber}</a>
						</c:if>
					</dd>
					<dd>
						<c:if test="${not empty referee.mobileNumber}">
							<a href="sms://${referee.mobileNumber}"><i class="glyphicon glyphicon-envelope"></i> ${referee.mobileNumber}</a>
						</c:if>
					</dd>

					<dt>Email address</dt>
					<dd>
						<c:if test="${not empty referee.emailAddress}">
							<a href="mailto://${referee.emailAddress}"><i class="glyphicon glyphicon-envelope"></i> ${referee.emailAddress}</a>
						</c:if>
					</dd>
				</dl>

			</div>

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listreferees/{clubId}" var="editUrl">
						<spring:param name="clubId" value="${referee.fkClubId}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-sm">Back</a>
				</div>
			</div>
		</div>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>
