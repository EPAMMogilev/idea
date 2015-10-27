(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('showIdeaState', showIdeaState);

	function showIdeaState() {
		return {
			restrict: 'E',
			replace: true,
			templateUrl:'templates/ideaState.tpl.html'
		}
	}

})();
