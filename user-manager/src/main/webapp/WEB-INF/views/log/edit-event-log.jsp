<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/home" htmlEscape="true" />" >B39</a></li>
        	<li class="active">Ny logg</li>
    	</ul>
		
		<c:choose>
			<c:when test="${log['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty log.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/log/event/delete/{logEntryId}" var="deleteUrl">
								<spring:param name="logEntryId" value="${log.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
						</div>
					</div>
				</c:if>
				<h3 class="panel-title">
					<c:if test="${log['new']}">Legg til ny </c:if>
				    Hendelse
				</h3>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="log" method="${method}" id="event-log-form">
					<form:hidden path="id" value="${log.id}" />
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->
					 
					<div class="form-group">
						<label>Opprettet av</label>
						<p class="form-control-static">${log.createdByUser}</p>
					</div>
					 
					<fmt:formatDate var="formattedCreatedDate" value="${log.createdDate}" pattern="dd.MM.yyyy HH:mm" />
					<fmt:formatDate var="formattedLastModifiedDate" value="${log.lastModifiedDate}" pattern="dd.MM.yyyy HH:mm" />
					<c:choose>
						<c:when test="${empty log.id}">
							<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="createdDate">Dato</form:label>
									<form:input min="01.01.2016" max="31.12.2017" cssClass="form-control" path="createdDate" value="${formattedCreatedDate}" placeholder="dd.MM.yyyy HH:mm"/>
									<form:errors path="createdDate" cssClass="help-block" />
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Opprettet dato</label>
								<p class="form-control-static">${formattedCreatedDate}</p>
							</div>
							<div class="form-group">
								<label>Sist endret</label>
								<p class="form-control-static">${formattedLastModifiedDate}</p>
							</div>
						</c:otherwise>
					</c:choose>
					
					<div class="form-group">
						<form:label path="level">Type</form:label>
						<form:select cssClass="form-control" path="level" >
							<form:option value="INFO" label="Informasjon" />
							<form:option value="CONFLICT" label="Konflikt" />
						</form:select>
					</div>
					
					<div class="form-group">
						<form:label path="title">Tittel</form:label>
						<form:input cssClass="form-control" path="title" value="${log.title}"  />
						<form:errors path="title" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<form:label path="content">Beskrivelse</form:label>
						<form:textarea cssClass="form-control" path="content" value="${log.content}" />
						<form:errors path="content" cssClass="help-block" />
					</div>
					
					<p />

					<c:choose>
						<c:when test="${log['new']}">
							<button type="submit" class="btn btn-primary">Legg til</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-primary">Oppdater</button>
						</c:otherwise>
					</c:choose>
				</form:form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/log/events" var="cancelUrl">
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