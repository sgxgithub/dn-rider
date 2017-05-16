(function ($) {
    var $app = $("#app");
     var apps = $app.data('apps');

     $app.autocomplete({
        source: function(request, response) {
            var results = $.ui.autocomplete.filter(apps, request.term);
            esponse(results.slice(0, 10));
         }
     });
}(jQuery));