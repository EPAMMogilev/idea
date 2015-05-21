(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('showIdea', showIdea);

	function showIdea() {
		return {
			restrict: 'E',
			replace: true,
			scope: {
				idea: '@',
				photo: '@',				
				title: '@',
				description: '@',
				created: '@',
				author: '@',			
				tag: '@',
				rtng: '@',
				ngModel: '=',
				details: '&',				
				rate: '&'				
			},
			templateUrl:'templates/idea.tpl.html'
		}
	}

})();
