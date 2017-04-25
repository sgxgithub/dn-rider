<h5>There are ${appCount} apps</h5>
<ul>
<g:each var="app" in="${apps}">
    <g:link action="showVersions" params="[app: app, releaseType:'All']">
        <li>${app}</li>
    </g:link>
</g:each>
</ul>