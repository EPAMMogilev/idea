(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('detailsCtrl', detailsCtrl);

    detailsCtrl.$inject = ['$scope', '$window', '$state', 'ideaDetails'];

    function detailsCtrl($scope, $window, $state, ideaDetails) {

        this.data = ideaDetails;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.edit = function(){
			$state.go('ideaUpdate', { 'idea': angular.toJson(ideaDetails) });
		};

		$scope.remove = function(){
            $window.location.href = '#home';
		};
    }


})();