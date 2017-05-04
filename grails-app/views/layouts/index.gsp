<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="DN-RIDER"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="index.css"/>

    <g:layoutHead/>
</head>
<body>

<nav class="navbar navbar-toggleable-md navbar-light" style="background-color: #5C67C7;">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="/#">DN-RIDER</a>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav  ml-auto">
            <g:pageProperty name="page.nav"/>
        </ul>
    </div>
</nav>

<g:layoutBody/>

<div class="footer"></div>

<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>

<asset:javascript src="application.js"/>

</body>

</html>
