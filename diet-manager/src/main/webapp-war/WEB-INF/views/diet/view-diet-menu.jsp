<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<head>
	<jsp:include page="../public/head-includes.jsp" />
	<style type="text/css">
		.table-condensed>thead>tr>th, 
		.table-condensed>tbody>tr>th,
		.table-condensed>tfoot>tr>th, 
		.table-condensed>thead>tr>td,
		.table-condensed>tbody>tr>td, 
		.table-condensed>tfoot>tr>td {padding: 3px;}
	</style>
	<script type="text/javascript">
	$(document).ready(function () {
	    (function ($) {
	        $('#filterInputId').keyup(function () {
	            var rex = new RegExp($(this).val(), 'i');
	            $('.table-tbody tr').hide();
	            //console.log("id: " + $('.table-tbody tr').attr('id'));
	            $('.table-tbody tr').filter(function () {
	                return rex.test($(this).text());
	            }).show();
	        })
	    }(jQuery));
	});
	</script>
</head>
<body>
	<jsp:include page="../public/body-header.jsp" />
	<jsp:useBean id="toDay" class="java.util.Date" />

	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/dietplan/current" htmlEscape="true" />" >Diet Plan</a></li>
        	<li class="active">Meny</li>
    	</ul>
    	
		<div class="page-header">
			<h4>Meny</h4>
			<spring:url value="/diet/mychoices" var="myChoicesUrl" htmlEscape="true">
			</spring:url> 
			<h5>
				<a href="${fn:escapeXml(myChoicesUrl)}">Mine valg</a>
			</h5>
			<spring:url value="/diet/menu/gallery/{menuId}" var="galleryUrl" htmlEscape="true">
			<spring:param name="menuId" value="${dietMenu.id}"/>
			</spring:url> 
			<h5>
				<a href="${fn:escapeXml(galleryUrl)}">Gallery</a>
			</h5>
		</div>
		
		<div class="input-group input-group-margin" >
			<span class="input-group-addon">
				<i class="glyphicon glyphicon-search"></i>
			</span>
			<label for="filterInputId" class="sr-only">Filter</label>
			<input id="filterInputId" type="text" class="form-control" placeholder="Filter text" />
		</div>
		
		<table id="dietMenuTbl" class="table table-condensed">
			<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>

			<thead>
				<tr class="info">
					<th colspan="2" class="text-left">Meny</th>
					<th class="text-right">
						<spring:url value="/diet/menuitem/new/{menuId}" var="addUrl" htmlEscape="true">
							<spring:param name="menuId" value="${dietMenu.id}" />
						</spring:url> 
						<a href="${fn:escapeXml(addUrl)}" class="btn btn-primary btn-xs">
							<span>Legg til</span>
						</a>
					</th>
				</tr>
			</thead>
			<tbody class="table-tbody">
					<tr class="active">
						<td colspan="3" class="text-left">
							<span class="label breakfast-bgcolor">Frokost</span>
						</td>
					</tr>
					<jsp:include page="menu-item-list.jsp" >
						<jsp:param name="type" value="Frokost" />
					</jsp:include>
					
					<tr class="active">
						<td colspan="3" class="text-left">
							<span class="label lunch-bgcolor">Lunsj</span>
						</td>
					</tr>
					<jsp:include page="menu-item-list.jsp" >
						<jsp:param name="type" value="Lunsj" />
					</jsp:include>
					
					<tr class="active">
						<td colspan="3" class="text-left">
							<span class="label dinner-bgcolor">Middag</span>
						</td>
					</tr>
					<jsp:include page="menu-item-list.jsp" >
						<jsp:param name="type" value="Middag" />
					</jsp:include>
					
					<tr class="active">
						<td colspan="3" class="text-left">
							<span class="label dessert-bgcolor">Dessert</span>
						</td>
					</tr>
					<jsp:include page="menu-item-list.jsp" >
						<jsp:param name="type" value="Dessert" />
					</jsp:include>

					<tr class="active">
						<td colspan="3" class="text-left">
							<span class="label evening-bgcolor">Kveldsmat</span>
						</td>
					</tr>
					<jsp:include page="menu-item-list.jsp" >
						<jsp:param name="type" value="Kveldsmat" />
					</jsp:include>
					
					<tr class="active">
						<td colspan="3" class="text-left">
							<span class="label meal-between-bgcolor">Mellom måltid</span>
						</td>
					</tr>
					<jsp:include page="menu-item-list.jsp" >
						<jsp:param name="type" value="Mellom måltid" />
					</jsp:include>
					
					<tr class="active">
						<td colspan="3" class="text-left">
							<span class="label dinner-accessories-bgcolor">Middag Tilbehør</span>
						</td>
					</tr>
					<jsp:include page="menu-item-list.jsp" >
						<jsp:param name="type" value="Middag Tilbehør" />
					</jsp:include>
					
					<tr class="active">
						<td colspan="3" class="text-left">
							<span class="label dinner-portions-bgcolor">Middag Porsjoner</span>
						</td>
					</tr>
					<jsp:include page="menu-item-list.jsp" >
						<jsp:param name="type" value="Middag Porsjon" />
					</jsp:include>

			</tbody>
			<tfoot>
				<tr class="info">
					<td colspan="3" class="text-right">
						<spring:url value="/diet/listdietplans" var="backUrl" >
						</spring:url>
						<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm"><spring:message code="btn.cancel"/></a>
					</td>
				</tr>
			</tfoot>
		</table>

		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>