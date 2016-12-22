var app = angular.module('klim', [], function($locationProvider) {
                                           $locationProvider.html5Mode(true);
                                         });

app.controller('UserCtrl', function($scope, $http, $location) {
  var profileId = $location.search()['profile_id'];
  var userId = $location.search()['user_id'];

  $http.get("rest/users/" + profileId)
  .then(function(response) {
      $scope.user = response.data;
      $scope.bgStyle = { 'background' : 'url(\'' + response.data.backgroundImg + '\')'};
  });

  $http.get("rest/users/" + userId + "/followers")
    .then(function(response) {
        $scope.followers = response.data;
        $scope.isFollower = containsUser($scope.followers, profileId);
    });

  $scope.follow = function() {
    $scope.isFollower = true;
  }

  $scope.unfollow = function() {
    $scope.isFollower = false;
  }
});

function containsUser(list, userId) {
    var i;
    for (i = 0; i < list.length; i++) {
        console.log(list[i].id);
        if (list[i].id == userId) {
            return true;
        }
    }
    return false;
}