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
    <nav class="nav">
        <a class="nav-link" href="" id="formatJson">JSON</a>
        <a class="nav-link" href="" id="formatRaw">RAW</a>
    </nav>

    <pre id="schema" data-schemajson="${schemajson}" data-schemaraw="${schemaraw}"></pre>
</div>

<asset:javascript src="show-schema.js"/>

</body>
</html>