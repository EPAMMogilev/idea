(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('profileCtrl', profileCtrl);

    profileCtrl.$inject = ['$scope', '$window', '$modal'];

    function profileCtrl($scope, $window, $modal) {

        $scope.profile = {username: $scope.currentUser.username, email: $scope.currentUser.email, imageUrl: "images/logo.png"};

    }


})();
