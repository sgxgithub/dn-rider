<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="index"/>
    <title>Comparison</title>
</head>

<body>

<g:render template="/components/notification"/>

<div id="content" class="container-fluid">
    <g:form class="form-inline my-5" url="[action: 'search', controller: 'comparison']" method="get">
        <g:textField name="app" class="form-control mr-2" id="app" value="${app}" autocomplete="off"
                     placeholder="trigramme" data-apps="${apps}"/>
        <g:textField name="version1" class="form-control mr-2" id="version1" value="${version1}" autocomplete="off"
                     placeholder="version1"
                     data-url="${createLink(controller: 'search', action: 'searchVersions', params: [releaseType: 'All'])}"/>
        <g:textField name="version2" class="form-control mr-2" id="version2" value="${version2}" autocomplete="off"
                     placeholder="version2"
                     data-url="${createLink(controller: 'search', action: 'searchVersions', params: [releaseType: 'All'])}"/>
        <button class="btn btn-outline-primary" type="submit">Search</button>
    </g:form>

    <table class="table table-hover table-responsive">
        <thead>
        <tr>
            <g:if test="${versions}">
                <th>${app}</th>
                <g:each var="version" in="${versions}">
                    <th>v${version}</th>
                </g:each>
            </g:if>
        </tr>
        </thead>
        <tbody>
        <g:if test="${packageIds}">
            <g:each status="i" var="packageId" in="${packageIds}">
                <tr>
                    <th>${packageId}</th>
                    <g:each var="packages" in="${listPackages}">
                        <g:if test="${packages[i]}">
                            <g:if test="${packages[i].tag == 'deleted'}">
                                <td>
                                    <span class="badge badge-danger">Deleted</span>
                                </td>
                            </g:if>
                            <g:else>
                                <td>
                                    <a href="${packages[i].packageUrl}">${packages[i].version}</a>
                                    <g:if test="${packages[i].tag == 'new'}">
                                        <span class="badge badge-success">New</span>
                                    </g:if>
                                    <g:if test="${packages[i].tag == 'updated'}">
                                        <span class="badge badge-info">Updated</span>
                                    </g:if>
                                </td>
                            </g:else>
                        </g:if>
                        <g:else>
                            <td> </td>
                        </g:else>
                    </g:each>
                </tr>
            </g:each>
        </g:if>
        </tbody>
    </table>
</div>

<asset:javascript src="comparison.js"/>

</body>
</html>
