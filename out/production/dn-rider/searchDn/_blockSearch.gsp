<g:form url="[action:'index',controller:'searchDn']" method="get">
    <div class="form-group row">
        <div class="col-4">
            <h5>APP</h5>
        </div>
        <div class="col-8">
            <g:textField name="app" class="form-control" value="${app}" placeholder="ccl, kli..."/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-4">
            <h5>ReleaseType</h5>
        </div>
        <div class="col-8">
            <g:select name="releaseType" class="form-control" from='["All", "Snapshots","Releases"]' value="${releaseType}"/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-4">
            <h5>Version</h5>
        </div>
        <div class="col-8">
            <g:textField name="version" class="form-control" value="${version}" placeholder="52.00.0-2, 1.36.1..."/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-4">
            <h5>Format</h5>
        </div>
        <div class="col-8">
            <g:select name="formatShow" class="form-control" from='["Text", "JSON"]' value="${formatShow}"/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-3 offset-8">
            <button class="btn btn-outline-primary" type="submit">Search</button>
        </div>
    </div>

</g:form>