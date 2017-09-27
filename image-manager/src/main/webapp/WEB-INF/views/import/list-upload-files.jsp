<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="en">

<jsp:include page="../public/head-tag.jsp" />

<body>
	<jsp:include page="../public/body-header.jsp" />
	<jsp:useBean id="toDay" class="java.util.Date" />
	
	<div class="container-fluid">
		
		<ul class="breadcrumb">
			<li><a
				href="<spring:url value="/gallery" htmlEscape="true" />">Bilder</a></li>
			<li class="active">Opplastede bilder</li>
		</ul>
	
		<table id="uploadImgTbl" class="table table-condensed">
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>
			<thead>
				<tr class="info">
					<th colspan="10" class="text-left">Uploaded Images</th>
				</tr>
				<tr>
					<th>userid</th>
					<th>created date</th>
					<th>filepath</th>
					<th>mapped file path</th>
					<th>name</th>
					<th>description</th>
					<th>type</th>
					<th>size (KB)</th>
					<th>#</th>
				</tr>
			</thead>
			</tbody>
			<c:forEach var="img" items="${uploadedImageList}">
				<tr>
					<td class="text-left">
						${img.userId}
					</td>
					<td>
						<fmt:formatDate value="${img.createdDate}" pattern="dd.MM.yyyy hh:mm:ss" />
					</td>
					<td>${img.filePath}</td>
					<td>${img.mappedAbsoluteFilePath}</td>
					<td>${img.name}</td>
					<td>${img.description}</td>
					<td>${img.type}</td>
					<td>${img.size}</td>
					<td>
						<a href="<c:url value="${img.mappedAbsoluteFilePath}/${img.name}" />" class="btn btn-primary btn-xs">view</a>
						<spring:url value="/deleteImage/{id}" var="delImgUrl">
							<spring:param name="id" value="${img.id}" />
						</spring:url>
						<a href="${fn:escapeXml(delImgUrl)}" class="btn btn-danger btn-xs">delete</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr class="active">
					<td colspan="10" class="text-left">
					</td>
				</tr>
			</tfoot>
		</table>
		
		<jsp:include page="../public/footer.jsp" />
	</div>
</body>
</html>