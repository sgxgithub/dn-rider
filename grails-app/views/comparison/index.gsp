<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="layout" content="index"/>
    <title>Comparison</title>
</head>

<body>

<g:render template="/components/notification"/>

<div id="content" class="container">
    <div class="row justify-content-center">
        <div class="col-6">
            <asset:image class="rounded mx-auto d-block my-5" src="dn-rider-logo-bgremoved.gif" width="40%"/>
        </div>
    </div>

    <table class="table table-hover">
        <thead>
        <tr>
            <th>#</th>
            <th>package 1</th>
            <th>package 2</th>
            <th>package 3</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">v1</th>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
        </tr>
        <tr>
            <th scope="row">v2</th>
            <td colspan="2">Larry the Bird</td>
            <td>@twitter</td>
        </tr>
        </tbody>
    </table>
</div>

<asset:javascript src="comparison.js"/>

</body>
</html>
