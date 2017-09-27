<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />

	<script type="text/javascript">
	    $(function () {
	    	$("#image-file").on("change", function() {
	    		fileSizeMB = (this.files[0].size/1024/1024).toFixed(2);
	        	$("#alertmsgId").html( "<strong>Warning!</strong> " + this.files[0].name + " file size is: " + (this.files[0].size/1024/1024).toFixed(2) + fileSizeMB + " MB and Max. file size is  5 MB");
	        	if (fileSizeMB > 5) {
		        	$("#alertmsgId").show();
	        	} else {
	        		$("#alertmsgId").hide();
	        	}
	    	});
	    });
	</script>

	<div class="container">

		<ul class="breadcrumb">
			<li><a
				href="<spring:url value="/gallery" htmlEscape="true" />">Bilder</a></li>
			<li class="active">Importer</li>
		</ul>

		<div id="alertmsgId" class="alert alert-warning" style="display:none;">
		</div>

		<c:if test="${not empty status}">
			<p class="bg-info">
				<c:out value="${status}" />
			</p>
		</c:if>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Import</h3>
			</div>
			<!-- end panel header -->
			<div class="panel-body">
				<form id="uploadImgFormId" method="POST" enctype="multipart/form-data" action="/image-manager/uploadimage">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<div class="form-group">
						<label for="file">Velg bilde (maks 5 MByte)</label> 
						<input id="image-file" type="file" name="file"></input>
					</div>
					<div class="form-group">
						<label for="title">Tittel</label>
						<input class="form-control" name="title"></input>
					</div>
					<div class="form-group">
						<label for="location">Sted</label>
						<input class="form-control" name="location"></input>
					</div>
					<div class="form-group">
						<label for="description">Beskrivelse</label>
						<textarea class="form-control" name="description"></textarea>
					</div>
					<input class="btn btn-primary" type="submit" value="Upload Image"></input>
				</form>
			</div>
			<!-- end panel body -->

			<div class="panel-footer clearfix">
				<div class="pull-right">
					<spring:url value="/gallery/{userId}" var="cancelUrl">
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