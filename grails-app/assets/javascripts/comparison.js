(function ($) {

    //ref: https://forum.jquery.com/topic/jquery-1-9-1-shift-select-range-of-same-class-checkboxes-each-residing-in-same-class-divs
    //ref: http://jsfiddle.net/jakecigar/QB9RT/3/
    $.fn.shiftClick = function () {
        let lastSelected; // Firefox error: LastSelected is undefined
        let checkBoxes = $(this);
        this.each(function () {
            $(this).click(function (ev) {
                if (ev.shiftKey && lastSelected) {
                    let last = checkBoxes.index(lastSelected);
                    let first = checkBoxes.index(this);
                    let start = Math.min(first, last);
                    let end = Math.max(first, last);
                    let chk = lastSelected.checked;
                    for (let i = start; i <= end; i++) {
                        checkBoxes[i].checked = chk;
                    }
                } else {
                    lastSelected = this;
                }
            })
        });
    };

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
                $("#versions").html(result);
                //active shift range select
                $('.checkbox-version').off('click').shiftClick();
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