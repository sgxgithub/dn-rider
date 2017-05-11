package dn.rider

class SettingsController {

    def index(SettingsCommand cmd) {

        String app = ""

        if (cmd.app) {
            app = cmd.app
        }
        log.info("Enregister preference: " + app)

        redirect(uri:"/")
    }
}
