(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('showComment', showComment);

	showComment.$inject = ['ideaDetailsService'];

	function showComment(ideaDetailsService) {
		return {
			restrict: 'A',
			replace: false,
			scope: {
				comment: '=',
				isAuthenticated: '='
			},
			link: function(scope, element, attrs) {
				scope.likedUsersList = ideaDetailsService.getlikedUsersListAsString(scope.comment);
			},
			templateUrl:'templates/comment.tpl.html'
		}
	}

})();
