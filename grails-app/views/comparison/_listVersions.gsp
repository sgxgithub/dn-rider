<g:if test="${versions}">
    <div class="alert alert-info alert-dismissable fade show px-2 py-1">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        Use shift to select/clear a range.
    </div>
</g:if>
<g:each var="version" in="${versions}">
    <div class="form-check">
        <label class="form-check-label">
            <input class="checkbox-version form-check-input" type="checkbox" name="versions"
                   value="${version}"> ${version}</label>
    </div>
</g:each>