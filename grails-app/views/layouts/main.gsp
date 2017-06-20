<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="DN-RIDER"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:link rel="icon" href="favicon.ico" type="image/x-icon"/>
    <asset:stylesheet src="dnrider.css"/>

    <g:layoutHead/>
</head>

<body>

<nav class="navbar navbar-toggleable-md sticky-top navbar-inverse" style="background-color: #4682B4;">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="/#">DN-RIDER</a>
    <span class="text-white">v<g:meta name="info.app.version"/></span>

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav  ml-auto">
            <g:pageProperty name="page.nav"/>
            <li class="nav-item" id="nav-item-search">
                <g:link class="nav-link" controller="search" action="index">Search</g:link>
            </li>
            <li class="nav-item" id="nav-item-comparison">
                <g:link class="nav-link" controller="comparison" action="index">Comparison</g:link>
            </li>
            <li class="nav-item" id="nav-item-validation">
                <g:link class="nav-link" controller="validation" action="index">Validation</g:link>
            </li>

            <li class="nav-item dropdown mr-2">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown">
                    <span class="fa fa-language fa-lg"></span>
                </a>

                <div class="dropdown-menu">
                    <a class="dropdown-item" href="?lang=fr">FR</a>
                    <a class="dropdown-item" href="?lang=en">EN</a>
                    <a class="dropdown-item" href="?lang=zh_CN">ZH</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<g:render template="/components/notification"/>

%{--jquery from cdn and local--}%
<script src="https://code.jquery.com/jquery-3.2.1.min.js"
        integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
        crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script type="text/javascript" src="/assets/jquery-3.2.1.min.js?compile=false" ><\/script>')</script>
%{--other javascript--}%
<asset:javascript src="application.js"/>

<g:layoutBody/>

</body>

</html>
