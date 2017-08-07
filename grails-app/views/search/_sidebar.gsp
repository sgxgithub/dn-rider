<g:form url="[action: 'search', controller: 'search']" method="get">
    <div id="fixForm">
        <div class="form-group row">
            <div class="col-4">
                <h5>
                    <g:message code="dn.rider.sidebar.app"/>
                </h5>
            </div>

            <div class="col-8">
                <g:textField name="app" class="form-control" value="${app}" placeholder="ccl, kli..."
                             data-url="${createLink(controller: 'search', action: 'getVersionsView')}"
                             data-apps="${apps}"
                             autocomplete="off" required="true" autofocus="true"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-4">
                <h5>
                    <g:message code="dn.rider.sidebar.releaseType"/>
                </h5>
            </div>

            <div class="col-8">
                <g:select name="releaseType" class="form-control" from='["All", "Snapshots", "Releases"]'
                          value="${releaseType}"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-4">
                <h5>
                    <g:message code="dn.rider.sidebar.version"/>
                </h5>
            </div>

            <div class="col-8">
                <g:textField name="version" class="form-control" value="${version}" placeholder="52.00.0-2, 1.36.1..."
                             autocomplete="off"
                             data-url="${createLink(controller: 'search', action: 'getVersionsList', params: [releaseType: 'All'])}"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-4">
                <h5>
                    <g:message code="dn.rider.sidebar.filter"/>
                </h5>
            </div>

            <div class="col-8">
                <g:textField name="regex" class="form-control" value="${regex}" placeholder="regex"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-7">
                <g:render template="/components/spinner"/>
            </div>

            <div class="col-3 offset-1">
                <button class="btn btn-outline-primary" type="submit">
                    <g:message code="dn.rider.btn.search"/>
                </button>
            </div>
        </div>
    </div>

    <div id="versions">
        <g:render template="listVersions"/>
    </div>
</g:form>