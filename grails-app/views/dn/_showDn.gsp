<g:form class="form-inline" action="show" method="get" style="margin: 50px auto; width:800px"
        xmlns:g="http://www.w3.org/1999/xhtml">
    <h2 class="form-signin-heading">Select the format</h2>
    <div class="form-group">
        <label for="formatShow">Format</label>
        <g:select name="formatShow" class="form-control" from='["JSON","Text"]' value="${format}"/>
    </div>
    <g:submitButton name="Search DN" class="btn btn-default"/>
</g:form>

<g:if test="${formatShow == 'JSON'}">
    <h4>The result:</h4>
    <h5>There are ${size_packages} packages in the dn</h5>

    <ul>
        <li>"packages":[</li>
        <g:render template="dnPackage" collection="${packages}" var="dnPackage"/>
        <li>]
        </li>
    </ul>
</g:if>
<g:else>
<p>${dnText}</p>
</g:else>