<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="index"/>
    <title>Comparison</title>
    <asset:stylesheet src="comparison.css"/></head>

<body>

<g:render template="/components/notification"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-3 py-3 bg-faded collapse show width" id="sidebar">
            <g:render template="blockSearch"/>
        </div>

        <div class="col-9">
            <a data-toggle="collapse" href="#sidebar">
                <i class="fa fa-navicon fa-lg"></i>
            </a>

            <div id="tableComparison">
                <g:render template="tableComparison"/>
            </div>
        </div>
    </div>
</div>

<asset:javascript src="comparison.js"/>

</body>
</html>
