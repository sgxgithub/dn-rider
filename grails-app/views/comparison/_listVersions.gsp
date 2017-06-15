<g:if test="${versions}">
    <div class="cb-group">
        <div class="alert alert-info alert-dismissable fade show px-3 py-2 m-0">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            Use shift to select/clear a range.
        </div>
    <g:each var="version" in="${versions}">
        <div class="form-check">
            <label class="form-check-label">
                <input class="checkbox-version form-check-input" type="checkbox" name="versions"
                       value="${version}"> ${version}</label>
        </div>
    </g:each>
    </div>
</g:if>