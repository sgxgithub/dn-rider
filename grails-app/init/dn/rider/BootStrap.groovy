package dn.rider

class BootStrap {

    def init = { servletContext ->
        log.info "test info"
        log.error "test error"
    }
    def destroy = {
    }
}
