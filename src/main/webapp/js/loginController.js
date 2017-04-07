(function () {
    'use strict';

    angular
        .module('klim')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$location', 'AuthenticationService'];
    function LoginController($location, AuthenticationService) {
        var vm = this;

        vm.login = login;

        (function initController() {
            // reset login status
            AuthenticationService.TrySessionLogin();
        })();

        function login() {
            vm.dataLoading = true;
            AuthenticationService.Login(vm.username, vm.password, function (response) {
                if (response.sessionId) {
                    AuthenticationService.SaveSession(response, function(result) {
                        if (result.success) {
                            $location.path('/user/'+result.data.userName);
                        }
                        else {
                            console.log('error');
                        }
                    });
                } else {
                    console.log(response)
                    //FlashService.Error(response.message);
                    vm.dataLoading = false;
                }
            });
        };
    }

})();