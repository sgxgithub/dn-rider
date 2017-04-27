<h4>The result:</h4>
<h5>There are ${size_versions} dn for app ${app}</h5>
<div class="container">
<div class="row">
    <ul>
        <g:each var="version" in="${versions}">
            <g:link action="showVersions" params="[app: app, version: version, releaseType:releaseType]">
                <li>${version}</li>
            </g:link>
        </g:each>
    </ul>
</div>
<g:paginate next="Forward" prev="Back" max="10" maxsteps="10" controller="dn" action="showVersions"
            total="${versionCount}"/>
</div>