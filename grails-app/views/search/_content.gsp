<nav class="nav">
    <a data-toggle="collapse" href="#sidebar">
        <span class="fa fa-navicon fa-lg pt-2"></span>
    </a>

    <div class="btn-group nav-link" id="status" data-toggle="buttons">
        <label class="btn btn-secondary btn-on btn-sm active">
            <input type="radio" name="formatShow" value="json" checked>JSON</label>
        <label class="btn btn-secondary btn-off btn-sm ">
            <input type="radio" name="formatShow" value="raw">RAW</label>
    </div>

    <a class="nav-link" href="" id="btnValidation"
       data-url="${createLink(controller: 'validation', action: 'validateSchema')}">Validation</a>

    <g:link class="nav-link" controller="edition" action="editDnFromNexus"
            params="[app: app, version: version]" target="_blank">
        Edition
    </g:link>

    <a class="nav-link" href="${urlNexus}"
       target="_blank" id="lienNexus">Source</a>

</nav>

<div id="validationResult"></div>

<pre id="blockDn" data-dnraw="${dnRaw}" data-dnjson= ${dnJson}> </pre>

<g:render template="/components/backToTop"/>