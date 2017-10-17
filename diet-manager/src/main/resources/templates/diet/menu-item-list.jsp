<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"></script>

<c:choose>
	<c:when test="${param.type == 'Frokost'}">
		<c:set var="menuItems" value="${dietMenu.breakfastMenuItems}" />
		<c:set var="isMenu" value="true" />
	</c:when>
	<c:when test="${param.type == 'Lunsj'}">
		<c:set var="menuItems" value="${dietMenu.lunchMenuItems}" />
		<c:set var="isMenu" value="true" />
	</c:when>
	<c:when test="${param.type == 'Middag'}">
		<c:set var="menuItems" value="${dietMenu.dinnerMenuItems}" />
		<c:set var="isMenu" value="true" />
	</c:when>
	<c:when test="${param.type == 'Dessert'}">
		<c:set var="menuItems" value="${dietMenu.dessertMenuItems}" />
		<c:set var="isMenu" value="true" />
	</c:when>
	<c:when test="${param.type == 'Kveldsmat'}">
		<c:set var="menuItems" value="${dietMenu.eveningMenuItems}" />
		<c:set var="isMenu" value="true" />
	</c:when>
	<c:when test="${param.type == 'Mellom måltid'}">
		<c:set var="menuItems" value="${dietMenu.mealBetweenMenuItems}" />
		<c:set var="isMenu" value="true" />
	</c:when>
	<c:when test="${param.type == 'Middag Tilbehør'}">
		<c:set var="menuItems" value="${dietMenu.dinnerAccessoriesMenuItems}" />
		<c:set var="isMenu" value="false" />
	</c:when>
	<c:when test="${param.type == 'Middag Porsjon'}">
		<c:set var="menuItems" value="${dietMenu.dinnerPortionMenuItems}" />
		<c:set var="isMenu" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="menuItems" value="null" />
		<c:set var="isMenu" value="false" />
	</c:otherwise>
</c:choose>

<c:if test="${menuItems != 'null' }" >
	<c:forEach var="menuItem" items="${menuItems}" varStatus="loop">
		<c:set var="txtColor" value="text-primary" />
		<c:set var="statusIcon" value="glyphicon glyphicon-unchecked unchecked-color" />
		<c:set var="editIcon" value="glyphicon glyphicon-edit" />
		<c:set var="bagdeColor" value="badge badge-info" />
		
		<c:if test="${menuItem.selectedCount == 0}" >
			<c:set var="bagdeColor" value="badge badge-warning" />
		</c:if>
		<tr>
			<!-- Hack for jquery filter so the row will apear in the result set when searching for meal type -->
			<td hidden="">${param.type}</td>
			<td class="text-left" style="border-top: none;">
				<spring:url value="/diet/menuitem/edit/{menuItemId}" var="editUrl" htmlEscape="true">
						<spring:param name="menuItemId" value="${menuItem.id}" />
				</spring:url>
				<spring:url value="/diet/menuitem/view/{menuItemId}" var="viewUrl" htmlEscape="true">
						<spring:param name="menuItemId" value="${menuItem.id}" />
				</spring:url>
				<a href="${fn:escapeXml(editUrl)}" title="Edit">
					<i class="${editIcon}"></i>
				</a>
				<a href="${fn:escapeXml(viewUrl)}" title="View">
					<small>
						<span class="${txtColor}">&nbsp;${menuItem.description}</span>
					</small>
				</a>
			</td>
			<td class="text-left" style="border-top: none;">
				<c:if test="${isMenu == 'true'}">
					<spring:url value="/diet/mychoices" var="trendUrl" htmlEscape="true">
					</spring:url>
					<a href="${fn:escapeXml(trendUrl)}" title="Valgt ${menuItem.selectedCount} ganger">
						<small><span class="${bagdeColor}" id="numOfSelectionMenuItem_${menuItem.id}" >${menuItem.selectedCount}</span></small>
					</a>
				</c:if>
			</td>
			<td id="row_${loop.index}" class="text-left" style="border-top: none;">
				<c:if test="${isMenu == 'true'}">
					<c:set var="linkClass" value="btn btn-default btn-xs" />
					<c:set var="restServiceParam" value="${userId}/${menuItem.id}" />
					<c:set var="restServiceAddUri" value="menu/registrer/${userId}/${menuItem.id}" />
					<c:set var="restServiceDelUri" value="menu/deregistrer/${userId}/${menuItem.id}" />
					<c:set var="btnText" value="registrer" />
					
					<button id="addSelectedMenuItem" type="button" class="${linkClass}" onclick="registrerMenuSelection(${menuItem.id},'${restServiceAddUri}');">
						<i class="glyphicon glyphicon-plus-sign edit-color"></i>
					</button>
					<button id="deleteSelectedMenuItem" type="button" class="${linkClass}" onclick="registrerMenuSelection(${menuItem.id},'${restServiceDelUri}');">
						<i class="glyphicon glyphicon-minus-sign delete-color"></i>
					</button>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</c:if>