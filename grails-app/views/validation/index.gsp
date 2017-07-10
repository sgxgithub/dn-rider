<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="main"/>
    <title>DN-RIDER Validation</title>
    <asset:stylesheet src="validation.css"/>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-7">
            <g:render template="inputForm"/>
        </div>
        <div class="col-5">
            <g:render template="resultPanel"/>
        </div>
    </div>
</div>

<asset:javascript src="validation.js"/>

</body>
</html>