<form id="formCompare">
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
                    <g:message code="dn.rider.sidebar.filter"/>
                </h5>
            </div>

            <div class="col-8">
                <g:textField name="regex" class="form-control" value="${regex}" placeholder="regex"/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-7">
                <g:render template="/components/spinner"/>
            </div>

            <div class="col-md-3">
                <button class="btn btn-outline-primary disabled" id="btnCompare">
                    <g:message code="dn.rider.btn.compare"/>
                </button>
            </div>
        </div>
    </div>

    <div id="versions">
        <g:render template="listVersions"/>
    </div>
</form>