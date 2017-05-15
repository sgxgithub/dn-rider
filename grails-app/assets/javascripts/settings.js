(function ($) {

    var $appsQuickAccessInput = $("#appsQuickAccessInput");
    var apps = $appsQuickAccessInput.data('apps');

    $appsQuickAccessInput.autocomplete({
        //limit the number of results to 10
        source: function(request, response) {
                    var results = $.ui.autocomplete.filter(apps, request.term);
                    response(results.slice(0, 10));
                 },
        select: function (event, ui) {
                    event.preventDefault();
                    $(this).val('');
                    $('#modal-chips').append(" <div class=\"chip\" >"+ui.item.value+"<span class=\"closebtn\" onclick=\"this.parentElement.remove()\">&times;</span></div>");
                 }
    });

    $("#formModal").submit(function(event){
             var apps = "hello_world";

//             $('#modal-chips').children.each(function(index){
//                apps = apps + '_' + index;
//             });

             $('#appsQuickAccess').val(apps);
             return;
         });

}(jQuery));