<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="index"/>
    <title>Comparison</title>
    <asset:stylesheet src="comparison.css"/></head>

<body>

<g:render template="/components/notification"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-3 py-3 bg-faded">
            <g:render template="blockSearch"/>
        </div>

        <div class="col-9">
            %{--<g:form class="form-inline my-5" url="[action: 'search', controller: 'comparison']" method="get">--}%
                %{--<g:textField name="app" class="form-control mr-2" id="app" value="${app}" autocomplete="off"--}%
                             %{--placeholder="trigramme" data-apps="${apps}"/>--}%
                %{--<g:textField name="version1" class="form-control mr-2" id="version1" value="${version1}"--}%
                             %{--autocomplete="off"--}%
                             %{--placeholder="version1"--}%
                             %{--data-url="${createLink(controller: 'search', action: 'searchVersions', params: [releaseType: 'All'])}"/>--}%
                %{--<button class="btn btn-outline-primary" type="submit">Compare</button>--}%
            %{--</g:form>--}%
            <g:render template="tableComparison"/>
        </div>
    </div>
</div>

<asset:javascript src="comparison.js"/>

</body>
</html>
