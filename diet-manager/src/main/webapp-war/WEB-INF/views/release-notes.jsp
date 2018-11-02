<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<jsp:include page="public/head-includes.jsp" />

<body>
	<jsp:include page="public/body-header.jsp" />
	<div class="container-fluid">
		<h1 class="releases-header">Release notes</em></h1>
		<div class="release">
        		<h2><a href="https://github.com/gunnarro">2017-02-17 <span>v1.2.1</span> </a></h2>
        		<ul>Bug fix</ul>
        	</div>
			<div class="release">
        		<h2><a href="https://github.com/gunnarro">2016-12-03 <span>v1.1.0</span> </a></h2>
        		<ul>Adde new features and improvments</ul>
        	</div>
      		<div class="release">
        		<h2><a href="https://github.com/gunnarro">2015-01-01 <span>v1.0.0</span></a></h2>
        		<ul>Inital release</ul>
        	</div>
		<jsp:include page="public/footer.jsp" />
	</div>
</body>

</html>