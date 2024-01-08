<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeCss("reportingui", "reportsapp/home.css")
%>
<script type="text/javascript">
  var breadcrumbs = [
    {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
    {
      label: "${ ui.message("reportingui.reportsapp.home.title") }", link: "${ ui.pageLink("botswanaemrreports", "reports")
}"
    }
  ];
</script>
<style>
.dashboard .info-container {
  width: 30%;
}
</style>
${ui.includeFragment("ssemrreports", "reports")}
