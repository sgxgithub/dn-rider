<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>
        Search for a liste of delivery-notes
    </title>
</head>
<body>

<g:render template="/components/search"/>
<g:render template="/components/showErrors"/>

<div class="container">
    <div class="row">
        <div class="col-md-1">
        </div>
        <div class="col-md-2">
            <g:render template="/components/columeVersions"/>
        </div>
        <div class="col-md-8">
            <g:render template="/components/columeDn"/>
        </div>
    </div>
</div>

</body>
</html>