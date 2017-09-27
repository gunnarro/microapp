<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="form-group">
	<form:label path="address.streetName">Streetname</form:label>
	<form:input cssClass="form-control" path="address.streetName" value="${address.streetName}" />
</div>

<div class="form-group">
	<form:label path="address.streetNumber">Street number</form:label>
	<form:input cssClass="form-control" path="address.streetNumber" value="${address.streetNumber}" />
</div>

<div class="form-group">
	<form:label path="address.streetNumberPostfix">Street Number Postfix</form:label>
	<form:input cssClass="form-control" path="address.streetNumberPostfix" value="${address.streetNumberPostfix}" />
</div>

<div class="form-group">
	<form:label path="address.postCode">Post Code</form:label>
	<form:input cssClass="form-control" path="address.postCode" value="${address.postCode}" />
</div>

<div class="form-group">
	<form:label path="address.city">City</form:label>
	<form:input cssClass="form-control" path="address.city" value="${address.city}" />
</div>

<div class="form-group">
	<form:label path="address.country">Country</form:label>
	<form:input cssClass="form-control" path="address.country" value="${address.country}" />
</div>