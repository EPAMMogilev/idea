(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('authenticationCtrl', authenticationCtrl);

    authenticationCtrl.$inject = ['$rootScope','AuthenticationService'];

    function authenticationCtrl($rootScope,AuthenticationService) {

         var vm = this;
//      vm.currentUser= $rootScope.globals.currentUser;
//      vm.authenticated = false;
//      if($rootScope.globals.currentUser){
//         vm.authenticated = true;}

     
         vm.logout = function () {
            AuthenticationService.ClearCredentials();
            vm.authenticated = false;
         };
         $rootScope.$watch('globals.currentUser', function(newVal) {
            vm.currentUser = newVal;
            if(newVal){
                vm.authenticated = true;}

         });

    }



})();
