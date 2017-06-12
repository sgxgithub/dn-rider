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

    $("#formatJson").click(function (e) {
        e.preventDefault();
        $schema.jsonBrowse(schemajson);
    });
    $("#formatRaw").click(function (e) {
        e.preventDefault();
        $schema.html(schemaraw);
    });

}(jQuery));