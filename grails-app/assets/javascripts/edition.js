//= require lib/jquery.numberedtextarea
//= require lib/jquery.set-cursor-position
//= require lib/jquery.autocomplete-app
//= require lib/combobox
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

    let setCombobox = function () {
        //clean the combobox
        $('input.custom-combobox-input').val('');
        $combobox
            .find('option')
            .remove()
            .end();

        let formData = new FormData();
        formData.append("app", $app.val());

        $.ajax({
            method: "POST",
            url: $("#combobox").data('url'),
            data: formData,
            processData: false,
            contentType: false
        })
            .done(function (repos) {
                $.each(repos, function (key, value) {
                    $combobox
                        .append($("<option></option>")
                            .attr("value", key)
                            .text(value));
                });
            });
    };

    $app.autocompleteApp(setCombobox);
    $app.on('input', setCombobox);

}(jQuery));