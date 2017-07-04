<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="main"/>
    <title>Edition</title>

    <asset:stylesheet src="edition.css"/>
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
        </div>
    </div>
</div>

<a id="back-to-top" href="#" class="btn btn-primary back-to-top"
   title="Click to return on the top" data-toggle="tooltip" data-placement="left"><span
        class="fa fa-angle-double-up"></span>
</a>

<asset:javascript src="lib/sidebar.js"/>
<asset:javascript src="edition.js"/>

</body>
</html>
