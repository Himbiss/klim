angular.module('klim').controller('UserCtrl', function($scope, $http, $location, $routeParams, User, Followers, Posts, Photos, Lightbox) {
  var profileId = $routeParams.name;

  $scope.profileUser = User.get({id : profileId});

  $scope.posts = Posts.query({userId : profileId});

  Followers.get( {userId : $scope.currentUser.id, followerId : profileId},
                 function() { $scope.isFollower = true;  },
                 function() { $scope.isFollower = false; } );

  $scope.profilePhotos = Photos.query({userId : profileId}, function(response) { console.log(response); });

  $scope.follow = function() {
    Followers.save({userId : $scope.currentUser.id, followerId : profileId}, function() {
        $scope.isFollower = true;
    });
  }

  $scope.unfollow = function() {
    Followers.delete({userId : $scope.currentUser.id, followerId : profileId}, function() {
        $scope.isFollower = false;
    });
  }

  $scope.post = function() {
    Posts.save({userId : $scope.currentUser.id, profileId : $scope.profileUser.id, content : $scope.content}, function(response) {
      $scope.content = '';
      $scope.posts.unshift(response);
    });
  }

  $scope.openLightboxModalPostPhotos = function (postIdx, photoIdx) {
      Lightbox.openModal($scope.posts[postIdx].photos, photoIdx);
  };

  $scope.openLightboxModalProfilePhotos = function (photoIdx) {
      Lightbox.openModal($scope.profilePhotos, photoIdx);
  };
});