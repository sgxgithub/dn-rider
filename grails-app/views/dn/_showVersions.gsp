<h4>The result:</h4>
                             <h5>There are ${size_versions} dn for app ${app}</h5>
                             <ul>
                                 <g:each var="version" in="${listVersion}">
                                     <g:link action="showList" params="[app: app, version: version, releaseType:releaseType]">
                                         <li>Version:${version}</li>
                                     </g:link>
                                 </g:each>
                             </ul>