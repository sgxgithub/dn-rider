<g:form url="[action:'searchVersions',controller:'searchVersions']" method="get">
    <div class="row">
        <div class="col-3">
            <h5>APP:</h5>
        </div>
        <div class="col-9">
            <g:textField name="app" class="form-control" value="${app}" placeholder="ccl, kli..."/>
        </div>
    </div>
    <div class="row">
        <div class="col-3">
            <h5>Version</h5>
        </div>
        <div class="col-9">
            <g:textField name="version" class="form-control" value="${version}" placeholder="52.00.0-2, 1.36.1..."/>
        </div>
    </div>
    <button class="btn btn-outline-primary" type="submit">Search</button>
</g:form>