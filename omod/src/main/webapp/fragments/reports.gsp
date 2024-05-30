<%
    def appFrameworkService = context.getService(context.loadClass("org.openmrs.module.appframework.service.AppFrameworkService"))
    def quarterly = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.ssemr.reports.quarterly")
    def monthly = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.ssemr.reports.monthly")
    def registers = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.ssemr.reports.registers")
    def contextModel = [:]
%>
<div class="dashboard clear">
    <div class="info-container column">
        <% if (registers) { %>
        <div class="info-section">
            <div class="info-header"><h3>Line Lists</h3></div>

            <div class="info-body">
                <ul>
                    <% registers.each { %>
                    <li>
                        ${ui.includeFragment("uicommons", "extension", [extension: it, contextModel: contextModel])}
                    </li>
                    <% } %>
                </ul>
            </div>
        </div>
        <% } %>
    </div>

    <div class="info-container column">
        <% if (monthly) { %>
        <div class="info-section">
            <div class="info-header"><h3>MOH Monthly Reports (DHIS2)</h3></div>

            <div class="info-body">
                <ul>
                    <% monthly.each { %>
                    <li>
                        ${ui.includeFragment("uicommons", "extension", [extension: it, contextModel: contextModel])}
                    </li>
                    <% } %>
                </ul>
            </div>
        </div>
        <% } %>
    </div>

    <div class="info-container column">
            <% if (quarterly) { %>
            <div class="info-section">
                <div class="info-header"><h3>Quarterly Reports (DATIM)</h3></div>

                <div class="info-body">
                    <ul>
                        <% quarterly.each { %>
                        <li>
                            ${ui.includeFragment("uicommons", "extension", [extension: it, contextModel: contextModel])}
                        </li>
                        <% } %>
                    </ul>
                </div>
            </div>
            <% } %>
        </div>
</div>
