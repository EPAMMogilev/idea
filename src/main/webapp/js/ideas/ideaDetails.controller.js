(function(){
	'use strict';

	angular
		.module('app.controllers')
		.controller('detailsCtrl', detailsCtrl);

	detailsCtrl.$inject = ['$scope', '$window', '$state', '$rootScope', 'ideasFactory', 'ideaDetails', 'mapGeoService'];

	function detailsCtrl($scope, $window, $state, $rootScope, ideasFactory, ideaDetails, mapGeoService) {

		$scope.idea = ideaDetails;
		$scope.likedUsersList = null;
		$scope.data = null;
		$scope.myMap = null;

		$scope.isAuthor = function(idea) {
			return ideasFactory.isUserAuthorOfIdea($rootScope.currentUser, idea);
		};

		this.promises = ideasFactory.getIdeaById($scope.idea.id).then(
			//success
			function( value )
			{
				$scope.data = value;
				if($scope.data != null) {
					$scope.likedUsersList = getlikedUsersListAsString();
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

		function getlikedUsersListAsString(){
			var users = [];
			for(var i = 0; i < $scope.data.likedUsers.length; i++) {
				users.push($scope.data.likedUsers[i].username);
			}
			return users.join(", ");
		}

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