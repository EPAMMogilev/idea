(function () {
    'use strict';

    angular
        .module('app.controllers')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$http', '$location', '$rootScope'];
    function LoginController($scope, $http, $location, $rootScope) {
     var vm = this;
     vm.login = login;
        var authenticate = function(credentials, callback) {
            var headers = credentials ? {authorization : "Basic "
                + btoa(credentials.email + ":" + credentials.password)
            } : {};
            $http.get('user', {headers : headers}).success(function(data) {
            
              if (data.name) {
                $rootScope.authenticated = true;
                $rootScope.currentUser = data;
              } else {
               
                $rootScope.authenticated = false;
              }
              callback && callback();
            }).error(function() {
              $rootScope.authenticated = false;
              callback && callback();
            });

          }

          authenticate();
          vm.credentials = {};
          function login() {
              authenticate(vm.credentials, function() {
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
