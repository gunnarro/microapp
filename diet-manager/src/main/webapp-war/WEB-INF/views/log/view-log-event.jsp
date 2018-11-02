<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">
<head>
	<jsp:include page="../public/head-includes.jsp" />
	<!-- markdown import -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
	<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
	
	<style type="text/css">
	.rounded {
  	border-radius: 10px;
  	}
  	
  	[class*="col-"] {
    margin-bottom: 15px;
}
	</style>
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />

	<script type="text/javascript">
	 $(document).ready(function(){
    	 var simplemde = new SimpleMDE({ element: $("#logCommentMsgInputId")[0] });
     })
	</script>
	
	<div class="container-fluid">
		
	<ul class="breadcrumb">
		<li><a href="<spring:url value="/diet/log/events" htmlEscape="true" />" >Log</a></li>
       	<li class="active">Hendelse</li>
   	</ul>
   
	<div class="panel panel-default">
		<div class="panel-heading clearfix">
			<div class="pull-right">
				<spring:url value="/diet/log/event/edit/{logId}" var="editImgUrl">
					<spring:param name="logId" value="${log.id}" />
				</spring:url>
		   		<spring:url value="/diet/log/event/delete/{logId}" var="delImgUrl">
					<spring:param name="logId" value="${log.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editImgUrl)}" class="glyphicon glyphicon-edit"></a>
				<a href="${fn:escapeXml(delImgUrl)}" class="glyphicon glyphicon-trash"></a>
			</div>
			
			 <c:if test="${log.level != 'ALL'}">
					<c:choose>
						<c:when test="${log.level == 'INFO'}">
							<c:set var="levelClass" value="label label-info" />
						</c:when>
						<c:when test="${log.level == 'ACTIVITY'}">
							<c:set var="levelClass" value="label label-success" />
						</c:when>
						<c:when test="${log.level == 'REPORT'}">
							<c:set var="levelClass" value="label label-warning" />
						</c:when>
						<c:when test="${log.level == 'CONFLICT'}">
							<c:set var="levelClass" value="label label-danger" />
						</c:when>
					</c:choose>
				</c:if>
			
			<h3 class="panel-title"><span class="${levelClass}">${log.level}</span> ${log.title}</h3>
		</div>
		<!-- end panel header -->
		
		<div class="panel-body center-block">
			<ul class="list-group">
			  <li class="list-group-item list-group-item-success" style="margin: 0px 0px 10px 0px;">
			  	<h5><small>Created <fmt:formatDate value="${log.createdDate}" pattern="EEEE dd.MM.yyyy HH:mm:ss" /> by ${log.createdByUser}</small></h5>
				<p class="text-left">${log.contentHtml}</p>
			  </li>
  
			   <c:forEach var="comment" items="${log.logComments}">
				     <li class="list-group-item list-group-item-info" style="margin: 0px 0px 10px 25px;">
			    		<h5 class="text-left"><small>Created <fmt:formatDate value="${comment.createdDate}" pattern="EEEE dd.MM.yyyy HH:mm:ss" /> by ${comment.createdByUser}</small></h5>
						<p class="text-left">${comment.contentHtml}</p>
			  		</li>
			  </c:forEach>
			</ul> 

			<!-- add comment -->
			<form method="POST" action="<spring:url value="/diet/log/event/comment/add" htmlEscape="true" />" id="event-log-comment-form">
					<input type="hidden" id="logId" name="logId" value="${log.id}" />
					<div class="form-group">
						<label for="comment">Kommentar</label>
						<textarea class="form-control" id="logCommentMsgInputId" name="comment" ></textarea>
					</div>
					<button type="submit" class="btn btn-primary">Legg til</button>
			</form>
	    </div>
	    
		<!-- end panel body -->
		<div class="panel-footer clearfix">
			<div class="pull-right">
				<spring:url value="/diet/log/events" var="backUrl">
				</spring:url>
				<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm"><spring:message code="btn.cancel"/></a>
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

