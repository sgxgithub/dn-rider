<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>
        Search for delivery-notes
    </title>
    <asset:stylesheet src="validation.css"/>
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
            <g:render template="/dn/inputForm"/>
        </div>
        <div class="col-md-4">
            <g:render template="/dn/resultPanel"/>
        </div>
        <div class="col-md-4">
            <pre>${content}</pre>
        </div>
    </div>
</div>

</body>
</html>
