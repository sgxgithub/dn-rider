<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <g:form action="show" method="GET" style="margin: 100px auto; width:320px">
        <g:textField name="app" value="">APP</g:textField>
        <g:textField name="version" value="">Version></g:textField>
        <g:submitButton name="Search DN" />
    </g:form>
    </body>
</html>