(function () {
    'use strict';

    angular
        .module('app.controllers')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$http', '$location', '$rootScope', '$cookies', 'authentificationService'];

    function LoginController($scope, $http, $location, $rootScope, $cookies, authentificationService) {
        var vm = this;
        vm.login = login;
        vm.credentials = {};

        function login() {
            authentificationService.authenticate(vm.credentials, function () {
                if ($rootScope.authenticated) {
                    $location.path("home");
                    vm.error = false;
                } else {
                    $location.path("login");
                    vm.error = true;
                }
            });
        };
    }

})();