<!DOCTYPE html>
<html xmlns:asset="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta name="layout" content="index"/>
    <title>
        Search for delivery-notes
    </title>
    <asset:javascript src="jquery-2.2.0.min.js"/>
    <asset:stylesheet src="validation.css"/>

    <g:javascript>
        $(document).ready(function(){
            $("#json-error-link").click(function(){
                $("#textarea-dn").focus().setCursorPosition(${offset});
            });
        $('input:file').change(
            function(e){
                $(".custom-file-control").addClass('changed').attr("data-content",e.target.files[0].name);
                //$("#fileName").val(e.target.files[0].name); //à tester
            });
        });
    </g:javascript>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-7">
            <g:render template="/validateSchema/inputForm"/>
        </div>
        <div class="col-5">
            <g:render template="/validateSchema/resultPanel"/>
        </div>
    </div>
</div>

</body>
</html>