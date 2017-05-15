(function ($) {

    var $appsQuickAccess = $("#appsQuickAccess");
    var apps = $appsQuickAccess.data('apps');

    $appsQuickAccess.autocomplete({
        //limit the number of results to 10
        source: function(request, response) {
                    var results = $.ui.autocomplete.filter(apps, request.term);
                    response(results.slice(0, 10));
                 }
    });

}(jQuery));