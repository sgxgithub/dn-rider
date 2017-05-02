package dn.rider

import org.springframework.web.multipart.MultipartFile

class UploadDnCommand implements grails.validation.Validateable{

    MultipartFile deliveryNoteFile
}