(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('showIdea', showIdea);
		
	showIdea.$inject = ['ideasFactory', '$state'];

	function showIdea(ideasFactory, $state) {
		return {
			restrict: 'E',
			replace: true,
			scope: {
				photo: '@',
				idea: '=',			
				ngModel: '=',
				details: '&',				
				changeRating: '&'				
			},
			link: function(scope, element, attrs) {
				scope.photo = 'images/photo.gif';
				scope.details = function() {
					var ideaDetail = {
						id: scope.idea.id/*,
						title:scope.idea.title,
						description:scope.idea.description,
						createdAt:scope.idea.createdAt,
					    	latitude:scope.idea.latitude,
					    	longitude:scope.idea.longitude
*/
					};
					$state.go('ideaDetails', { 'idea': angular.toJson(ideaDetail) });
				};
				scope.changeRating = function (mark) {
					scope.idea.rating += mark;
					ideasFactory.updateIdea(scope.idea);
				};
			},
			templateUrl:'templates/idea.tpl.html'
		}
	}

})();
