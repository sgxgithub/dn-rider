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
            <g:render template="/dn/columeVersions"/>
        </div>
        <div class="col-md-4">
            <g:render template="/dn/columeVersionsPaginated"/>
        </div>
        <div class="col-md-4">
            <g:if test="${dnText}">
                <g:render template="/dn/columeDn"/>
            </g:if>
            <g:else>
                Choose a version !
            </g:else>
        </div>
    </div>
</div>

</body>
</html>
