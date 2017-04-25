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
        <div class="col-md-2">
            <g:render template="/dn/columeApps"/>
        </div>
        <div class="col-md-2">
            <g:render template="/dn/columeVersions"/>
        </div>
        <div class="col-md-8">
            <g:render template="/dn/columeDn"/>
        </div>
    </div>
</div>

</body>
</html>
