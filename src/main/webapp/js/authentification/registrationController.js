(function () {
    'use strict';

    angular
        .module('app.controllers')
        .controller('RegistrationController', RegistrationController);

    RegistrationController.$inject = ['$scope', '$http', '$window', '$rootScope', 'usersFactory'];
    function RegistrationController($scope, $http,  $window, $rootScope, usersFactory) {

        var vm = this;
        vm.registrationRequest = registrationRequest;
        vm.registration = registration;
        vm.credentials = {};
        function registration (){
        var request = {
            username:vm.credentials.username,
            email:vm.credentials.email,
            password: vm.credentials.password
        };
        vm.registrationRequest(request);

        };

        function registrationRequest (user){
         console.log(vm.credentials);
                usersFactory.createUser(user).then(
                   //success
                   function( value )
                   {
                    $window.location.href = '#home';
                   },
                   //error
                   function( error ){
                    alert("Ошибка создания пользователя: " + error.statusText);
                   }
                 );
        };
    }
})();
