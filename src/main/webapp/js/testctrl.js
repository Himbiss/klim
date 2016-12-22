var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope, $http) {
  $http.get("rest/users")
  .then(function(response) {
      $scope.myWelcome = response.data;
  });
});