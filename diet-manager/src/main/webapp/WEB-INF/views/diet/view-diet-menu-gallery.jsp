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
	<jsp:useBean id="toDay" class="java.util.Date" />

	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a href="<spring:url value="/diet/dietplan/current" htmlEscape="true" />" >Diet Plan</a></li>
        	<li class="active">Meny Gallery</li>
    	</ul>
    	
		<div class="page-header">
			<h4>Meny Gallery</h4>
		</div>
					<jsp:include page="menu-item-list-gallery.jsp" >
						<jsp:param name="type" value="Frokost" />
					</jsp:include>
				
					<jsp:include page="menu-item-list-gallery.jsp" >
						<jsp:param name="type" value="Lunsj" />
					</jsp:include>
					
					<jsp:include page="menu-item-list-gallery.jsp" >
						<jsp:param name="type" value="Middag" />
					</jsp:include>
					
					<jsp:include page="menu-item-list-gallery.jsp" >
						<jsp:param name="type" value="Dessert" />
					</jsp:include>
					
					<jsp:include page="menu-item-list-gallery.jsp" >
						<jsp:param name="type" value="Kveldsmat" />
					</jsp:include>
					
					<jsp:include page="menu-item-list-gallery.jsp" >
						<jsp:param name="type" value="Mellom måltid" />
					</jsp:include>
			
		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>