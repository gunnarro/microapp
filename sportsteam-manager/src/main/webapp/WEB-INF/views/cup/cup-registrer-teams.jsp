<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
	function registrerTeam(btnId, serviceParam) {
		var serviceUri="/cup/unregistrerteam/";
		var btnText="deregistrer";
		if ( $('#btn_' + btnId).hasClass('btn-danger') ) {
			serviceUri="/cup/registrerteam/";
	    } 
		var restUrl="/rest/sportsteam/" + serviceUri + "/" + serviceParam;
		$.ajax({
			url : restUrl,
			type : "POST",
			data : {
			},
			cache : false,
			contentType : "application/json",
			mimeType : "application/json",
			before : function() {
				$btn.button('loading');
			},
			success : function(response) {
				$('#btn_' + btnId).button('reset');
				$('#numOfRegTeams').html(response);
				if (parseInt(response) > 3) {
					$('#numOfRegTeams').removeClass("text-danger");
					$('#numOfRegTeams').addClass("text-success");
				} else {
					$('#numOfRegTeams').removeClass("text-success");
					$('#numOfRegTeams').addClass("text-danger");
				}
				$('#btn_' + btnId).toggleClass("btn-success btn-danger");
				if ( $('#btn_' + btnId).hasClass('btn-danger') ) {
					btnText="registrer";
			    } 
			    $('#btn_' + btnId).text(btnText);
			},
			error : function(jqXhr, textStatus, errorThrown) {
				alert("Error - Cup Registrer Team: " + jqXhr + ", status: " + textStatus + ", err: "
						+ errorThrown);
			}
		});
		return true;
	}
</script>

<div class="panel-body">
	<section>
		<h4 class="panel-title">
			Registrered Teams <span class="badge alert-info"> <c:set var="textColor" value="text-danger" /> <c:if test="${param.numberOfRegistreredTeams > 0}">
					<c:set var="textColor" value="text-success" />
				</c:if> <span id="numOfRegTeams" class="${textColor}">${param.numberOfRegistreredTeams}</span> / ${param.numberOfTeams}
			</span>
		</h4>

		<table class="table borderless">
			<c:forEach var="item" items="${teamList}" varStatus="loop">
				<tr>
					<td>
						<spring:url value="/cup/{cupId}/{teamId}" var="teamCupUrl">
							<spring:param name="cupId" value="${param.cupId}" />
							<spring:param name="teamId" value="${item.id}" />
						</spring:url> 
						<a href="${fn:escapeXml(teamCupUrl)}" class="btn btn-link btn-xs">${item.value}</a></td>
					<td>&nbsp;&nbsp;</td>
					<td><c:choose>
							<c:when test="${item.selected}">
								<c:set var="linkClass" value="btn-registrer-width btn btn-success btn-xs" />
								<c:set var="restServiceParam" value="${param.cupId}/${item.id}" />
								<c:set var="btnText" value="deregistrer" />
							</c:when>
							<c:otherwise>
								<c:set var="linkClass" value="btn-registrer-width btn btn-danger btn-xs" />
								<c:set var="restServiceParam" value="${param.cupId}/${item.id}" />
								<c:set var="btnText" value="registrer" />
							</c:otherwise>
						</c:choose> <c:choose>
							<c:when test="${param.finished}">
								<button disabled="disabled" id="btn_${loop.index}" type="button" data-loading-text="updating..." class="${linkClass}"
									onclick="registrerTeam(${loop.index},'${restServiceParam}');">
									<c:out value="${btnText}" />
								</button>
							</c:when>
							<c:otherwise>
								<button id="btn_${loop.index}" type="button" data-loading-text="updating..." class="${linkClass}"
									onclick="registrerTeam(${loop.index},'${restServiceParam}');">
									<c:out value="${btnText}" />
								</button>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</table>
	</section>
</div>
