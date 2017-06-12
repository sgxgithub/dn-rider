//= require jquery-ui.min
//= require tether.min
//= require bootstrap
//= require_self

if (typeof jQuery !== 'undefined') {
    (function ($) {
        $(document).ajaxStart(function () {
            $('#spinner').fadeIn();
        }).ajaxStop(function () {
            $('#spinner').fadeOut();
        });

        $(document).ready(function () {
            let $btt = $('#back-to-top');
            $(window).scroll(function () {
                if ($(this).scrollTop() > 50) {
                    $btt.fadeIn();
                } else {
                    $btt.fadeOut();
                }
            });
            // scroll body to 0px on click
            $btt.click(function () {
                $(this).tooltip('hide');
                $('body,html').animate({
                    scrollTop: 0
                }, 500);
                return false;
            });

            $btt.tooltip('show');

        });

    })(jQuery);
}
