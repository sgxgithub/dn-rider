<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="main"/>
    <title>DN-RIDER</title>
    <asset:stylesheet src="home.scss"/>
</head>

<body>
<content tag="nav">
    <g:render template="/components/modalPrefence"/>
</content>

<div id="content" class="container">
    <div class="row justify-content-center">
        <div class="col-6">
            <asset:image class="rounded mx-auto d-block my-5" src="dn-rider-logo-bgremoved.gif" width="40%"/>
            <g:form class="my-5" controller="home" action="search" method="get">
                <div class="row">
                    <div class="col-10">
                        <g:textField class="form-control" autocomplete="off" name="app"
                                     placeholder="trigramme" value="${app}" data-apps="${apps}"/>
                    </div>

                    <div class="col-2">
                        <g:submitButton class="btn btn-outline-primary" type="submit" name="submit"
                                        value="${g.message([code: 'dn.rider.btn.search'])}"/>
                    </div>
                </div>
            </g:form>

            <div class="d-flex justify-content-around mt-5">
                <g:each var="appQuickAccess" in="${appsQuickAccessArray}">
                    <g:link class="btn btn-outline-primary btn-lg" controller="search" action="search"
                            params="[app:appQuickAccess]">
                        ${appQuickAccess}
                    </g:link>
                </g:each>
            </div>

        </div>
    </div>
</div>

<footer>
    <ul class="nav justify-content-center my-1">
        <li class="nav-item">
            <a class="nav-link" href="https://wiki.vsct.fr/display/KTN/DN-Rider">Wiki</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="http://gitlab.socrate.vsct.fr/rundep/dn-rider">Gitlab</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="http://swagger.io/">Swagger</a>
        </li>
    </ul>
</footer>

<asset:javascript src="home.js"/>
<asset:javascript src="settings.js"/>

</body>
</html>
