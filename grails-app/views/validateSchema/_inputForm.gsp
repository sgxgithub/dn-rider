<g:form action="showSchema" class="form-inline">
    <label>Schema:</label>
    <g:submitButton name="Show Schema" class="btn btn-default"/>
</g:form>

<g:form action="validateSchema" method="post">
<div class="col-md-9">
    <div class="form-group">
        <label for="dn">
            DN:
            <g:if test="${isChecked && !isJsonValid}">
                <span class="error" id="input2-invalid">Invalid JSON: parse error, <a id="input2-link" href="#">line:${line},offset:${offset}</a></span>
            </g:if>
        </label>
        <textarea name='dn' id="dn" class="form-control" rows="10" cols="20">${dn}</textarea>
    </div>
</div>
<div class="col-md-3">
    <g:submitButton name="Check Syntax" class="btn btn-default"/>
</div>
</g:form>

<g:uploadForm action="uploadDn" class="form-inline">
<input type="file" class="form-control" name="deliveryNoteFile"/>
<g:submitButton name="Upload DN" class="btn btn-default"/>
</g:uploadForm>