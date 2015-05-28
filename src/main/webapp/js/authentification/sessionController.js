(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('sessionCtrl', sessionCtrl);

    sessionCtrl.$inject = ['sessionService', 'sessionFactory', '$location'];

    function sessionCtrl(sessionService, sessionFactory, $location) {

         var vm = this;
//      vm.currentUser= $rootScope.globals.currentUser;
//      vm.authenticated = false;
//      if($rootScope.globals.currentUser){
//         vm.authenticated = true;}

     
         if(sessionService.getSessionId() && sessionService.getSessionId() != ''){
             vm.email =  sessionService.getUser().email;
             vm.authenticated = true;
         }


         vm.logout = function () {
            sessionFactory.deleteSession(sessionService.getSessionId()).then(function (value) {
                sessionService.setSessionId('');
                sessionService.setUser('');
                vm.authenticated = false;
                $location.path("home");
            }, function(error) {
                console.log(error); alert(error);
                sessionService.setSessionId('');
                sessionService.setUser('');
                vm.authenticated = false;
                $location.path("home");
           });
         };

    }



})();
