(function ($) {
    let $app = $("#app");
    let apps = $app.data('apps');

    $app.autocomplete({
        source: function (request, response) {
            let results = $.ui.autocomplete.filter(apps, request.term);
            response(results.slice(0, 10));
        }
    });
}(jQuery));