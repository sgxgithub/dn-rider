$.fn.backToTop = function (content) {

    const $btt = $(this);

    content.scroll(function () {
        if ($(this).scrollTop() > 50) {
            $btt.fadeIn();
        } else {
            $btt.fadeOut();
        }
    });

// scroll body to 0px on click
    $btt.click(function () {
        content.animate({
            scrollTop: 0
        }, 500);
        return false;
    });
};