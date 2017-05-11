<!DOCTYPE html>
<html xmlns:asset="http://www.w3.org/1999/xhtml">
<head>
    <meta name="layout" content="index"/>
    <title>Welcome to DN-RIDER</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <asset:stylesheet src="index.css"/>
</head>
<body>
<content tag="nav">
    <li>
        <a href="https://wiki.vsct.fr/display/KTN/DN-Rider">Wiki</a>
    </li>
    <li>
        <a href="http://gitlab.socrate.vsct.fr/rundep/dn-rider">Gitlab</a>
    </li>
</content>

<div id="content" class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="index-logo">
                <asset:image class="img-rounded" src="dn-rider-logo.gif" width="40%"/>
            </div>
            <g:form url="[action:'searchVersions',controller:'searchVersions']" method="get">
                <div class="form-groupe">
                    <input type="text" class="form-control" name="app" placeholder="trigramme">
                </div>
                <g:submitButton name="Search" class="btn btn-default"/>
            </g:form>
        </div>
    </div>
</div>

</body>
</html>
