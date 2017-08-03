//= require lib-ext/tether.min
//= require lib-ext/bootstrap
//= require lib-ext/jquery-ui.min
//= require_self

if (typeof jQuery !== 'undefined') {
    (function ($) {
        $(document).ajaxStart(function () {
            $('#spinner').fadeIn();
        }).ajaxStop(function () {
            $('#spinner').fadeOut();
        });

    })(jQuery);
}