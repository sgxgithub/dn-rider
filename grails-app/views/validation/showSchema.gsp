<!DOCTYPE html>
<html xmlns:asset="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta name="layout" content="main"/>
    <title>
        Validation
    </title>
    <asset:stylesheet src="show-schema.css"/>
</head>

<body>

<div id="content">
    <div class="btn-group nav-link" id="status" data-toggle="buttons">
        <label class="btn btn-secondary btn-on btn-sm active">
            <input type="radio" name="formatShow" value="json" checked>JSON</label>
        <label class="btn btn-secondary btn-off btn-sm ">
            <input type="radio" name="formatShow" value="raw">RAW</label>
    </div>

    <pre id="schema" data-schemajson="${schemajson}" data-schemaraw="${schemaraw}"></pre>
</div>

<asset:javascript src="show-schema.js"/>

</body>
</html>