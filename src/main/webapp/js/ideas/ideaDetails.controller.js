(function(){
	'use strict';

	angular
		.module('app.controllers')
		.controller('detailsCtrl', detailsCtrl);

	detailsCtrl.$inject = ['$scope', '$window', '$state', '$rootScope', 'ideasFactory', 'ideaDetails', 'mapGeoService', 'ideaDetailsService'];

	function detailsCtrl($scope, $window, $state, $rootScope, ideasFactory, ideaDetails, mapGeoService, ideaDetailsService) {

		$scope.idea = ideaDetails;
		$scope.likedUsersList = null;
		$scope.data = null;
		$scope.myMap = null;
    	$scope.comments = null;

		$scope.isAuthor = function(idea) {
			return ideasFactory.isUserAuthorOfIdea($rootScope.currentUser, idea);
		};

		this.promises = ideasFactory.getIdeaById($scope.idea.id).then(
			//success
			function( value )
			{
				$scope.data = value;
				if($scope.data != null) {
					$scope.likedUsersList = ideaDetailsService.getlikedUsersListAsString($scope.data);
				}

				//set geo point
				if($scope.data && $scope.data.latitude && $scope.data.longitude){
					var geoPoints = {
						latitude: $scope.data.latitude,
						longitude: $scope.data.longitude
					};
					mapGeoService.setGeoCoordsSimpleMap($scope.myMap, geoPoints);
				}//if

				//set image
				if($scope.data != null && $scope.data.imageUrl == null){
				$scope.data.imageUrl = "images/300x300.png";
				}//if
			}
		);

		ideasFactory.getCommentsByIdeaId($scope.idea.id).then(
				//success
				function(comments)
				{
					$scope.comments = comments;
				}
			);

		$scope.edit = function(){
			$state.go('ideaUpdate', { 'idea': angular.toJson($scope.idea) });
		};

		$scope.remove = function(){
			ideasFactory.removeIdea($scope.idea).then(
				function( value )
				{
					alert("Удалено");
					$state.go('home');
				});
		};

		$scope.changeMapVisibility = function(){
			$("#map").toggle();
		};

		$scope.changeLike = function() {
			ideasFactory.changeIdeaLike($scope.idea.id).then(
				function (idea) {
					$scope.data.rating = idea.rating;
					$scope.data.liked = !$scope.data.liked;
					$scope.likedUsersList = ideaDetailsService.getlikedUsersListAsString(idea);
				}
			);
		};

		//init function: load map point
		$scope.init = function(){
			$scope.myMap = new ymaps.Map("map", {
				//[53.894617; 30.331014]
				center: [30.331014, 53.894617],
				zoom: 11
			});
		}
	}


})();