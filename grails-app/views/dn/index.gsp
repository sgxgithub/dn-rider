<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <g:form action="show" method="get" style="margin: 50px auto; width:500px">
        APP<g:textField name="app" value=""/><br/>
        Version<g:textField name="version" value=""/>
        <g:submitButton name="Search DN" />
    </g:form>


    <form class="form-inline" action="/dn/show" method="get" style="margin: 50px auto; width:600px">
        <div class="form-group">
            <label for="app">APP</label>
            <input type="text" class="form-control" id="app" placeholder="ccl, gel...">
        </div>
        <div class="form-group">
            <label for="version">Version</label>
            <input type="text" class="form-control" id="version" placeholder="52.00.0-2...">
        </div>
        <button type="submit" class="btn btn-default">Search</button>
    </form>

    </body>
</html>