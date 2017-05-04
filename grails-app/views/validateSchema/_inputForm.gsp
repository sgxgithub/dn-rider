<g:uploadForm action="uploadDn" class="form-inline">
    DN:
    <label class="custom-file mx-2 my-2 ">
        <input type="file" id="file" name="deliveryNoteFile" class="custom-file-input"/>
        <span class="custom-file-control"> </span>
    </label>
    <g:submitButton name="Upload" class="btn btn-secondary"/>
</g:uploadForm>

<g:form action="validateSchema" method="post">
<div class="form-group row">
    <div class="col-10">
        <textarea name='dn' id="textarea-dn" class="form-control" rows="25" cols="20">${dn}</textarea>
    </div>
    <div class="col-2">
        <g:submitButton name="Check Syntax" class="btn btn-primary"/><br/>
        <g:link action="showSchema" class="btn btn-secondary mt-2">Show Schema</g:link>
    </div>
</div>
</g:form>