var app = angular.module('klim', ['ngResource', 'ngRoute', 'ngCookies', 'ngSanitize', 'bootstrapLightbox'])
                 .factory('User', function($resource) { return $resource('/rest/users/:id'); })
                 .factory('Followers', function($resource) {return $resource('/rest/followers/:userId/:followerId'); })
                 .factory('Photos', function($resource) { return $resource('/rest/photos/:id'); })
                 .factory('Posts', function($resource) { return $resource('/rest/posts/:id'); });

app.config(function($routeProvider, $locationProvider) {
    $routeProvider
    .when("/", {
        redirectTo: '/login'
    })
    .when("/login", {
        controller: 'LoginController',
        templateUrl : "partials/login.html",
        controllerAs: 'vm'
    })
    .when("/user/:name", {
        templateUrl : "partials/user.html"
    })
    .otherwise({ redirectTo: '/login' });
});
