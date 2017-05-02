<!DOCTYPE html>
<html xmlns:asset="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta name="layout" content="main"/>
    <title>
        Search for delivery-notes
    </title>
    <asset:stylesheet src="validation.css"/>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <g:render template="/validateSchema/inputForm"/>
        </div>
        <div class="col-md-6">
            <g:render template="/validateSchema/resultPanel"/>
        </div>
    </div>
</div>

</body>
</html>