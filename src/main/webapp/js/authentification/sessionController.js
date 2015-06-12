(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('sessionCtrl', sessionCtrl);

    sessionCtrl.$inject = ['$rootScope', '$http'];

    function sessionCtrl($rootScope, $http) {

         var vm = this;

                 var authenticate = function(callback) {
                     $http.get('user').success(function(data) {

                       if (data.name) {
                       console.log(data.name);
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

                   console.log("hello");;
                   authenticate();
    }



})();
