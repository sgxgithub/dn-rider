<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="index"/>
    <title>
        Search for a liste of delivery-notes
    </title>
</head>
<body>

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