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
			<li><a href="<spring:url value="/gallery" htmlEscape="true" />" >Mine Bilder</a></li>
        	<li class="active">Bilde</li>
    	</ul>
		
		<c:choose>
			<c:when test="${imageDetail['new']}">
				<c:set var="method" value="POST" />
			</c:when>
			<c:otherwise>
				<c:set var="method" value="PUT" />
			</c:otherwise>
		</c:choose>

		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<c:if test="${not empty imageDetail.id}">
					<div class="pull-right">
						<div class="pull-right">
							<spring:url value="/image/delete/{id}" var="deleteUrl">
								<spring:param name="id" value="${imageDetail.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
						</div>
					</div>
				</c:if>
				<h3 class="panel-title">
					<c:if test="${imageDetail['new']}">Legg til nytt </c:if>
				    Bilde
				</h3>
			</div>
			<!-- end panel header -->

			<div class="panel-body">
				<form:form modelAttribute="imageDetail" method="${method}" id="image-detail-form">
					<form:hidden path="id" value="${imageDetail.id}" />
					<!-- only for debug -->
					<!-- 
					<div class="form-group has-error">
						<form:errors path="*" />
					</div>
					 -->

					<c:if test="${not empty imageDetail.id}">
						<div class="form-group">
							<label>Bilde</label>
							<p class="form-control-static">
								<img src="<c:url value="${imageDetail.mappedAbsoluteFilePath}/tumbs/${imageDetail.name}" />">
							</p>
						</div>
					</c:if>
					 
					<fmt:formatDate var="formattedCreatedDate" value="${imageDetail.createdDate}" pattern="dd.MM.yyyy HH:mm" />
					<fmt:formatDate var="formattedLastModifiedDate" value="${imageDetail.lastModifiedDate}" pattern="dd.MM.yyyy HH:mm" />
					<div class="form-group">
						<label>Opprettet dato</label>
						<p class="form-control-static">${formattedCreatedDate}</p>
					</div>
					<div class="form-group">
						<label>Sist endret</label>
						<p class="form-control-static">${formattedLastModifiedDate}</p>
					</div>
					
					<div class="form-group">
						<label>Name</label>
						<p class="form-control-static">${imageDetail.name}</p>
					</div>
					
					<div class="form-group">
						<form:label path="title">Tittel</form:label>
						<form:input cssClass="form-control" path="title" value="${imageDetail.title}"  />
						<form:errors path="title" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<form:label path="geoLocation">Sted</form:label>
						<form:input cssClass="form-control" path="geoLocation" value="${imageDetail.geoLocation}"  />
						<form:errors path="geoLocation" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<form:label path="description">Beskrivelse</form:label>
						<form:textarea cssClass="form-control" path="description" value="${imageDetail.description}" />
						<form:errors path="description" cssClass="help-block" />
					</div>
					
					<p />

					<c:choose>
						<c:when test="${imageDetail['new']}">
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
					<spring:url value="/gallery" var="cancelUrl">
						<spring:param name="userId" value="1" />
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