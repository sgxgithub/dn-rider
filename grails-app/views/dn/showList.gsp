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

<div class="container">
    <div class="row">
        <div class="col-md-4">
            <g:render template="/dn/showVersions"/>
        </div>
        <div class="col-md-8">
            <g:if test="${packages}">
                <g:render template="/dn/showDn"/>
            </g:if>
            <g:else>
                Choose a version !
            </g:else>
        </div>
    </div>
</div>

</body>
</html>
