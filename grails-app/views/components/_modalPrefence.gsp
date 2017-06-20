<!-- Button trigger modal -->
<li class="nav-item" data-toggle="modal" data-target="#modalPreference">
    <a class="nav-link"><span class="fa fa-cog fa-lg"></span></a>
</li>

<!-- Modal -->
<div class="modal fade" id="modalPreference" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content" role="document">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">User Settings</h5>
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <g:form url="[action: 'index', controller: 'settings']" method="get" id="formModal">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="appsQuickAccessInput">Choice of applications for quick access :</label>
                        <g:textField class="form-control" autocomplete="off" name="appsQuickAccessInput"
                                     placeholder="trigramme"
                                     data-apps="${apps}"/>
                    </div>

                    <div id="modal-chips" class="my-2">
                        <g:each var="appQuickAccess" in="${appsQuickAccessArray}">
                            <div class="chip my-1">
                                <span>${appQuickAccess}</span>
                                <a class="close"><span> &times;</span></a>
                            </div>
                        </g:each>
                    </div>
                </div>

                <g:hiddenField name="appsQuickAccess" value="${appsQuickAccess}"/>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </g:form>
        </div>
    </div>
</div>