//= require lib/jquery.sidebar
//= require lib/jquery.autocomplete-app
//= require lib/jquery.json-browse
//= require lib/jquery.utils
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

        //button back to top
        let $btt = $('#back-to-top');
        $btt.backToTop($("#content"));

        //collapse sidebar when content is not empty
        if ($("#content").children().length !== 0) {
            $("#sidebar").collapse('hide');
        }

        setVersions();
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

    $app.autocompleteApp(setVersions, $version);

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
    if (dnJson) {
        $blockDn.jsonBrowse(dnJson);
    }

    $("#btnValidation").click(function (event) {
        event.preventDefault();

        let formData = new FormData();
        formData.append("dn", JSON.stringify(dnJson));

        $.ajax({
            method: "POST",
            url: $("#btnValidation").data('url'),
            data: formData,
            processData: false,
            contentType: false
        })
            .done(function (result) {
                //set table content
                $("#validationResult").html(result);
            });
    });

    $("input[type='radio']").on('change', function () {
        const formatShow = $('input[name=formatShow]:checked').val();
        if (formatShow === 'json') {
            $blockDn.jsonBrowse(dnJson);
        } else {
            $blockDn.html(JSON.stringify(dnJson, null, 4));
        }
    });

}(jQuery));