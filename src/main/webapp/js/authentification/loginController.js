(function () {
    'use strict';

    angular
        .module('app.controllers')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$http', '$location', 'sessionService'];
    function LoginController($scope, $http, $location, sessionService) {
        var vm = this;

        vm.user = {};
        vm.user.email = '';
        vm.user.password = '';

        vm.loginUser = loginUser;

        function loginUser(user) {
            vm.resetError();

           $http.post('api/v1/users/authenticate', vm.user).success(function(login) {
                if(login.sessionId===null) {
                    vm.setError(login.status);

                    return;
                }

                sessionService.setSessionId(login.sessionId);
                sessionService.setUser(vm.user.email);

                vm.user.email = '';
                vm.user.password = '';

                $location.path("main");

                document.location.reload(true);

           }).error(function() {
                vm.setError('Invalid user/password combination');
           });
        }

        vm.resetError = function() {
            vm.error = false;
            vm.errorMessage = '';
        }

        vm.setError = function(message) {
            vm.error = true;
            vm.errorMessage = message;
            vm.SessionId='';
        }
    }

})();
