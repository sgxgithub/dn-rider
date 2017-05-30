<form id="formCompare">
    <div class="form-group row">
        <div class="col-4">
            <h5>APP</h5>
        </div>

        <div class="col-8">
            <g:textField name="app" class="form-control" value="${app}" placeholder="ccl, kli..." data-apps="${apps}" autocomplete="off"/>
        </div>
    </div>

    <div class="form-group row">
        <div class="col-4">
            <h5>ReleaseType</h5>
        </div>

        <div class="col-8">
            <g:select name="releaseType" class="form-control" from='["All", "Snapshots", "Releases"]'
                      value="${releaseType}"/>
        </div>
    </div>

    <div class="row">
        <div class="col-3">
            <div class="btn btn-outline-primary" id="btnSearch"
                 data-url="${createLink(controller: 'search', action: 'getVersionsView')}">Search</div>
        </div>

        <div class="col-3 offset-5">
            <button class="btn btn-outline-primary" id="btnCompare">Compare</button>
        </div>
    </div>

    <div id="versions">
        <g:render template="listVersions"/>
    </div>
</form>