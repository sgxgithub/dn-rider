<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="index"/>
    <title>Comparison</title>
</head>

<body>

<g:render template="/components/notification"/>

<div id="content" class="container">
    <g:form class="form-inline my-5" url="[action:'search',controller:'comparison']" method="get">
        <g:textField name="app" class="form-control mr-2" id="app" value="${app}" autocomplete="off" placeholder="trigramme" data-apps="${apps}"/>
        <g:textField name="version" class="form-control mr-2" id="version" value="${version}" autocomplete="off" placeholder="version"
                     data-url="${createLink(controller:'search', action: 'searchVersions', params: [releaseType:'All'])}"/>
        <button class="btn btn-outline-primary" type="submit">Search</button>
    </g:form>

    <table class="table table-hover">
        <thead>
        <tr>
            <g:if test="${packageCount}">
            <th>${app}</th>
            <g:each var="i" in="${(1..packageCount)}">
                <th>pk ${i}</th>
            </g:each>
            </g:if>
        </tr>
        </thead>
        <tbody>
        <g:if test="${version}">
            <tr>
                <th scope="row">v${version}</th>
                <g:each in="${packages}">
                    <td>${it.version}</td>
                </g:each>
            </tr>
        </g:if>
        </tbody>
    </table>
</div>

<asset:javascript src="comparison.js"/>

</body>
</html>
