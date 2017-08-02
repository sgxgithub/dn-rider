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
            <div id="validationResult"></div>
            <g:render template="content"/>
        </div>
    </div>
</div>

<a id="back-to-top" href="#" class="btn btn-primary back-to-top"
   title="Click to return on the top" data-toggle="tooltip" data-placement="left"><span
        class="fa fa-angle-double-up"></span>
</a>

<asset:javascript src="search.js"/>

</body>
</html>