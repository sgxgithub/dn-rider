<h5>There are ${versionCount} dn for app ${app}</h5>
<ul>
<g:each var="version" in="${versions}">
    <g:link action="searchDn" controller="searchDn"
            params="[app: app, version: version, releaseType: releaseType, formatShow:formatShow]">
        <li>${version}</li>
    </g:link>
</g:each>
</ul>