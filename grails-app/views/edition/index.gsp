<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="main"/>
    <title>Edition</title>

    <asset:stylesheet src="edition.css"/>
</head>

<body>

<g:render template="/components/modalSave"/>

<div class="container">
    <g:form action="validateSchema" method="post">
        <div class="my-2">
            <button class="btn btn-outline-primary" type="submit">
                <g:message code="dn.rider.validation.btn.checkSyntax"/>
            </button>
            <g:link action="showSchema" controller="validation" class="btn btn-outline-primary" target="_blank">
                <g:message code="dn.rider.validation.btn.showSchema"/>
            </g:link>
            <a href class="btn btn-outline-primary" data-toggle="modal" data-target="#modalSave">Stocker</a>
        </div>

        <g:render template="/validation/resultPanel"/>

        <div class="dn-upload">
            <label for="deliveryNoteFile">
                <span class="fa fa-paperclip fa-lg"></span>
            </label>
            <input type="file" id="deliveryNoteFile" name="deliveryNoteFile" class="custom-file-input"
                   data-url="${createLink(controller: 'validation', action: 'uploadDn')}"/>
            <g:render template="/components/spinner"/>
        </div>

        <div class="mb-5">
            <textarea name='dn' id="textarea-dn" class="form-control" rows="20" cols="20">${dn}</textarea>
        </div>
    </g:form>
</div>

<a id="back-to-top" href="#" class="btn btn-primary back-to-top"
   title="Click to return on the top" data-toggle="tooltip" data-placement="left"><span
        class="fa fa-angle-double-up"></span>
</a>

<asset:javascript src="edition.js"/>
<asset:javascript src="lib/combobox.js"/>

</body>
</html>
