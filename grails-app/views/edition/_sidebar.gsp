<form class="form-inline">
    <g:message code="dn.rider.dn"/>
    <label class="custom-file mx-2 my-2 ">
        <input type="file" id="deliveryNoteFile" name="deliveryNoteFile" class="custom-file-input"
               data-url="${createLink(controller: 'validation', action: 'uploadDn')}"/>
        <span class="custom-file-control"></span>
    </label>

    <g:render template="/components/spinner"/>
</form>