<%
    def appFrameworkService = context.getService(context.loadClass("org.openmrs.module.appframework.service.AppFrameworkService"))
    def overview = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.ssemr.reports.overview")
    def monthly = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.ssemr.reports.monthly")
    def registers = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.ssemr.reports.registers")
    def contextModel = [:]
%>
<div class="dashboard clear">
    <div class="info-container column">
        <% if (registers) { %>
        <div class="info-section">
            <div class="info-header"><h3>Registers</h3></div>

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
            <div class="info-header"><h3>Monthly Reports</h3></div>

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
</div>
