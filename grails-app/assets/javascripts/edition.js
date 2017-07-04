(function ($) {
    $(document).ready(function () {
        //set actived nav item
        $("nav .navbar-nav .nav-item").removeClass("active");
        $("#nav-item-edition").addClass("active");
    });

    //sidebar collapse
    $('#sidebar').sidebarCollapse();

}(jQuery));