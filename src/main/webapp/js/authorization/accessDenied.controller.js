(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('accessDenied', accessDenied);

    accessDenied.$inject = ['$scope', '$window'];

    function accessDenied($scope, $window) {
		$scope.back = function(){
			$window.location.href = '#home';
		};
    }


})();
