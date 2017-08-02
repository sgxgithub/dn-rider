<nav class="nav">
    <a data-toggle="collapse" href="#sidebar">
        <i class="fa fa-navicon fa-lg align-middle"></i>
    </a>
    <a class="nav-link" href="" id="formatJson">JSON</a>
    <a class="nav-link" href="" id="formatRaw">RAW</a>

    <g:link class="nav-link" controller="edition" action="editDnFromNexus"
            params="[app: app, version: version]" target="_blank">
        Edition
    </g:link>

    <a class="nav-link" href="${urlNexus}"
       target="_blank" id="lienNexus">Nexus</a>

    <a class="nav-link" href="" id="btnValidation" data-url="${createLink(controller: 'validation', action: 'validateSchema')}">Validation</a>

</nav>

<pre id="blockDn" data-dnraw="${dnRaw}" data-dnjson="${dnJson}"></pre>