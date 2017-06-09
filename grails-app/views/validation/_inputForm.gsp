<form class="form-inline">
    <g:message code="dn.rider.dn"/>
    <label class="custom-file mx-2 my-2 ">
        <input type="file" id="deliveryNoteFile" name="deliveryNoteFile" class="custom-file-input"
               data-url="${createLink(controller: 'validation', action: 'uploadDn')}"/>
        <span class="custom-file-control"></span>
    </label>

    <div id="spinner" class="spinner" style="display:none;">
        <asset:image src="spinner.gif"/>
        <g:message code="spinner.alt"/>
    </div>
</form>

<g:form action="validateSchema" method="post">
    <div class="form-group row">
        <g:hiddenField name="offset" value="${offset}"/>
        <div class="col-9">
            <textarea name='dn' id="textarea-dn" class="form-control" rows="25" cols="20">${dn}</textarea>
        </div>

        <div class="col-3">
            <button class="btn btn-outline-primary" type="submit">
                <g:message code="dn.rider.validation.btn.checkSyntax"/>
            </button>
            <g:link action="showSchema" class="btn btn-outline-primary mt-2">
                <g:message code="dn.rider.validation.btn.showSchema"/>
            </g:link>
        </div>
    </div>
</g:form>