(function(){
	'use strict';

	angular
		.module('app.controllers')
		.controller('ideasSearchCtrl', ideasSearchCtrl);

	ideasSearchCtrl.$inject = ['$rootScope', '$scope', '$location'];

	function ideasSearchCtrl($rootScope, $scope, $location) {

		var vm = this;
		$scope.query = null;

		vm.changeQuery = function() {
			$rootScope.$broadcast('query-update', $scope.query);
		}

		vm.isSearchVisible = function() {
			if($location.path().indexOf("/myideas") !== -1 || $location.path().indexOf("/home") !== -1) {
				return true;
			} else {
				return false;
			}
		}

		$scope.$on('query-clean', function() {
			$scope.query = null;
		});
	}
})();