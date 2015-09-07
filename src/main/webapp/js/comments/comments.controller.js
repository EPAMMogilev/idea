(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('commentsCtrl', commentsCtrl);

    commentsCtrl.$inject = ['$scope', 'commentsFactory'];

    function commentsCtrl($scope, commentsFactory) {

    	$scope.comments = null;
        this.promises = commentsFactory.getCommentsByIdeaId($scope.idea.id).then(
			//success
			function(comments)
			{
				$scope.comments = comments;
			}
		);
    }
})();
