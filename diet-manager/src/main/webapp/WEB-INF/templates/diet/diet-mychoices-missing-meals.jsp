<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table id="missingMealsTbl" class="table table-condensed">
	<thead>
		<tr class="info">
			<th class="text-left">Ikke fulgt diet planen følgende dager<small></small></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="mapEntry" items="${missingMealsMap}">	
			<tr class="active">
				<td class="text-left">
		            <span class="label label-danger"><fmt:formatDate value="${mapEntry.key}" pattern="EEEE dd.MM.yy" /></span>
				</td>    
			</tr>
			<tr>
				<td class="text-left" style="border-top: none;">
					<small><span class="text-muted">
						<c:forEach var="value" items="${mapEntry.value}">	
							${value}&nbsp;
						</c:forEach>						
					</span></small>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
	</tfoot>
</table>

