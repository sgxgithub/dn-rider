<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="index"/>
    <title>DN-RIDER</title>
</head>
<body>
<content tag="nav">
    <li>
        <g:link class="nav-link" controller="validateSchema" action="index">Validation</g:link>
    </li>
</content>

<g:render template="/components/notification"/>

<div id="content" class="container">
    <div class="row justify-content-center">
        <div class="col-6">
            <asset:image class="rounded mx-auto d-block my-5" src="dn-rider-logo.gif" width="40%"/>
            <g:form url="[action:'index',controller:'searchDn']" method="get">
                <div class="row">
                    <div class="col-10">
                        <input class="form-control" id="trigramme" autocomplete="off" name="app" type="text"
                               placeholder="trigramme"/>
                    </div>
                    <div class="col-2">
                        <button class="btn btn-outline-primary" type="submit">
                            <g:message code="dn.rider.btn.search"/>
                        </button>
                    </div>
                </div>
            </g:form>
        </div>
    </div>
</div>

</body>
</html>
