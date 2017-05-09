<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="index"/>
    <title>
        Search for a liste of delivery-notes
    </title>
</head>
<body>
<content tag="nav">
    <li>
        <g:link class="nav-link" controller="validateSchema" action="index">Validation</g:link>
    </li>
</content>

<div class="container-fluid">
    <div class="row">
        <div class="col-3 bg-faded" style="max-height:720px; overflow:scroll">
            <g:render template="/components/blockSearch"/>
            <g:render template="/components/columeVersions"/>
        </div>
        <div class="col-9" style="max-height:720px; overflow:scroll">
            <g:render template="/components/notification"/>
            <g:render template="/components/columeDn"/>
        </div>
    </div>
</div>

</body>
</html>