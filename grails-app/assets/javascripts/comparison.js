(function ($) {
    $(document).ready(function () {
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-comparison").addClass("active");
    });

    //autocomplete of apps
    let $app = $("#app");

    let setVersions = function () {
        // $("#btnSearch").click(function () {
        let app = $app.val();
        //fired only if the length >= 3
        if (app.length < 3) return;

        let releaseType = $("#releaseType").val();

        $.ajax({
            method: "GET",
            url: $app.data('url') + '?app=' + app + '&releaseType=' + releaseType
        })
            .done(function (result) {
                console.log(result);
                $("#versions").html(result);
            });
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
        }
    });

    //search the list of versions
    $app.on('input', setVersions);

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

}(jQuery));