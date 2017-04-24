<h4>The result:</h4>
<h5>There are ${sizeApps} apps</h5>
<ul>
<g:each var="app" in="${listApps}">
    <g:link action="showList" params="[app: app, version: version, releaseType:releaseType]">
        <li>App:${app}</li>
    </g:link>
</g:each>
</ul>