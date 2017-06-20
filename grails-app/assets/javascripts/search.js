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

        setHeight();
    });

    $(window).resize(function () {
        setHeight();
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
                setHeight();
            });
    };

    const cleanForm = function () {
        $version.val('');
        $regex.val('');
    };

    let setHeight = function () {
        $("#row-main").outerHeight($(window).height() - 56);
        $("#sidebar").outerHeight($(window).height() - 56);
        $versions.outerHeight($("#sidebar").outerHeight(true) - $("#fixForm").outerHeight(true) - 16);
        $("#versions ul").outerHeight($versions.outerHeight(true) - 30);
        $("#content").outerHeight($(window).height() - 56);
    };


    let apps = $app.data('apps');
    $app.autocomplete({
        source: function (request, response) {
            let results = $.ui.autocomplete.filter(apps, request.term);
            response(results.slice(0, 10));
        },
        select: function (event, ui) {
            //set the input value from selected item
            $app.val(ui.item.value);
            setVersions();

            // add data-versions when select a version
            $.ajax({
                method: "GET",
                url: $version.data('url') + '&app=' + ui.item.value
            })
                .done(function (versions) {
                    $version.autocomplete({
                        source: function (request, response) {
                            let results = $.ui.autocomplete.filter(versions, request.term);
                            response(results.slice(0, 10));
                        }
                    });
                });
        }
    });

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