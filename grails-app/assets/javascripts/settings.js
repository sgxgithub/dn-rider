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
                    $('#modal-chips').append(" <div class=\"chip\"> <span>"+ ui.item.value+ "</span> <button type=\"button\" class=\"close\" onclick=\"this.parentElement.remove()\"><span aria-hidden=\"true\">&times;</span> </button> </div>");
                 }
    });

    $("#formModal").submit(function(event){
             var apps = "";

             $('#modal-chips .chip>span').each(function(index){
                apps = apps + '_' + $(this).text();
             });

             $('#appsQuickAccess').val(apps);
             return;
         });

}(jQuery));