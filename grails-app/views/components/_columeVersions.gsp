<h5>There are ${versionCount} dn for app ${app}</h5>
<ul>
<g:each var="version" in="${versions}">
    <g:link action="searchVersions" controller="searchVersions"  params="[app: app, version: version, releaseType: releaseType]">
        <li>${version}</li>
    </g:link>
</g:each>
</ul>