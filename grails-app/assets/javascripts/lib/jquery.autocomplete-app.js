/**
 * auto-complete the app name
 * auto-complete the app version when version exist
 */

$.fn.autocompleteApp = function (functionAfterSelect, version = null) {
    let $app = $(this);
    let apps = $app.data('apps');

    $app.autocomplete({
        source: function (request, response) {
            let results = $.ui.autocomplete.filter(apps, request.term);
            response(results.slice(0, 10));
        },
        select: function (event, ui) {
            //set the input value from selected item
            $app.val(ui.item.value);

            if (functionAfterSelect !== undefined) {
                functionAfterSelect();
            }

            if (version !== null) {
                // add data-versions when select a version
                $.ajax({
                    method: "GET",
                    url: version.data('url') + '&app=' + ui.item.value
                })
                    .done(function (versions) {
                        version.autocomplete({
                            source: function (request, response) {
                                let results = $.ui.autocomplete.filter(versions, request.term);
                                response(results.slice(0, 10));
                            }
                        });
                    });
            }
        }
    });
};


