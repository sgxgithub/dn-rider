//= require lib/jquery-ui.min
//= require lib/tether.min
//= require lib/bootstrap
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