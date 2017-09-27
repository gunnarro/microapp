<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<hr>
<div class="pull-right">
	<div class="row">
		<div class="col-xs-12">
			<footer>
				<div>
					<small>
					<a href="<spring:url value="/releasenotes" htmlEscape="true" />">
						<font style="color:green">Diet</font><font style="color:blue">Manager_v</font>${initParam.dietmanagerVersion}
					</a>
					</small>
				</div>
				<div>
					<small><label class="text-muted">Powered by gunnarro</label></small>
				</div>
			</footer>
		</div>
	</div>
</div>
