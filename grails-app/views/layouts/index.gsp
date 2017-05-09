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

<nav class="navbar navbar-toggleable-md navbar-light" style="background-color: #4682B4;">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="/#">DN-RIDER</a>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav  ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="https://wiki.vsct.fr/display/KTN/DN-Rider">Wiki</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://gitlab.socrate.vsct.fr/rundep/dn-rider">Gitlab</a>
            </li>
            <g:pageProperty name="page.nav"/>
            <li class="nav-item dropdown mr-5">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown">Lang<span class="caret"></span></a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="?lang=fr">FR</a>
                    <a class="dropdown-item" href="?lang=en">EN</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<g:layoutBody/>

<footer>
    <ul class="nav justify-content-center my-1">
        <li class="nav-item">
            <a class="nav-link" href="https://wiki.vsct.fr/display/KTN/DN-Rider">Wiki</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="http://gitlab.socrate.vsct.fr/rundep/dn-rider">Gitlab</a>
        </li>
    </ul>
    <p>
        <a href="#">Back to top</a>
    </p>
</footer>

<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>

<asset:javascript src="application.js"/>

</body>

</html>
