<g:form action="validateSchema" method="post">
<div class="form-group">
    <label for="schema">Schema:</label>
    <textarea name='schema' class="form-control" rows="10" cols="20">${schema}</textarea>
</div>
<div class="form-group">
    <label for="dn">
        DN:
        <g:if test="${invalidJson}">
            <span class="error" id="input2-invalid">Invalid JSON: parse error, <a id="input2-link" href="#">line:${line},offset:${offset}</a></span>
        </g:if>
    </label>
    <textarea name='dn' class="form-control" rows="10" cols="20">${dn}</textarea>
</div>
<g:submitButton name="Check Syntax" class="btn btn-default"/>
</g:form>