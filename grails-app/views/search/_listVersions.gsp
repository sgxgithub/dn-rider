<div class="li-group">
    <h5>${versionCount}
    <g:if test="${versionCount < 2}">
        <g:message code="dn.rider.sidebar.result"/>
    </g:if>
    <g:else>
        <g:message code="dn.rider.sidebar.results"/>
    </g:else>
    </h5>
    <ul>
        <g:each var="version" in="${versions}">
            <g:link action="search" controller="search"
                    params="[app: app, version: version, releaseType: releaseType, formatShow: formatShow]">
                <li>${version}</li>
            </g:link>
        </g:each>
    </ul>
</div>
