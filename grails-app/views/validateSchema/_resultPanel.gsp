<g:form>
    <label>Validation results:</label>
    <g:if test="${isChecked && isJsonValid}">
        <g:if test="${!isSchemaValid}">
            <span class="error">failure</span>
        </g:if>
        <g:else>
            <span class="success">success</span>
        </g:else>
    </g:if>
    <pre>${raw(content)}</pre>
</g:form>