<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style type="text/css">
	.table-condensed>thead>tr>th, 
	.table-condensed>tbody>tr>th,
	.table-condensed>tfoot>tr>th, 
	.table-condensed>thead>tr>td,
	.table-condensed>tbody>tr>td, 
	.table-condensed>tfoot>tr>td {padding: 3px;}
</style>
		<jsp:useBean id="toDay" class="java.util.Date" />
		<table id="myWieghtTbl" class="table table-condensed">
			<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
			<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
			<caption class="text-right">
				<small>Last updated: <fmt:formatDate value="${toDay}" pattern="dd.MM.yy HH:mm:ss" /></small>
			</caption>

			<thead>
				<tr class="info">
					<th colspan="6" class="text-left">Min Vekt</th>
					<th class="text-right">
						<spring:url value="/diet/body/measurement/new" var="editUrl" htmlEscape="true">
						</spring:url> 
						<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">
							<span>Legg til</span>
						</a>
					</th>
				</tr>
				<tr class="info">
					<th><small>#</small></th>
					<th><small>Dato</small></th>
					<th><small>Vekt (kg)</small></th>
					<th><small>Høyde (cm)</small></th>
					<th><small>KMI</small></th>
					<th><small>Kommentar</small></th>
					<th><small>#</small></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="nameColor" value="label label-primary" />
				<c:set var="itemIcon" value="glyphicon glyphicon-check" />
				<c:set var="editIcon" value="glyphicon glyphicon-edit edit-color" />
				<c:set var="txtColor" value="text-muted" />
				<c:set var="numberColor" value="text-primary" />
				<c:set var="deleteIcon" value="glyphicon glyphicon-remove-circle delete-color" />
				<c:set var="nameColor" value="label label-primary" />
					
				<c:forEach var="log" items="${logs}" varStatus="loop">	
					<c:set var="trendWeightIcon" value="none" />
					<c:choose>
							<c:when test="${log.trendWeight > 0}">
								<c:set var="trendWeightIcon" value="glyphicon glyphicon-arrow-up color-satisfaction-5" />
							</c:when>
							<c:when test="${log.trendWeight < 0}">
								<c:set var="trendWeightIcon" value="glyphicon glyphicon-arrow-down color-satisfaction-1" />
							</c:when>
							<c:otherwise>
								<c:set var="trendWeightIcon" value="glyphicon glyphicon-arrow-right color-muted" />
							</c:otherwise>
					</c:choose>
					<c:set var="rowBgColor" value="none" />
					<c:if test="${loop.index % 2 == 0}" >
						<c:set var="rowBgColor" value="bg-color-row-even" />
					</c:if>
					<tr class="${rowBgColor}">
						<td class="text-left" style="border-top: none;">
							<i class="${trendWeightIcon}" ></i>
						</td>
						<td class="text-left" style="border-top: none;">
							<small><span class="${txtColor}"><fmt:formatDate value="${log.logDate}" pattern="EEE dd.MM.yyyy" /></span></small>
						</td>
						<td class="text-left" style="border-top: none;">
							<small><span class="${numberColor}">${log.weight}</span> <span class="${txtColor}">(<fmt:formatNumber value="${log.weight - 52}" pattern="##.#" />)</span></small>
						</td>
						<td class="text-left" style="border-top: none;">
							<small><span class="${numberColor}">${log.height}</span> <span class="${txtColor}">(<fmt:formatNumber value="${log.height - 163}" pattern="##.#" />)</span></small>
						</td>
						<td class="text-left" style="border-top: none;">
							<small><span class="${numberColor}"><fmt:formatNumber value="${log.bmi}" pattern="##.#" /></span> <span class="${txtColor}">(<fmt:formatNumber value="${log.bmi - 19.2}" pattern="##.#" />)</span></small>
						</td>
						<td class="text-left" style="border-top: none;"><small><span class="${txtColor}">${log.comment}</span></small></td>
						<td class="text-left" style="border-top: none;">
							<spring:url value="/diet/body/measurement/delete/{id}" var="deleteUrl">
									<spring:param name="id" value="${log.id}" />
							</spring:url>
							<a href="${fn:escapeXml(deleteUrl)}" >
								<i class="glyphicon glyphicon-trash" ></i>
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr class="info">
					<td colspan="7" class="text-right">
						<spring:url value="/home" var="backUrl" >
						</spring:url>
						<a href="${fn:escapeXml(backUrl)}" class="btn btn-primary btn-sm">Tilbake</a>
					</td>
				</tr>
			</tfoot>
		</table>