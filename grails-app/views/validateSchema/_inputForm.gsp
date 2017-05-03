<g:form action="showSchema" class="form-inline">
    <label>Schema:</label>
    <g:submitButton name="Show Schema" class="btn btn-default"/>
</g:form>

<g:form action="validateSchema" method="post">
<div class="col-md-9">
    <div class="form-group">
        <label for="textarea-dn">
            DN:
            <g:if test="${isChecked && !isJsonValid}">
                <span class="error" >Invalid JSON: parse error, <a id="json-error-link" href="#">line ${line}</a></span>
            </g:if>
        </label>
        <textarea name='dn' id="textarea-dn" class="form-control" rows="10" cols="20">${dn}</textarea>
    </div>
</div>
<div class="col-md-3">
    <g:submitButton name="Check Syntax" class="btn btn-primary"/>
</div>
</g:form>

<g:uploadForm action="uploadDn" class="form-inline">
<div class="form-group">
    <input type="file" class="form-control" name="deliveryNoteFile"/>
    <g:submitButton name="Upload" class="btn btn-default"/>
</div>
</g:uploadForm>