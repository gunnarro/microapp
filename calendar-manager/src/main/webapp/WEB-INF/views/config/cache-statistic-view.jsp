<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<div class="container">
		
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<div class="pull-right">
					<spring:url value="/contact/edit/{contactId}" var="editUrl">
						<spring:param name="contactId" value="${contact.id}" />
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-sm">edit</a>
				</div>
				<h1 class="panel-title">${contact.fullName}</h1>
			</div>

			<div class="panel-body">

				<dl class="dl-horizontal">
					<dt>Status</dt>
					<dd>${contact.status.name}</dd>

					<dt>Club</dt>
					<dd>
						<spring:url value="/club/{clubId}" var="viewUrl">
							<spring:param name="clubId" value="${contact.team.club.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}">${contact.team.club.fullName}</a>
					</dd>

					<dt>Team</dt>
					<dd>
						<spring:url value="/team/{teamId}" var="viewUrl">
							<spring:param name="teamId" value="${contact.team.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}">${contact.team.name}</a>
					</dd>

					<dt>Team roles</dt>
					<dd>
						<c:if test="${contact.teamRoles}">
							<c:forEach var="type" items="${contact.teamRoleList}">
								<span class="text-capitalize"><c:out value="${type.name}"></c:out></span>
								<br>
							</c:forEach>
						</c:if>
					</dd>

					<dt>Name</dt>
					<dd>${contact.fullName}</dd>

					<dt>Address</dt>
					<dd>
						<c:if test="${contact.address.addressValid}">
							<address>
								<a href="https://maps.google.com/?q=${contact.address.fullAddress}"><i class="glyphicon glyphicon-map-marker"></i> ${contact.address.fullAddress}</a> <br>
								${contact.address.postCode} ${contact.address.city}<br> ${contact.address.country}
							</address>
						</c:if>
					</dd>

					<dt>Gender</dt>
					<dd>${contact.gender}</dd>

					<dt>Mobile number</dt>
					<dd>
						<c:if test="${not empty contact.mobileNumber}">
							<a href="tel://${contact.mobileNumber}"><i class="glyphicon glyphicon-earphone"></i> ${contact.mobileNumber}</a>
						</c:if>
					</dd>
					<dd>
						<c:if test="${not empty contact.mobileNumber}">
							<a href="sms://${contact.mobileNumber}"><i class="glyphicon glyphicon-envelope"></i> ${contact.mobileNumber}</a>
						</c:if>
					</dd>

					<dt>Email address</dt>
					<dd>
						<c:if test="${not empty contact.emailAddress}">
							<a href="mailto://${contact.emailAddress}"><i class="glyphicon glyphicon-envelope"></i> ${contact.emailAddress}</a>
						</c:if>
					</dd>
				</dl>

				<div class="page-header">
					<h4>Relations (Children/Players)</h4>
				</div>

				<ul class="list-group">
					<c:forEach var="item" items="${contact.relationItemList}" varStatus="loop">
						<li class="list-group-item"><a href="/player/${item.id}">${item.value}</a></li>
						<td></td>
					</c:forEach>
				</ul>
			</div>

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/listcontacts/{teamId}" var="editUrl">
					</spring:url>
					<a href="${fn:escapeXml(editUrl)}" class="btn btn-default btn-sm">Back</a>
				</div>
			</div>
		</div>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>

</html>

