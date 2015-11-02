(function () {
    'use strict';
    angular
        .module('app.controllers')
        .controller('sessionCtrl', sessionCtrl);

    sessionCtrl.$inject = ['$rootScope', '$http', '$window', '$translate', 'sessionService'];

    function sessionCtrl($rootScope, $http, $window, $translate, sessionService) {

        var vm = this;
        vm.authenticate = authenticate;
        vm.logout = logout;

        function authenticate(callback) {
            $http.get('user').success(function (data) {

                if (data.name) {
                    console.log(data.name);
                    $rootScope.authenticated = true;
                    $rootScope.currentUser = data;
                } else {

                    $rootScope.authenticated = false;
                }
                callback && callback();
            }).error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });

        }

        function logout() {
            $http.post("logout", {}).success(function () {
                $window.location.reload();
            }).error(function (data) {
                console.log("Logout failed");
            });
        }

        vm.authenticate();

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