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

        //button back to top
        let $btt = $('#back-to-top');
        $("#content").scroll(function () {
            if ($(this).scrollTop() > 50) {
                $btt.fadeIn();
            } else {
                $btt.fadeOut();
            }
        });
        // scroll body to 0px on click
        $btt.click(function () {
            $(this).tooltip('hide');
            $("#content").animate({
                scrollTop: 0
            }, 500);
            return false;
        });

        $btt.tooltip('show');
    });

    //autocomplete of apps
    let $app = $("#app");
    let $releaseType = $("#releaseType");
    let $regex = $("#regex");

    const cleanForm = function () {
        $regex.val('');
    };

    let setVersions = function () {
        let app = $app.val();
        //fired only if the length >= 3
        if (app.length < 3) return;

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
    $app.on('input', function () {
        setVersions();
        cleanForm();
    });
    $releaseType.on('change', setVersions);
    $regex.on('input', setVersions);

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
                //set table content
                $("#tableComparison").html(result);
                let timer;
                //enable popover
                //ref: https://stackoverflow.com/questions/15989591/how-can-i-keep-bootstrap-popover-alive-while-the-popover-is-being-hovered
                let $popovers = $("[data-toggle='popover']");
                //transfer content json to html
                $popovers.each(function () {
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
            });
    });

    $('#sidebar').sidebarCollapse();

}(jQuery));