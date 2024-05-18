var runReportApp = angular.module("runReportApps", [ ]).
filter('translate', function() {
  return function(input, prefix) {
    var code = prefix ? prefix + input : input;
    return emr.message(code, input);
  }
});

runReportApp.controller('RunReportController', ['$scope', '$http', '$window', '$timeout', function($scope, $http, $window, $timeout) {

  $scope.loading = true;

  $scope.queue = [];

  $scope.completed = [];

  $scope.submitting = false;

  $scope.refreshHistory = function() {
    $http.get("/" + OPENMRS_CONTEXT_PATH + "/reportingui/reportStatus/getQueuedRequests.action?reportDefinition=" + $window.reportDefinition.uuid).
    success(function(data, status, headers, config) {
      $scope.queue = data;
      $scope.loading = false;
      if ($scope.queue.length > 0)  {
        $timeout($scope.refreshHistory, 10000);
      }
    }).
    error(function(data, status, headers, config) {
      console.log("Error getting queue: " + status);
      $scope.queue = [];
    });

    $http.get("/" + OPENMRS_CONTEXT_PATH + "/reportingui/reportStatus/getCompletedRequests.action?reportDefinition=" + $window.reportDefinition.uuid).
    success(function(data, status, headers, config) {
      $scope.completed = data;
    }).
    error(function(data, status, headers, config) {
      console.log("Error getting completed: " + status);
      $scope.completed = [];
    });
  }

  $scope.hasResults = function() {
    return !$scope.loading && $scope.completed.length > 0;
  }

  $scope.hasNoResults = function() {
    return !$scope.loading && $scope.completed.length == 0;
  }

  var defaultSuccessAction = function(data, status, headers, config) {
    emr.successMessage(data.message);
    $scope.refreshHistory();
  }

  var defaultErrorAction = function(data, status, headers, config) {
    emr.errorMessage(data.message);
    $scope.refreshHistory();
  }

  $scope.cancelRequest = function(request) {
    $http.post("/" + OPENMRS_CONTEXT_PATH + "/reportingui/reportStatus/cancelRequest.action?reportRequest=" + request.uuid).
    success(defaultSuccessAction).
    error(defaultErrorAction);
  }

  $scope.canSave = function(request) {
    return request.status == 'COMPLETED';
  }

  $scope.saveRequest = function(request) {
    $http.post("/" + OPENMRS_CONTEXT_PATH + "/reportingui/reportStatus/saveRequest.action?reportRequest=" + request.uuid).
    success(defaultSuccessAction).
    error(defaultErrorAction);
  }

  $scope.runReport = function() {
    // this is a plain old form, not really using angular. need to rewrite the datepickers to be angular-friendly
    var form = angular.element('#run-report');
    var submission = form.serializeArray();
    var missingParams = [ ];
    console.log(submission);
    for (var i = 0; i < submission.length; ++i) {
      var p = submission[i];
      if (p.name.indexOf("parameterValues[") == 0) {
        if (!p.value) {
          missingParams.push(p.name);
        }
      }
    }
    if (missingParams.length > 0) {
      emr.errorMessage(emr.message("reportingui.runReport.missingParameter", "Missing parameter values"));
    }
    else {
      $scope.submitting = true;
      $.post("runReport.page?reportDefinition=" + $window.reportDefinition.uuid, submission, function() {
        location.href = location.href;
      });
    }
  }

  $scope.reRunRequest = function(request) {
    var months = ["Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    var monthsIntegers = ["01", "02", "03","04", "05", "06", "07", "08", "09", "10", "11", "12"];

    var startDate = new Date(request.reportDefinition.mappings[2].value.split('.').join(' '));
    var endDate = new Date (request.reportDefinition.mappings[0].value.split('.').join(' '));



    jQuery('#run-report').find("input[name*='startDate']").val(startDate.getFullYear()+"-"+ monthsIntegers[startDate.getMonth()]+"-"+startDate.getDate());
    jQuery('#run-report').find("input[name*='startDate']").parent().find("input[type='text']").val(startDate.getDate()+" " + months[startDate.getMonth()] +" " + startDate.getFullYear());
    jQuery('#run-report').find("input[name*='endDate']").val(endDate.getFullYear()+"-"+  monthsIntegers[endDate.getMonth()] +"-"+endDate.getDate());
    jQuery('#run-report').find("input[name*='endDate']").parent().find("input[type='text']").val(endDate.getDate()+" " + months[endDate.getMonth()] +" " + endDate.getFullYear());

  }


}]);
