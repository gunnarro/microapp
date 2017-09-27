<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../../../public/head-tag.jsp" />

<body>
	<div class="container">
		<jsp:include page="../../../public/body-header.jsp" />

		<c:if test="${not empty status}">
			<p class="bg-info">
				<c:out value="${status}" />
			</p>
		</c:if>

		<div class="page-header">
			<h4>Import data</h4>
		</div>

		<form method="POST" enctype="multipart/form-data" action="/uploadfile">
			Select file to upload <input type="file" name="file"><br /> 
			<input type="submit" value="Upload File">
		</form>
		
		<spring:url value="/uploadimage" var="uploadImgUrl" />
		<form id="uploadImgFormId" method="POST" enctype="multipart/form-data" action="${fn:escapeXml(uploadImgUrl)}">
			Select picture to upload: <input type="file" name="file"><br /> 
			Image Description: <textarea name="description" form="uploadImgFormId"></textarea><br />
			<input type="submit" value="Upload Picture">
		</form>
	
		<datatables:table id="file" data="${uploadedFileList}" row="file" theme="bootstrap3" cssClass="table table-striped" pageable="false" info="false">
			<datatables:column title="id">
				<c:out value="${file.id}" />
			</datatables:column>
			<datatables:column title="Name">
				<c:out value="${file.name}" />
			</datatables:column>
			<datatables:column title="Size KB">
				<c:out value="${file.size}" />
			</datatables:column>
			<datatables:column title="Uploaded date">
				<fmt:formatDate value="${file.createdDate}" pattern="dd.MM.yyyy hh:mm:ss" />
			</datatables:column>
			<datatables:column title="#">
				<spring:url value="/viewfile/{path}/{filename}" var="viewUrl">
					<spring:param name="path" value="${file.canonicalPath}" />
					<spring:param name="filename" value="${file.name}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">view</a>
				<spring:url value="/loaddata/{path}/{filename}" var="viewUrl">
					<spring:param name="path" value="${file.canonicalPath}" />
					<spring:param name="filename" value="${file.name}" />
				</spring:url>
				<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">load</a>
				<spring:url value="/deletefile/{path}/{filename}" var="delUrl">
					<spring:param name="path" value="${file.canonicalPath}" />
					<spring:param name="filename" value="${file.name}" />
				</spring:url>
				<a href="${fn:escapeXml(delUrl)}" class="btn btn-danger btn-xs">delete</a>
			</datatables:column>
		</datatables:table>


		<datatables:table id="img" data="${uploadedImageList}" row="img" theme="bootstrap3" cssClass="table table-striped" pageable="false" info="false">
			<datatables:column title="id">
				<c:out value="${img.id}" />
			</datatables:column>
			<datatables:column title="Created date">
				<fmt:formatDate value="${img.createdDate}" pattern="dd.MM.yyyy hh:mm:ss" />
			</datatables:column>
			<datatables:column title="Path">
				<c:out value="${img.filePath}" />
			</datatables:column>
			<datatables:column title="AbsolutPath">
				<c:out value="${img.mappedAbsoluteFilePath}" />
			</datatables:column>
			<datatables:column title="Name">
				<c:out value="${img.name}" />
			</datatables:column>
			<datatables:column title="Type">
				<c:out value="${img.type}" />
			</datatables:column>
			<datatables:column title="Size KB">
				<c:out value="${file.size}" />
			</datatables:column>
			<datatables:column title="#">
				<spring:url value="/viewimage/{id}" var="viewImgUrl">
					<spring:param name="id" value="${img.id}" />
				</spring:url>
				<a href="${fn:escapeXml(viewImgUrl)}" class="btn btn-primary btn-xs">view</a>
				<spring:url value="/deleteimage/{id}" var="delImgUrl">
					<spring:param name="id" value="${img.id}" />
				</spring:url>
				<a href="${fn:escapeXml(delImgUrl)}" class="btn btn-danger btn-xs">delete</a>
			</datatables:column>
		</datatables:table>

		<jsp:include page="../../../public/footer.jsp" />
	</div>
</body>
</html>