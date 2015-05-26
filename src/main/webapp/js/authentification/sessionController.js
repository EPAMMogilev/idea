(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('sessionCtrl', sessionCtrl);

    sessionCtrl.$inject = ['sessionService'];

    function sessionCtrl(sessionService) {

         var vm = this;
//      vm.currentUser= $rootScope.globals.currentUser;
//      vm.authenticated = false;
//      if($rootScope.globals.currentUser){
//         vm.authenticated = true;}

     
         if(sessionService.getSessionId() && sessionService.getSessionId() != ''){
             vm.email =  sessionService.getUser();
             vm.authenticated = true;
         }


         vm.logout = function () {
            sessionService.setSessionId('');
            sessionService.setUser('');
            vm.authenticated = false;
            document.location.reload(true);
         };

    }



})();
