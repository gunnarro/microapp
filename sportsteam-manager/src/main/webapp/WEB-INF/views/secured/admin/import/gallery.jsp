<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<html lang="en">

<jsp:include page="../../../public/head-tag.jsp" />

<style type="text/css">
/* add a little bottom space under the images */
.thumbnail {
	margin-bottom: 7px;
}
</style>

<body>
	<div class="container">
		<jsp:include page="../../../public/body-header.jsp" />

		<div class="page-header">
			<h4>Gallery</h4>
			<h5>${name}</h5>
		</div>
		<c:forEach var="filePath" items="${imageList}">
		<div class="row">
			<div class="col-xs-3">
				<a href="#" class="thumbnail">
					<img src="${filePath}" class="img-responsive img-rounded"></a>
			</div>
		</div>
		</c:forEach>
		<jsp:include page="../../../public/footer.jsp" />
	</div>
</body>
</html>
