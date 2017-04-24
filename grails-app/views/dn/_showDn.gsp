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
<pre>${dnText}</pre>
</g:else>