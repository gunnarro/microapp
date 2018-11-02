<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">
<head>
	<jsp:include page="../public/head-includes.jsp" />
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />

	<div class="container-fluid">
		
	<ul class="breadcrumb">
		<li><a href="<spring:url value="/diet/menu/1" htmlEscape="true" />" >Diet Menu</a></li>
       	<li class="active">Måltid</li>
   	</ul>
    	
	<div class="panel panel-default">
		<div class="panel-heading clearfix">
			<div class="pull-right">
				<spring:url value="/diet/menuitem/edit/{menuItemId}" var="editImgUrl">
					<spring:param name="menuItemId" value="${menuItem.id}" />
				</spring:url>
		   		<spring:url value="/diet/menuitem/delete/{menuItemId}" var="delImgUrl">
					<spring:param name="menuItemId" value="${menuItem.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editImgUrl)}" class="glyphicon glyphicon-edit"></a>
				<a href="${fn:escapeXml(delImgUrl)}" class="glyphicon glyphicon-trash"></a>
			</div>
			<h3 class="panel-title">${menuItem.name}</h3>
		</div>
		<!-- end panel header -->
		
		<div class="panel-body">
       		<div class="media">
			  <div class="media-left">
			  	<a href="#">
			    	<img src="${menuItem.imageLink}" class="media-object">
			   	</a>
			  </div>
			  <div class="media-body">
			    <dl class="dl-horizontal">
					<dt>Opprettet</dt>
					<dd><fmt:formatDate value="${menuItem.createdDate}" pattern="dd.MM.yyyy HH:mm" /></dd>
					<dt>Sist endret</dt>
					<dd><fmt:formatDate value="${menuItem.lastModifiedDate}" pattern="dd.MM.yyyy HH:mm" /></dd>
					<dt>Måltid</dt>
					<dd>${menuItem.name}</dd>
					<dt>Kategori</dt>
					<dd>${menuItem.category}</dd>
					<dt>Beskrivelse</dt>
					<dd>${menuItem.description}</dd>
					<dt>Energi (KCal)</dt>
					<dd>${menuItem.energy}</dd>
					<dt>Valgt</dt>
					<dd>${menuItem.selectedCount} </dd>
					<dt>Trend siste 7 dager</dt>
					<dd>${menuItem.selectionTrends} </dd>
				</dl>
			  </div>
			</div>
	    </div>
	    
		<!-- end panel body -->
		<div class="panel-footer clearfix">
			<div class="pull-right">
				<spring:url value="/diet/menu/1" var="cancelUrl">
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

