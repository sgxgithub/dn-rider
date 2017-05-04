<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="index"/>
    <title>Welcome to DN-RIDER</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <asset:stylesheet src="index.css"/>
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

<div id="content" class="container">
    <div class="row justify-content-center">
        <div class="col-6">
            <asset:image class="rounded mx-auto d-block my-5" src="dn-rider-logo.gif" width="40%"/>
            <g:form url="[action:'searchVersions',controller:'searchVersions']" method="get">
                <div class="row">
                    <div class="col-10">
                        <input class="form-control" name="app" type="text" placeholder="trigramme"/>
                    </div>
                    <div class="col-2">
                        <button class="btn btn-outline-primary" type="submit">Search</button>
                    </div>
                </div>
            </g:form>
        </div>
    </div>
</div>

</body>
</html>
