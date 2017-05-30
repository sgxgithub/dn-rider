(function ($) {
    $(document).ready(function () {
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-comparison").addClass("active");
    });

    //autocomplete of apps
    let $app = $("#app");
    let apps = $app.data('apps');
    $app.autocomplete({
        source: function (request, response) {
            let results = $.ui.autocomplete.filter(apps, request.term);
            response(results.slice(0, 10));
        }
    });

    //search the list of versions
    $("#btnSearch").click(function () {
        let app = $("#app").val();
        let releaseType = $("#releaseType").val();

        $.ajax({
            method: "GET",
            url: $("#btnSearch").data('url') + '?app=' + app + '&releaseType=' + releaseType
        })
            .done(function (result) {
                $("#versions").html(result)
            });
    });

    //compare
    $("#btnCompare").click(function (event) {
        event.preventDefault();

        let formData = new FormData(document.getElementById("formCompare"));

        $.ajax({
            method: "POST",
            url: "compare",
            data: formData,
            processData: false,
            contentType: false
        })
            .done(function (result) {
                $("#tableComparison").html(result);
            });
    });

}(jQuery));