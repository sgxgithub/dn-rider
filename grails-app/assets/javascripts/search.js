//= require lib/jquery.sidebar
//= require lib/jquery.autocomplete-app
//= require_self

(function ($) {

    //autocomplete of apps
    let $app = $("#app");
    let $releaseType = $("#releaseType");
    let $version = $("#version");
    let $regex = $("#regex");
    let $versions = $("#versions");

    $(document).ready(function () {
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-search").addClass("active");
    });

    let setVersions = function () {
        let app = $app.val();
        //fired only if the length >= 3
        if (app.length < 3) return;

        let releaseType = $releaseType.val();
        let regex = $regex.val();

        $.ajax({
            method: "POST",
            url: $app.data('url'),
            data: {app: app, releaseType: releaseType, regex: regex, template: '/search/listVersions'}
        })
            .done(function (result) {
                $versions.html(result);
            });
    };

    const cleanForm = function () {
        $version.val('');
        $regex.val('');
    };

    $app.autocompleteAppAndVersion($version, setVersions);

    //search the list of versions
    // $app.on('input', setVersions);
    $app.on('input', function () {
        setVersions();
        cleanForm();
    });
    $releaseType.on('change', setVersions);
    $regex.on('input', setVersions);

    //sidebar collapse
    $('#sidebar').sidebarCollapse();

    //json format
    //plugin found in jqueryscript.net
    //ref : http://www.jqueryscript.net/demo/Tiny-jQuery-Plugin-For-Pretty-JSON-Print-JSON-Browse/
    let $blockDn = $("#blockDn");
    let dnJson = $blockDn.data('dnjson');
    let dnRaw = $blockDn.data('dnraw');
    if (dnJson) {
        $blockDn.jsonBrowse(dnJson);
    }

    $("#formatJson").click(function (e) {
        e.preventDefault();
        $blockDn.jsonBrowse(dnJson);
    });
    $("#formatRaw").click(function (e) {
        e.preventDefault();
        $blockDn.html(dnRaw);
    });

}(jQuery));