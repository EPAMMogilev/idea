(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('showIdeaStateUpdate', showIdeaStateUpdate);

	function showIdeaStateUpdate() {
		return {
			restrict: 'E',
			replace: true,
			templateUrl:'templates/ideaStateUpdate.tpl.html'
		}
	}

})();
