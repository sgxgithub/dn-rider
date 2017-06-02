<form class="my-4">
    <h4>
        <g:message code="dn.rider.validation.result.title"/>
    </h4>
    <g:if test="${isChecked && !isJsonValid}">
        <div class="alert alert-danger">
            <strong><g:message code="dn.rider.validation.msg.invalidJson"/></strong> <a id="json-error-link" href="#">line: ${line}</a>
        </div>
        <textarea class="form-control" rows="3">${message}</textarea>
    </g:if>
    <g:if test="${isChecked && isJsonValid}">
        <g:if test="${!isSchemaValid}">
            <div class="alert alert-warning">
                <strong><g:message code="dn.rider.validation.msg.invalidSchema"/></strong>
            </div>
            <pre class="form-control">${raw(content)}</pre>
        </g:if>
        <g:else>
            <div class="alert alert-success">
                <strong>
                    <g:message code="dn.rider.validation.msg.success"/>
                </strong>
            </div>
        </g:else>
    </g:if>
</form>