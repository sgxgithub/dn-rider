<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="main"/>
    <title>Edition</title>

    <asset:stylesheet src="edition.css"/>
</head>

<body>

<g:render template="/components/modalSave"/>

<div class="container-fluid">
    <div class="row" id="row-main">
        <div class="col-3 py-3 bg-faded sidebar collapse show" id="sidebar">
            <g:render template="sidebar"/>
        </div>

        <div class="col-9" id="content">
            <a data-toggle="collapse" href="#sidebar" id="btn-sidebar">
                <i class="fa fa-navicon fa-lg py-3 p-1"></i>
            </a>

            <g:form action="validateSchema" method="post">
                <div class="col-12">
                    <button class="btn btn-outline-primary" type="submit">
                        <g:message code="dn.rider.validation.btn.checkSyntax"/>
                    </button>
                    <g:link action="showSchema" controller="validation" class="btn btn-outline-primary" target="_blank">
                        <g:message code="dn.rider.validation.btn.showSchema"/>
                    </g:link>
                    <a class="btn btn-outline-primary" data-toggle="modal" data-target="#modalSave">Stocker</a>
                </div>

                <div class="form-group row">
                    <g:hiddenField name="offset" value="${offset}"/>
                    <div class="col-12">
                        <textarea name='dn' id="textarea-dn" class="form-control" rows="20" cols="20">${dn}</textarea>
                    </div>
                </div>
            </g:form>

            <g:render template="/validation/resultPanel"/>

        </div>
    </div>
</div>

<a id="back-to-top" href="#" class="btn btn-primary back-to-top"
   title="Click to return on the top" data-toggle="tooltip" data-placement="left"><span
        class="fa fa-angle-double-up"></span>
</a>

<asset:javascript src="edition.js"/>

</body>
</html>
