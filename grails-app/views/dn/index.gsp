<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>
        Search a delivery-notes
    </title>
</head>
<body>

<g:form class="form-inline" action="show" method="get" style="margin: 50px auto; width:600px">
    <div class="form-group">
        <label for="app">APP</label>
        <g:textField name="app" class="form-control" value="" placeholder="ccl, kli..."/>
    </div>
    <div class="form-group">
        <label for="version">Version</label>
        <g:textField name="version" class="form-control" value="" placeholder="52.00.0-2, 1.36.1..."/>
    </div>
    <g:submitButton name="Search DN" class="btn btn-default"/>
</g:form>

</body>
</html>