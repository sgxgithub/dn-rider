(function ($) {
    $(document).ready(function () {
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-search").addClass("active");
    });

    //autocomplete of apps
    let $app = $("#app");
    let apps = $app.data('apps');
    $app.autocomplete({
        source: function (request, response) {
            let results = $.ui.autocomplete.filter(apps, request.term);
            response(results.slice(0, 10));
        },
        select: function (event, ui) { // add data-versions when select a version
            $.ajax({
                method: "GET",
                url: $('#version').data('url') + '&app=' + ui.item.value
            })
                .done(function (versions) {
                    $('#version').autocomplete({
                        source: function (request, response) {
                            let results = $.ui.autocomplete.filter(versions, request.term);
                            response(results.slice(0, 10));
                        }
                    });
                });
        }
    });

    //sidebar collapse
    $('#sidebar')
        .on('show.bs.collapse', function () {
            $("#content").toggleClass("col-9 col-12");
        })
        .on('hidden.bs.collapse', function () {
            setTimeout(
                function () {
                    $("#content").toggleClass("col-9 col-12");
                }, 350);
        });

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