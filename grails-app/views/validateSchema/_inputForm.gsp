<g:uploadForm action="uploadDn" class="form-inline">
    <g:message code="dn.rider.dn"/>
    <label class="custom-file mx-2 my-2 ">
        <input type="file" id="file" name="deliveryNoteFile" class="custom-file-input"/>
        <span class="custom-file-control"> </span>
    </label>
    <button class="btn btn-secondary" type="submit">
        <g:message code="dn.rider.btn.upload"/>
    </button>
</g:uploadForm>

<g:form action="validateSchema" method="post">
<div class="form-group row">
    <div class="col-10">
        <textarea name='dn' id="textarea-dn" class="form-control" rows="25" cols="20">${dn}</textarea>
    </div>
    <div class="col-2">
        <button class="btn btn-outline-primary" type="submit">
            <g:message code="dn.rider.validation.btn.checkSyntax"/>
        </button>
        <g:link action="showSchema" class="btn btn-secondary mt-2">
            <g:message code="dn.rider.validation.btn.showSchema"/>
        </g:link>
    </div>
</div>
</g:form>