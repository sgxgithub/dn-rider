<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="index"/>
    <title>
        Search for a liste of delivery-notes
    </title>
    <asset:stylesheet src="search.css"/>
</head>

<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-3 py-3 bg-faded collapse show width" id="sidebar">
            <div id="blockSearch">
                <g:render template="blockSearch"/>
            </div>

            <div id="blockVersions">
                <g:render template="blockVersions"/>
            </div>
        </div>

        <div id="blockDn" class="col-9">
            <a data-toggle="collapse" href="#sidebar">
                <i class="fa fa-navicon fa-lg"></i>
            </a>
            <g:render template="/components/notification"/>
            <g:render template="blockDn"/>
        </div>
    </div>
</div>

<asset:javascript src="search.js"/>

</body>
</html>