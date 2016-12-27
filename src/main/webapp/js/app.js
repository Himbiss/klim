var app = angular.module('klim', ['ngResource', 'ngRoute'], function($locationProvider) {$locationProvider.html5Mode(true);})
                 .factory('User', function($resource) { return $resource('/rest/users/:id'); })
                 .factory('Followers', function($resource) {return $resource('/rest/followers/:userId/:followerId'); })
                 .factory('Posts', function($resource) { return $resource('/rest/posts/:id'); });

app.constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
});

app.constant('USER_ROLES', {
    all: '*',
    admin: 'admin',
    editor: 'editor',
    guest: 'guest'
})

app.config(function($routeProvider, $locationProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "partials/login.html"
    })
    .when("/user/:name", {
        templateUrl : "partials/user.html"
    })
    .otherwise({ redirectTo: '/' });

    // use the HTML5 History API
    $locationProvider.html5Mode(true);
});
