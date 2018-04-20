(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('showComment', showComment);

	showComment.$inject = ['usersService', 'commentsFactory'];

	function showComment(usersService, commentsFactory) {
		return {
			restrict: 'A',
			replace: false,
			scope: {
				comment: '=',
				changeLike: '&',
				isAuthenticated: '='
			},
			link: function(scope, element, attrs) {
				scope.photo = (scope.comment.author.imageUrl) ? scope.comment.author.imageUrl : 'images/no_user_photo.png';
				scope.likedUsersList = usersService.getlikedUsersListAsString(scope.comment);
				scope.changeLike = function() {
					commentsFactory.changeCommentLike(scope.comment.id).then(
						function (comment) {
							scope.comment.rating = comment.rating;
							scope.comment.liked = !scope.comment.liked;
							scope.likedUsersList = usersService.getlikedUsersListAsString(comment);
						}
					);
				}
			},
			templateUrl:'templates/comment.tpl.html'
		}
	}

})();
