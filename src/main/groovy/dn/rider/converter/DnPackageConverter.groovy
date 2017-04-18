package dn.rider.converter

import dn.rider.content.DnPackage
import grails.databinding.converters.ValueConverter
import org.grails.web.json.JSONObject

/**
 *
 */
class DnPackageConverter implements ValueConverter {

    boolean canConvert(value) {
        value instanceof JSONObject
    }

    def convert(value) {
        log.info "converting the value..."
        new DnPackage(info: value.type, ver: value.version, name: value.name)
    }

    Class<?> getTargetType() {
        DnPackage
    }
}
