<h5>
The result:
</h5>

<p>There are ${size_packages} packages in the dn</p>
<ul>
<li>"packages":[</li>
<g:render template="dnPackage" collection="${packages}" var="p"/>
<li>]</li>
</ul>