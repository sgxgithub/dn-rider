<form class="my-4">
    <h5>Validation results:</h5>
    <g:if test="${isChecked && !isJsonValid}">
        <div class="alert alert-danger">
            <strong>Invalid JSON:</strong> parse error in <a id="json-error-link" href="#">line: ${line}</a>
        </div>
        <textarea class="form-control" rows="3">${message}</textarea>
    </g:if>
    <g:if test="${isChecked && isJsonValid}">
        <g:if test="${!isSchemaValid}">
            <div class="alert alert-warning">
                <strong>Invalid Schema</strong>
            </div>
            <pre class="form-control">${raw(content)}</pre>
        </g:if>
        <g:else>
            <div class="alert alert-success">
                <strong>Success</strong>
            </div>
        </g:else>
    </g:if>
</form>