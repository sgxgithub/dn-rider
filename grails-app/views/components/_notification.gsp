<g:if test="${flash.message}">
    <div class="alert alert-info alert-dismissable fade show">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <pre class="notification">${flash.message}</pre>
    </div>
</g:if>

<g:if test="${flash.error}">
    <div class="alert alert-danger alert-dismissable fade show">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <pre class="notification">${flash.error}</pre>
    </div>
</g:if>

<g:if test="${flash.success}">
    <div class="alert alert-success alert-dismissable fade show">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <pre class="notification">${flash.success}</pre>
    </div>
</g:if>