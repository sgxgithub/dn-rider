<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="index"/>
    <title>DN-RIDER</title>
    <asset:stylesheet src="home.css"/>
</head>

<body>
<content tag="nav">
    <li>
        <g:link class="nav-link" controller="validation" action="index">Validation</g:link>
    </li>
</content>

<g:render template="/components/notification"/>

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
                <g:each var="appQuickAccess" in="${appsQuickAccess}">
                    <g:link class="btn btn-outline-primary btn-lg" controller="home" action="search"
                            params="[app:appQuickAccess]">
                        ${appQuickAccess}
                    </g:link>
                </g:each>
            </div>

        </div>
    </div>
</div>

<asset:javascript src="home.js"/>
<asset:javascript src="settings.js"/>

</body>
</html>
