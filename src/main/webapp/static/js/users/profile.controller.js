(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('profileCtrl', profileCtrl);

    profileCtrl.$inject = ['$scope', '$window', '$modal', 'usersFactory', 'sessionService'];

    function profileCtrl($scope, $window, $modal, usersFactory, sessionService) {

        var imageUrl = $scope.currentUser.imageUrl ? $scope.currentUser.imageUrl : "images/logo.png"

        $scope.profile = {id: $scope.currentUser.id, username: $scope.currentUser.username, email: $scope.currentUser.email, password: $scope.currentUser.password, imageUrl: imageUrl};

        $scope.back = function(){
            $window.location.href = '#home';
        };

		$scope.doWork = function(data) {
            if ($scope.profileForm.$invalid) {return;}

            var request = {
                id: data.id,
                email: data.email,
                password: data.password
            };

            usersFactory.updateUser(request).then(
               //success
               function( value ) {
                   sessionService.logout();
               },
               //error
               function( error ){
                   alert("Ошибка обновления профайла пользователя: " + error.statusText);
               }
           );
        };

    }


})();
