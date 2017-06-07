<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>
        Search for a liste of delivery-notes
    </title>
    <asset:stylesheet src="search.css"/>
    <asset:stylesheet src="jquery.json-browse.css"/>
</head>

<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-3 py-3 bg-faded sidebar collapse show width" id="sidebar">
            <div id="blockSearch">
                <g:render template="blockSearch"/>
            </div>
        </div>

        <div class="col-9" id="content">
            <a data-toggle="collapse" href="#sidebar">
                <i class="fa fa-navicon fa-lg"></i>
            </a>
            <g:render template="blockDn"/>
        </div>
    </div>
</div>

<asset:javascript src="jquery.json-browse.js"/>
<asset:javascript src="search.js"/>

</body>
</html>