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

<h4>The result:</h4>
<h5>There are ${size_versions} dn for app ${app}</h5>

<div class="container">
    <div class="row">
        <div class="col-md-4">
            <ul>
                <g:each var="version" in="${listVersion}">
                    <g:link action="showList" params="[app: app, version: version]">
                        <li>Version:${version}</li>
                    </g:link>
                </g:each>
            </ul>
        </div>
        <div class="col-md-8">
            <g:if test="${packages}">
                <g:render template="/dn/show"/>
            </g:if>
            <g:else>
                Choose a version !
            </g:else>
        </div>
    </div>

</div>



</body>
</html>
