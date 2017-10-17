<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
	<c:forEach var="menuItem" items="${menuItems}" varStatus="loop" >
		<c:set var="alignCls" value="media-left" />
		<c:if test="${loop.count % 2 == 0}" >
			<c:set var="alignCls" value="media-right" />
		</c:if>
		<div class="media">
			 <div class="${alignCls}">
				 <c:if test="${menuItem.imageLink != null}">
				  	<a href="${menuItem.imageLink}">
				    	<img src="${menuItem.imageTumbLink}" class="media-object">
				   	</a>
				 </c:if>
			 </div>
			  <div class="media-body">
				    <dl class="dl-horizontal">
						<dt>Måltid</dt>
						<dd>${menuItem.name}</dd>
						<dt>Kategori</dt>
						<dd>${menuItem.category}</dd>
						<dt>Beskrivelse</dt>
						<dd>${menuItem.description}</dd>
						<dt>Energi (KCal)</dt>
						<dd>${menuItem.energy}</dd>
					</dl>
			  </div>
			</div>
	</c:forEach>
</c:if>