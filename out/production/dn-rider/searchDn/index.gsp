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
        <g:link class="nav-link" controller="validation" action="index">Validation</g:link>
    </li>
</content>

<div class="container-fluid">
    <div class="row">
        <div class="col-3 py-3 bg-faded">
            <div>
                <g:render template="blockSearch"/>
            </div>
            <div style="max-height:600px; overflow:scroll">
                <g:render template="blockVersions"/>
            </div>
        </div>
        <div class="col-9" style="max-height:800px; overflow:scroll">
            <g:render template="/components/notification"/>
            <g:render template="blockDn"/>
        </div>
    </div>
</div>

</body>
</html>