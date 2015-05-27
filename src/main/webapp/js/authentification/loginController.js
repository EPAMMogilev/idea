(function () {
    'use strict';

    angular
        .module('app.controllers')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', 'sessionFactory', '$location', 'sessionService'];
    function LoginController($scope, sessionFactory, $location, sessionService) {
        var vm = this;

        vm.user = {};
        vm.user.email = '';
        vm.user.password = '';

        vm.loginUser = loginUser;

        function loginUser(user) {
            vm.resetError();

           sessionFactory.createSession(vm.user).then(function (session) {

                if(session.id===null) {
//                    vm.setError(login.status);

                    return;
                }

                sessionService.setSessionId(session.id);
                sessionService.setUser(session.user);

                vm.user.email = '';
                vm.user.password = '';

                $location.path("main");

                document.location.reload(true);

           }, function(error) {
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
