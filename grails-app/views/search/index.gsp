<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>
        Search for a liste of delivery-notes
    </title>
    <asset:stylesheet src="search.css"/>
</head>

<body>

<div class="container-fluid">
    <div class="row" id="row-main">
        <div class="col-3 py-3 bg-faded sidebar collapse show width" id="sidebar">
            <g:render template="sidebar"/>
        </div>

        <div class="col-9" id="content">
            <a data-toggle="collapse" href="#sidebar">
                <i class="fa fa-navicon fa-lg"></i>
            </a>
        </div>
    </div>
</div>

<asset:javascript src="search.js"/>

</body>
</html>