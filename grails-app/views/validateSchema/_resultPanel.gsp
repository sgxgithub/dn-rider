<g:form>
    <div class="form-groupe">
        <label for="results">Validation results:</label>
        <g:if test="${!valid}">
            <span class="error">failure</span>
        </g:if>
        <g:else>
            <span class="success">success</span>
        </g:else>
    </div>
    <textarea name="results" class="form-control" rows="20" cols="20" id="results" readonly="readonly"> ${content}</textarea>
</g:form>