(function () {
    'use strict';

    angular
        .module('ideaFactories')
        .factory('AuthenticationService', AuthenticationService);

    AuthenticationService.$inject = ['$http', '$cookieStore', '$rootScope', 'usersFactory','md5'];
    function AuthenticationService($http, $cookieStore, $rootScope, usersFactory, md5) {
        var service = {};

        service.Login = Login;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;

        return service;

        function Login(email, password, callback) {

        var response;
            usersFactory.getUserByEmailAndPassword(email, password).then(function (user) {
                response = { success: true };
                callback(response);}
            ,function() {
                response = { success: false, message: 'Email or password is incorrect' };
                callback(response)
                });



        }

        function SetCredentials(email, password) {
            var authdata = md5.createHash((email + ':' + password) || '');

            $rootScope.globals = {
                currentUser: {
                    email: email,
                    authdata: authdata
                }
            };

            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookieStore.put('globals', $rootScope.globals);
        }

        function ClearCredentials() {
            $rootScope.globals = {};
            $cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
        }
    }

})();
