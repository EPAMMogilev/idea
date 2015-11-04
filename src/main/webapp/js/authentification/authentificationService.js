(function () {

    'use strict';

    angular
        .module('app.services')
        .service('authentificationService', authentificationService);

    authentificationService.$inject = ['$rootScope', '$http', '$cookies', '$q'];

    function authentificationService($rootScope, $http, $cookies, $q) {

        var publicMethod = {
            init: init,
            authenticate: authenticate
        };

        return publicMethod;

        function init() {
            $http.get('user', {
                headers: $cookies
            }).success(function (data) {
                if (data.name) {
                    $rootScope.authenticated = true;
                    $rootScope.currentUser = data;
                } else {
                    $rootScope.authenticated = false;
                }
            }).error(function () {
                $rootScope.authenticated = false;
            });

            return $q.defer().promise;
        };

        function authenticate(credentials, callback) {
            var headers = credentials ? {
                authorization: "Basic " + btoa(credentials.email + ":" + credentials.password)
            } : {};
            $http.get('user', {
                headers: headers
            }).success(function (data) {
                if (data.name) {
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
        };
    };
})();