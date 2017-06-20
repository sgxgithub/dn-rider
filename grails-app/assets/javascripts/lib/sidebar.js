$.fn.sidebarCollapse = function () {
    $(this)
        .on('show.bs.collapse', function () {
            $("#content").toggleClass("col-9 col-12");
        })
        .on('hidden.bs.collapse', function () {
            setTimeout(
                function () {
                    $("#content").toggleClass("col-9 col-12");
                }, 350);
        });
};