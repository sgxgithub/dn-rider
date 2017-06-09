<nav class="nav">
    <a class="nav-link" href="" id="formatJson">JSON</a>
    <a class="nav-link" href="" id="formatRaw">RAW</a>
    <g:link class="nav-link" controller="validation" action="validationDnFromNexus"
            params="[app: app, version: version]" target="_blank">
        Validation
    </g:link>
    <a class="nav-link" href="${urlNexus}"
       target="_blank" id="lienNexus">Nexus</a>
</nav>

<pre id="blockDn" data-dnraw="${dnRaw}" data-dnjson="${dnJson}"></pre>