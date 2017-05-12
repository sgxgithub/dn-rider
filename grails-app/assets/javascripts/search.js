(function ($) {

    var $app = $("#app");
    var apps = $app.data('apps');

    $app.autocomplete({
        source: apps
    });

}(jQuery));