(function ($) {
    $(document).ready(function(){
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-comparison").addClass("active");
    });

    //autocomplete of apps
    var $app = $("#app");
    var apps = $app.data('apps');
    $app.autocomplete({
       source: function(request, response) {
                   var results = $.ui.autocomplete.filter(apps, request.term);
                   response(results.slice(0, 10));
                },
       select: function (event, ui) { // add data-versions when select a version
                   $.ajax({
                        method: "GET",
                        url: $('#version1').data('url') + '&app=' + ui.item.value
                   })
                   .done(function( versions ) {
                      $('#version1, #version2').autocomplete({
                                        source: function(request, response) {
                                                   var results = $.ui.autocomplete.filter(versions, request.term);
                                                   response(results.slice(0, 10));
                                                }
                                   });
                   });
                 }
    });
}(jQuery));