<g:if test="${flash.message}">
    <div class="alert alert-danger alert-dismissable fade show">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <pre class="notification">${flash.message}</pre>
    </div>
</g:if>