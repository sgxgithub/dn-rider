package dn.rider

import org.grails.web.json.JSONObject
import org.springframework.web.multipart.MultipartFile

class Dn {

    //JSONObject NDL_pour_rundeck
    //static hasMany = [packages: DnPackage]

    //MultipartFile deliveryNoteFile
    byte[] dnBytes

    static constraints = {
        dnBytes nullable:true
    }

    static mapping = {
        dnBytes column: 'dn_bytes', sqlType: 'longblob'
    }
}