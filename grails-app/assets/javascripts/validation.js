if (typeof jQuery === 'undefined') {
    throw new Error('Validation\'s JavaScript requires jQuery')
}

(function ($) {
    $(document).ready(function () {
        //set actived nav item
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-validation").addClass("active");
    });

    //display line number in textarea
    //Found at: http://www.jqueryscript.net/form/jQuery-Plugin-To-Display-Line-Numbers-In-Textarea-numberedTextarea.html
    $('#textarea-dn').numberedtextarea();

    //show file name
    $('input:file').change(function (e) {
        $(".custom-file-control").addClass('changed').attr("data-content", e.target.files[0].name);
    });

    /*
     * Function added to set the cursor position at a given offset in a text area
     * Found at: http://stackoverflow.com/questions/499126/jquery-set-cursor-position-in-text-area
     */
    $.fn.setCursorPosition = function (pos) {
        if ($(this).get(0).setSelectionRange) {
            $(this).get(0).setSelectionRange(pos, pos);
        } else if ($(this).get(0).createTextRange) {
            var range = $(this).get(0).createTextRange();
            range.collapse(true);
            range.moveEnd('character', pos);
            range.moveStart('character', pos);
            range.select();
        }
    };
    $("#json-error-link").click(function () {
        $("#textarea-dn").focus().setCursorPosition($('#offset').val());
    });
}(jQuery));