<g:if test="${versions}">
    <div class="cb-group" id="cb-group-versions" data-versionsselected= ${versionsSelected}>
        <div class="alert alert-info alert-dismissable fade show px-3 py-2 m-0">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <g:message code="dn.rider.sidebar.info.shift"/>
        </div>
        <g:each var="version" in="${versions}">
            <div class="form-check">
                <label class="form-check-label">
                    <input class="checkbox-version form-check-input" type="checkbox" name="versions"
                           value="${version}"> ${version}</label>
            </div>
        </g:each>
    </div>
</g:if>
<g:else>
    <h5 class="m-3">
        <g:message code="dn.rider.sidebar.noResult"/>
    </h5>
</g:else>