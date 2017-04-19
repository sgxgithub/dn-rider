<g:form class="form-inline" action="show" method="get" style="margin: 50px auto; width:700px"
        xmlns:g="http://www.w3.org/1999/xhtml">
<h2 class="form-signin-heading">Search for a delivery-note</h2>
<div class="form-group">
    <label for="app">APP</label>
    <g:textField name="app" class="form-control" value="${app}" placeholder="ccl, kli..."/>
</div>
<div class="form-group">
    <label for="version">Version</label>
    <g:textField name="version" class="form-control" value="${version}" placeholder="52.00.0-2, 1.36.1..."/>
</div>
    <g:submitButton name="Search DN" class="btn btn-default"/>
</g:form>

<g:form class="form-inline" action="showList" method="get" style="margin: 50px auto; width:700px">
<h2 class="form-signin-heading">Search for a list of delivery-notes</h2>
<div class="form-group">
    <label for="app">APP</label>
    <g:textField name="app" class="form-control" value="${app}" placeholder="ccl, kli..."/>
</div>
<div class="form-group">
    <label for="releaseType">Release Type</label>*
    <g:select name="releaseType" class="form-control" from='["All", "Snapshots","Releases"]' value="${releaseType}"/>
</div>
<g:submitButton name="Search DN List" class="btn btn-default"/>
</g:form>