(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('detailsCtrl', detailsCtrl);

    detailsCtrl.$inject = ['$scope', '$window', 'ideaDetails'];

    function detailsCtrl($scope, $window, ideaDetails) {

        this.data = ideaDetails;

		$scope.back = function(){
            $window.location.href = '#home';
		};
    }


})();