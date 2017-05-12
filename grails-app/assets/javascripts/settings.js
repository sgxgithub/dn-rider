(function ($) {

    var $appsQuickAccess = $("#appsQuickAccess");
    var apps = $appsQuickAccess.data('apps');

    $appsQuickAccess.autocomplete({
        source: apps
    });

}(jQuery));