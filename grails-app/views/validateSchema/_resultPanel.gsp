<g:form>
    <label>Validation results:</label>
    <g:if test="${!invalidJson}">
    <g:if test="${!invalidDn}">
        <span class="error">failure</span>
    </g:if>
    <g:else >
        <span class="success">success</span>
    </g:else>
    </g:if>
    <pre>${raw(content)}</pre>
</g:form>