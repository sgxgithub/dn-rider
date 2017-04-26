<g:form action="validateSchema" method="post">
    <div id="left" class="content">
        <div class="horiz">
            <label for="schema">Schema:</label>
            <span class="error starthidden" id="input-invalid">Invalid JSON: parse error, <a id="input-link" href="#"> </a></span>
        </div>
        <textarea name="schema" rows="20" cols="20" class="half" id="schema"> ${schema}</textarea>
        <div class="horiz">
            <label for="dn">DN:</label>
            <span class="error starthidden" id="input2-invalid">Invalid JSON:
            parse error, <a id="input2-link" href="#"> </a></span>
        </div>
        <textarea name="dn" rows="20" cols="20" class="half" id="dn">${dn}</textarea>
        <div class="horiz">
            <g:submitButton name="Check Syntax" class="btn btn-default"/>
            <span>(<a id="load" href="#">load sample data</a>)</span>
        </div>
    </div>
</g:form>