<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="index"/>
    <title>
        Search for a liste of delivery-notes
    </title>
</head>
<body>

<content tag="nav">
    <li class="nav-item">
        <a class="nav-link" href="https://wiki.vsct.fr/display/KTN/DN-Rider">Wiki</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" href="http://gitlab.socrate.vsct.fr/rundep/dn-rider">Gitlab</a>
    </li>
</content>

<div class="container-fluid">
    <div class="row">
        <div class="col-3 bg-faded" style="max-height:720px; overflow:scroll">
            <g:render template="/components/columeVersions"/>
        </div>
        <div class="col-9" style="max-height:720px; overflow:scroll">
            <g:render template="/components/columeDn"/>
        </div>
    </div>
</div>

</body>
</html>