//= require lib/jquery.autocomplete-app
//= require_self

(function ($) {

    $(document).ready(function () {
        let $app = $("#app");

        //autocomplete
        $.ajax({
            method: "GET",
            url: $app.data('url')
        })
            .done(function (result) {
                $app.data('apps', result);
                $app.autocompleteApp();
            });
    });

}(jQuery));