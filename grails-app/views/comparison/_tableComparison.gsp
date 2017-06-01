<table class="table table-hover table-responsive">
    <thead>
    <tr>
        <g:if test="${rowVersions}">
            <th>module</th>
            <th>name</th>
            <g:each var="rowVersion" in="${rowVersions}">
                <th>
                    <a href="${rowVersion.url}" target="_blank">${rowVersion.name}</a>
                </th>
            </g:each>
        </g:if>
    </tr>
    </thead>
    <tbody>
    <g:if test="${rowPackages}">
        <g:each status="i" var="rowPackage" in="${rowPackages}">
            <tr>
                <th>${rowPackage.key.module}</th>
                <th>${rowPackage.key.name}</th>
                <g:if test="${rowVersions}">
                    <g:each var="version" in="${rowVersions.name}">
                        <g:if test="${rowPackage[version]?.tag == 'deleted'}">
                            <td>
                                <span class="badge badge-danger">Deleted</span>
                            </td>
                        </g:if>
                        <g:else>
                            <td>
                                <g:if test="${rowPackage[version]?.tag == 'new'}">
                                    <span class="badge badge-success">N</span>
                                </g:if>
                                <g:if test="${rowPackage[version]?.tag == 'changed'}">
                                    <span class="badge badge-info">C</span>
                                </g:if>
                                <g:if test="${rowPackage[version]?.packageUrl}">
                                    <a href="${rowPackage[version]?.packageUrl}">${rowPackage[version]?.name}</a>
                                </g:if>
                                <g:else>
                                    ${rowPackage[version]?.name}
                                </g:else>
                            </td>
                        </g:else>
                    </g:each>
                </g:if>
            </tr>
        </g:each>
    </g:if>
    </tbody>
</table>