(function () {
    'use strict';
    angular
        .module('app.controllers')
        .controller('sessionCtrl', sessionCtrl);

    sessionCtrl.$inject = ['$rootScope', '$http', '$location', '$translate', 'sessionService', 'authentificationService'];

    function sessionCtrl($rootScope, $http, $location, $translate, sessionService, authentificationService) {

        var vm = this;
        vm.logout = logout;

        function logout() {
            $http.post("logout", {}).success(function () {
                $rootScope.authenticated = false;
                $rootScope.currentUser = {};
                $location.path("/home");
            }).error(function (data) {
                console.log("Logout failed");
            });
        }

        vm.useLang = function (lang) {
            $translate.use(lang);
        }

        vm.getCurrentLang = function () {
            return $translate.use();
        }

        vm.hasRole = function (role, user) {
            return sessionService.hasRole(role, user);
        }

        vm.getIconClassForCurrentUser = function () {
            var user = $rootScope.currentUser;
            return sessionService.getIconClassForCurrentUser(user);
        }
    }



})();