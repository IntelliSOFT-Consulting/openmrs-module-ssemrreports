<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeCss("reportingui", "reportsapp/home.css")
%>
<script type="text/javascript">
</script>
<style>
.dashboard .info-container {
  width: 30%;
}
</style>
<div>
  ${ui.includeFragment("ssemrreports", "reports")}
</div>

