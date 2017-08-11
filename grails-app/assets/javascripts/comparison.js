//= require lib/jquery.sidebar
//= require lib/jquery.shift-click
//= require lib/jquery.autocomplete-app
//= require lib/jquery.json-browse-popover
//= require lib/jquery.utils
//= require_self

(function ($) {
    $(document).ready(function () {
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-comparison").addClass("active");

        //button back to top
        let $btt = $('#back-to-top');
        $btt.backToTop($("#content"));

        setPopovers();

        //active shift range select
        $('.checkbox-version').off('click').shiftClick();

        //set checkbox checked
        const versionsSelected = $('#cb-group-versions').data('versionsselected');
        $.each(versionsSelected, function (index, val) {
            $(`input:checkbox[value='${val}']`).prop('checked', true)
        });

        enableButtonCompare();

        //collapse sidebar when content is not empty
        if ($("#content").children().length !== 0) {
            $("#sidebar").collapse('hide');
        }
    });

    $('#sidebar').sidebarCollapse();

    //autocomplete of apps
    let $app = $("#app");
    let $releaseType = $("#releaseType");
    let $regex = $("#regex");

    const cleanForm = function () {
        $regex.val('');
    };

    let setVersions = function () {
        let app = $app.val();

        let releaseType = $releaseType.val();
        let regex = $regex.val();

        $.ajax({
            method: "POST",
            url: $app.data('url'),
            data: {app: app, releaseType: releaseType, regex: regex, template: '/comparison/listVersions'}
        })
            .done(function (result) {
                $("#versions").html(result);
                //active shift range select
                $('.checkbox-version').off('click').shiftClick();

                enableButtonCompare();
            });
    };

    $app.autocompleteApp(setVersions);

    //search the list of versions
    $app.on('input', function () {
        setVersions();
        cleanForm();
    });
    $releaseType.on('change', setVersions);
    $regex.on('input', setVersions);

    let setPopovers = function () {
        let timer;
        //enable popover
        //ref: https://stackoverflow.com/questions/15989591/how-can-i-keep-bootstrap-popover-alive-while-the-popover-is-being-hovered
        let $popovers = $("[data-toggle='popover']");
        //transfer content json to html
        $(".popover-body").each(function () {
            $(this).setPopoverContent($(this).data('content'));
        });
        $popovers.popover({
            container: 'body',
            html: true,
            template: '<div class="popover" role="tooltip"><div class="popover-arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
            animation: false,
            placement: 'left',
            trigger: 'manual',
            content: ""
        })
            .on("mouseenter", function () {
                let _this = this;
                timer = setTimeout(function () {
                    $(_this).popover("show");
                    $(".popover").on("mouseleave", function () {
                        $(_this).popover('hide');
                    });
                }, 300);
            })
            .on("mouseleave", function () {
                clearTimeout(timer);
                let _this = this;
                setTimeout(function () {
                    if (!$(".popover:hover").length) {
                        $(_this).popover("hide");
                    }
                }, 300);
            })
            .on('shown.bs.popover', function () {
                $("a.json-toggle").click(function (event) {
                    let _this = this;
                    event.preventDefault();
                    let target = $(this).toggleClass('collapsed').siblings('ul.json-dict, ol.json-array');
                    target.toggle();
                    if (target.is(':visible')) {
                        target.siblings('.json-placeholder').remove();
                    }
                    else {
                        let count = target.children('li').length;
                        let placeholder = count + (count > 1 ? ' items' : ' item');
                        target.after('<a href class="json-placeholder">' + placeholder + '</a>');
                        // Simulate click on toggle button when placeholder is clicked
                        $("a.json-placeholder").click(function (event) {
                            event.preventDefault();
                            $(_this).click();
                        });
                    }
                });
            });
    };

    let enableButtonCompare = function () {
        //disable button compare when no version selected
        if ($("form input:checkbox:checked").length == 0) {
            $('#btnCompare').addClass('disabled');
        } else {
            $('#btnCompare').removeClass('disabled');
        }
        $("input:checkbox").change(function () {
            if ($("form input:checkbox:checked").length == 0) {
                $('#btnCompare').addClass('disabled');
            } else {
                $('#btnCompare').removeClass('disabled');
            }
        });
    }

}(jQuery));