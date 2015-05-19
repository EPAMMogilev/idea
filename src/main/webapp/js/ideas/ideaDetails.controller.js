(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('detailsCtrl', detailsCtrl);

    detailsCtrl.$inject = ['$scope', '$window', 'detailsService'];

    function detailsCtrl($scope, $window, detailsService) {

        this.data = detailsService.getData();

		$scope.back = function(){
            $window.location.href = '#home';
		};
    }


})();