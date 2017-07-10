//= require lib/sidebar
//= require lib/jquery.numberedtextarea
//= require lib/setCursorPosition
//= require_self

(function ($) {
    $(document).ready(function () {
        //set actived nav item
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-edition").addClass("active");
    });
    //sidebar collapse
    $('#sidebar').sidebarCollapse();

    //display line number in textarea
    $('#textarea-dn').numberedtextarea();

    $("#deliveryNoteFile").change(function (e) {
        //show file name
        $(".custom-file-control").addClass('changed').attr("data-content", e.target.files[0].name);
        //set textarea content
        let formData = new FormData();
        let deliveryNoteFile = this.files[0];
        formData.append("deliveryNoteFile", deliveryNoteFile);

        $.ajax({
            url: $('#deliveryNoteFile').data('url'),
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (res) {
                $('#textarea-dn').val(res);
            },
            error: function (res) {
                alert(res);
            }
        });
    });

    $("#json-error-link").click(function () {
        $("#textarea-dn").focus().setCursorPosition($('#offset').val());
    });

}(jQuery));