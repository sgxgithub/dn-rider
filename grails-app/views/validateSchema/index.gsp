<!DOCTYPE html>
<html xmlns:asset="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta name="layout" content="main"/>
    <title>
        Search for delivery-notes
    </title>
    <asset:stylesheet src="validation.css"/>
    <asset:javascript src="jquery-2.2.0.min.js"/>
    <!--<g:javascript>-->
        <!--$( document ).ready(function() {-->
            <!--document.getElementsByTagName('textarea')[2].innerText="Hello ${content}";-->
        <!--});-->
    <!--</g:javascript>-->
</head>
<body>
<!--Upload Form:<br/>-->
<!--<g:uploadForm action="validation" method="post" enctype="multipart/form-data" >-->
<!--<input type="file" name="fDn"/>-->
<!--<input type="submit"/>-->
<!--</g:uploadForm>-->

<div class="container">
    <div class="row">
        <div class="col-md-4">
            <g:render template="/validateSchema/inputForm"/>
        </div>
        <div class="col-md-4">
            <g:render template="/validateSchema/resultPanel"/>
        </div>
        <div class="col-md-4">
            <pre>hello ! js fonctionne pas</pre>
            <div>
                ${content}
            </div>
        </div>
    </div>
</div>

</body>
</html>