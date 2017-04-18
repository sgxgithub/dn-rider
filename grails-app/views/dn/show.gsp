<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>
        Search for delivery-notes
    </title>
</head>
<body>

<g:render template="/dn/search"/>

<h4>The result:</h4>
<h5>There are ${size_packages} packages in the dn</h5>

<ul>
    <li>"packages":[</li>
    <g:render template="dnPackage" collection="${packages}" var="dnPackage"/>
    <li>]
    </li>
</ul>

</body>
</html>
