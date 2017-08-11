//= require lib/jquery.numberedtextarea
//= require lib/jquery.set-cursor-position
//= require lib/jquery.autocomplete-app
//= require_self

(function ($) {
    $(document).ready(function () {
        //set actived nav item
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-edition").addClass("active");

        setCombobox();
    });

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

    //display line number in textarea
    $('#textarea-dn').numberedtextarea();

    $("#json-error-link").click(function () {
        $("#textarea-dn").focus().setCursorPosition($('#offset').val());
    });

    $("#form-save-dn").submit(function () {
        $("#hidden-field-dn").val($("#textarea-dn").val());
    });

    $app = $("#app");
    $combobox = $('#combobox');

    $combobox.click(function () {
        // close if already visible
        if ($combobox.autocomplete("widget").is(":visible")) {
            $combobox.autocomplete("close");
            return;
        }
        $(this).blur();
        $combobox.autocomplete("search", "");
        $combobox.focus();
    });

    let setCombobox = function () {
        //clean the combobox
        $combobox.val('');

        let formData = new FormData();
        formData.append("app", $app.val());

        $.ajax({
            method: "POST",
            url: $combobox.data('url'),
            data: formData,
            processData: false,
            contentType: false
        })
            .done(function (repos) {
                $combobox.autocomplete({
                    source: repos,
                    minLength: 0
                });
            });
    };

    $app.autocompleteApp(setCombobox);
    $app.on('input', setCombobox);

}(jQuery));