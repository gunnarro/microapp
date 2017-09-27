<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

	<table class="table table-striped" >
		<thead>
	        <tr>
	        	<!-- 
	        	<th>#</th>
	        	 -->
	            <th>Date</th>
	            <th>League</th>
	            <th>Match</th>
	            <th>Result</th>
	            <th>Venue</th>
	            <th>Referee</th>
	            <th>Actions</th>
	        </tr>
	    </thead>
		<tbody>
			<c:forEach var="match" items="${matchList}">
				<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-not-played-color" />
				<c:set var="lblCls" value="label label-primary" />
				<c:set var="dateColor" value="label label-primary" />
				
				<c:choose>
					<c:when test="${match.startWeekDayName == 'Sat'}">
						<c:set var="dateColor" value="label label-default" />
					</c:when>
					<c:when test="${match.startWeekDayName == 'Sun'}">
						<c:set var="dateColor" value="label label-primary" />
					</c:when>
					<c:when test="${match.startWeekDayName == 'Mon'}">
						<c:set var="dateColor" value="label label-success" />
					</c:when>
					<c:when test="${match.startWeekDayName == 'Tue'}">
						<c:set var="dateColor" value="label label-info" />
					</c:when>
					<c:when test="${match.startWeekDayName == 'Wen'}">
						<c:set var="dateColor" value="label label-warning" />
					</c:when>
					<c:when test="${match.startWeekDayName == 'Thu'}">
						<c:set var="dateColor" value="label label-danger" />
					</c:when>
					<c:when test="${match.startWeekDayName == 'Fri'}">
						<c:set var="dateColor" value="label label-default" />
					</c:when>
					<c:otherwise>
				    	<c:set var="dateColor" value="label label-default" />
				    </c:otherwise>
				</c:choose>
				
				<c:if test="${match.played}">
					<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-played-color" />
					<c:set var="lblCls" value="label label-success" />
				</c:if>
				<c:if test="${match.cancelled}">
					<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-cancelled-color" />
					<c:set var="lblCls" value="label label-warning" />
				</c:if>
				<c:if test="${match.postponed}">
					<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-postponed-color" />
					<c:set var="lblCls" value="label label-warning" />
				</c:if>
				<c:if test="${match.ongoing}">
					<c:set var="statusIconCls" value="glyphicon glyphicon-flag match-status-ongoing-color" />
					<c:set var="lblCls" value="label label-Primary" />
				</c:if>	
			   <tr>
			   		<!-- 
		            <td>
		            	<span class="${statusIconCls}"></span>
		            </td>
		             -->
		            <td>
		            	<span class="${dateColor}">${match.startWeekDayName}</span>
		            	<span class="${lblCls}"><fmt:formatDate value="${match.startDate}" pattern="dd.MM.yy" /></span>
						<span class="${lblCls}"><fmt:formatDate value="${match.startDate}" pattern="HH:mm" /></span>    
		            </td>
		            <td>
		            	<spring:url value="/league/{leagueId}" var="editUrl">
							<spring:param name="leagueId" value="${match.fkLeagueId}" />
						</spring:url>
						<a href="${fn:escapeXml(editUrl)}"><c:out value="${match.fkLeagueId}" /></a>
		            </td>
		            <td>
		            	<spring:url value="/team/{teamId}" var="editUrl">
							<spring:param name="teamId" value="${match.homeTeam.id}" />
						</spring:url>
						<a href="${fn:escapeXml(editUrl)}">${match.homeTeam.name}</a>
						<span> - </span>
						<spring:url value="/team/{teamId}" var="editUrl">
							<spring:param name="teamId" value="${match.awayTeam.id}" />
						</spring:url>
						<a href="${fn:escapeXml(editUrl)}">${match.awayTeam.name}</a>
		            </td>
		            <td>
		        		<c:if test="${match.played}" >
							<c:out value="${match.result}" />
						</c:if>    
		            </td>
		            <td>
		                <a href="https://maps.google.com/?q=${match.venue}">${match.venue}</a>
		            </td>
		            <td>
		            	<spring:url value="/referee/{refereeId}" var="editUrl">
							<spring:param name="refereeId" value="${match.referee.id}" />
						</spring:url>
						<a href="${fn:escapeXml(editUrl)}"><c:out value="${match.referee.fullName}" /></a>
		            </td>
		            <td>
						<spring:url value="/match/edit/{matchId}" var="editUrl">
							<spring:param name="matchId" value="${match.id}" />
						</spring:url>
						<a href="${fn:escapeXml(editUrl)}" class="btn btn-primary btn-xs">edit</a>
		
						<spring:url value="/match/delete/{matchId}" var="deleteUrl">
							<spring:param name="matchId" value="${match.id}" />
						</spring:url>
						<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-danger btn-xs">delete</a>
				
						<spring:url value="/match/{matchId}" var="viewUrl">
							<spring:param name="matchId" value="${match.id}" />
						</spring:url>
						<a href="${fn:escapeXml(viewUrl)}" class="btn btn-primary btn-xs">registrer <span class="badge"><c:out value="${match.numberOfSignedPlayers}" /></span></a>		            
		            </td>
		        </tr>
	        </c:forEach>
		</tbody>
		<tfoot></tfoot>
	</table>