<g:form action="validateSchema" method="post">
<div class="form-group">
    <label for="schema">
        Schema: <span class="error starthidden" id="input-invalid">Invalid JSON: parse error, <a id="input-link" href="#"> </a></span>
    </label>
    <textarea name='schema' class="form-control" rows="10" cols="20" >${schema}</textarea>
</div>
<div class="form-group">
    <label for="dn">
        DN: <span class="error starthidden" id="input2-invalid">Invalid JSON: parse error, <a id="input2-link" href="#"> </a></span>
    </label>
    <textarea name='dn' class="form-control" rows="10" cols="20" >${dn}</textarea>
</div>
<g:submitButton name="Check Syntax" class="btn btn-default"/>
</g:form>