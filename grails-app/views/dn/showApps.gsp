<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>
        Search for delivery-notes
    </title>
</head>
<body>

<g:render template="/dn/search"/>

<g:render template="/dn/showFlash"/>

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <g:render template="/dn/showListApps"/>
        </div>
        <div class="col-md-3">
            apps
        </div>
        <div class="col-md-3">
            versions
        </div>
        <div class="col-md-3">
            delivery-notes
        </div>
    </div>
</div>

</body>
</html>
