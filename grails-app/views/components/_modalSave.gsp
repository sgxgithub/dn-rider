<!-- Modal -->
<div class="modal fade" id="modalSave" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content" role="document">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Save delivery-notes</h5>
                <g:render template="/components/spinner"/>
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <g:form url="[action: 'saveDn', controller: 'edition']" method="post" id="form-save-dn">
                <div class="modal-body">
                    <g:hiddenField name='dn' id="hidden-field-dn">Dn</g:hiddenField>
                    <div class="form-group row">
                        <div class="col-4">
                            <h5>
                                <g:message code="dn.rider.sidebar.app"/>
                            </h5>
                        </div>

                        <div class="col-8">
                            <g:textField name="app" class="form-control" value="${app}" placeholder="ccl, kli..."
                                         data-url="${createLink(controller: 'search', action: 'getVersionsView')}"
                                         data-apps="${apps}"
                                         autocomplete="off" required="true"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-4">
                            <h5>
                                <g:message code="dn.rider.sidebar.version"/>
                            </h5>
                        </div>

                        <div class="col-8">
                            <g:textField name="version" class="form-control" value="${version}"
                                         placeholder="52.00.0-2, 1.36.1..."
                                         autocomplete="off"
                                         data-url="${createLink(controller: 'search', action: 'getVersionsList', params: [releaseType: 'All'])}"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-4">
                            <h5>
                                Repository
                            </h5>
                        </div>

                        <div class="col-8">
                            <input id="combobox" name='repo' class="form-control custom-select"
                                   data-url="${createLink(controller: 'edition', action: 'getRepos')}">
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </g:form>
        </div>
    </div>
</div>
