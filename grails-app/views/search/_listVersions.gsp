<g:if test="${versionCount}">
    <h5>${versionCount}
    <g:message code="dn.rider.sidebar.results"/>
    </h5>
    <ul>
        <g:each var="version" in="${versions}">
            <g:link action="search" controller="search"
                    params="[app: app, version: version, releaseType: releaseType, formatShow: formatShow]">
                <li>${version}</li>
            </g:link>
        </g:each>
    </ul>
</g:if>
