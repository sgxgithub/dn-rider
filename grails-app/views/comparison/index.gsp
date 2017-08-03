<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="main"/>
    <title>Comparison</title>

    <asset:stylesheet src="comparison.css"/>

</head>

<body>

<div class="container-fluid">
    <div class="row" id="row-main">
        <div class="col-3 py-3 bg-faded sidebar collapse show" id="sidebar">
            <g:render template="sidebar"/>
        </div>

        <div class="col-9" id="content">
            <a data-toggle="collapse" href="#sidebar" id="btn-sidebar">
                <i class="fa fa-navicon fa-lg py-3 p-1"></i>
            </a>

            <div id="tableComparison">
                <g:render template="tableComparison"/>
            </div>
        </div>
    </div>
</div>

<g:render template="/components/backToTop"/>

<asset:javascript src="comparison.js"/>

</body>
</html>
