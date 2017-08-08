//= require lib/jquery.json-browse
//= require_self

(function ($) {
    $(document).ready(function () {
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-validation").addClass("active");
    });

    //json format
    //plugin found in jqueryscript.net
    //ref : http://www.jqueryscript.net/demo/Tiny-jQuery-Plugin-For-Pretty-JSON-Print-JSON-Browse/
    let $schema = $("#schema");
    let schemajson = $schema.data('schemajson');
    let schemaraw = $schema.data('schemaraw');
    if (schemajson) {
        $schema.jsonBrowse(schemajson);
    }

    $("input[type='radio']").on('change', function () {
        const formatShow = $('input[name=formatShow]:checked').val();
        if (formatShow === 'json') {
            $schema.jsonBrowse(schemajson);
        } else {
            $schema.html(schemaraw);
        }
    });

}(jQuery));