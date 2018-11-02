<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<jsp:include page="../public/head-includes.jsp" />
	<!-- Bootstrap Date-Picker Plugin -->
	 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js"></script>
      <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css"> 
      <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>

	<!-- markdown import -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
	<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>

</head>
<body>

	<jsp:include page="../public/body-header.jsp" />
	<jsp:include page="../confirm-dialog.jsp" />
	
	<script type="text/javascript">
	
		$('#delete_log_btn_id').on('click',function(){
        	 BootstrapDialog.confirm('Hi Apple, are you sure?');
       	});
	
	     $(function () {
	    	 var container=$('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
	    	 var options={
	    		 format: 'DD.MM.YYYY h:mm',
	    	 };
             $('#createdDateTimePickerId').datetimepicker(options);
         });
	     
	     $(document).ready(function(){
	    	 var simplemde = new SimpleMDE({ element: $("#logMsgInputId")[0] });
	     })
	</script>
	
	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/log/events" htmlEscape="true" />" >log</a></li>
        	<li class="active">Hendelse</li>
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
							<spring:url value="/diet/log/event/delete/{logEntryId}" var="deleteUrl">
								<spring:param name="logEntryId" value="${log.id}" />
							</spring:url>
							<a id="delete_log_btn_id" href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
							<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#delete-confirm">Confirm</button>
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
					 
					<fmt:formatDate var="formattedCreatedDate" value="${log.createdDate}" pattern="dd.MM.yyyy HH:mm" />
					<fmt:formatDate var="formattedLastModifiedDate" value="${log.lastModifiedDate}" pattern="dd.MM.yyyy HH:mm" />
					<c:choose>
						<c:when test="${empty log.id}">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label for="createdDate" class="control-label"><spring:message code="label.date"/></label>
								<div class="controls">
									<div class="input-group date" id="createdDateTimePickerId">
										<input id="createdDateId" name="createdDate" type="text" class="date-picker form-control" value="${formattedCreatedDate}" placeholder="dd.MM.yyyy HH:mm" />
										<label for="createdDate" class="input-group-addon">
											<span class="glyphicon glyphicon-calendar"></span>
										</label>
									</div>
								</div>
								<form:errors path="createdDate" cssClass="help-block error-color" />
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<label>Opprettet av</label>
								<p class="form-control-static">${log.createdByUser}</p>
							</div>
							<div class="form-group">
								<label>Opprettet dato</label>
								<p class="form-control-static">${formattedCreatedDate}</p>
							</div>
							<div class="form-group">
								<label path="createdDate">Sist endret</label>
								<p class="form-control-static">${formattedLastModifiedDate}</p>
							</div>
						</c:otherwise>
					</c:choose>
					
					<div class="form-group">
						<form:label path="level">Type</form:label>
						<form:select cssClass="form-control" path="level" >
							<form:option value="INFO" label="Informasjon" />
							<form:option value="CONFLICT" label="Konflikt" />
							<form:option value="ACTIVITY" label="Aktivitet" />
							<form:option value="REPORT" label="Rapport" />
						</form:select>
					</div>
					
					<div class="form-group">
						<form:label path="title">Tittel</form:label>
						<form:input cssClass="form-control" path="title" value="${log.title}"  />
						<form:errors path="title" cssClass="help-block" />
					</div>
					
					<div class="form-group">
						<form:label path="content">Beskrivelse</form:label>
						<form:textarea id="logMsgInputId" cssClass="form-control" path="content" value="${log.contentHtml}" />
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