<div class="my-4">
    <g:if test="${isChecked && !isJsonValid}">
        <div class="alert alert-danger alert-dismissable fade show">
            <h5>
                <g:message code="dn.rider.validation.result.title"/>
            </h5>
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong><g:message code="dn.rider.validation.msg.invalidJson"/></strong> <a id="json-error-link"
                                                                                        href="#">line: ${line}</a>
            <pre>${message}</pre>
        </div>
    </g:if>
    <g:if test="${isChecked && isJsonValid}">
        <g:if test="${!isSchemaValid}">
            <div class="alert alert-warning alert-dismissable fade show">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <h5>
                    <g:message code="dn.rider.validation.result.title"/>
                </h5>
                <strong><g:message code="dn.rider.validation.msg.invalidSchema"/></strong>
                <pre>${raw(content)}</pre>
            </div>
        </g:if>
        <g:else>
            <div class="alert alert-success alert-dismissable fade show">
                <strong>
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <h5>
                        <g:message code="dn.rider.validation.result.title"/>
                    </h5>
                    <g:message code="dn.rider.validation.msg.success"/>
                </strong>
            </div>
        </g:else>
    </g:if>
</div>