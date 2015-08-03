(function(){
	'use strict';

	angular
		.module('app.controllers')
		.controller('detailsCtrl', detailsCtrl);

	detailsCtrl.$inject = ['$scope', '$window', '$state', '$rootScope', 'ideasFactory', 'ideaDetails', 'mapGeoService'];

	function detailsCtrl($scope, $window, $state, $rootScope, ideasFactory, ideaDetails, mapGeoService) {

		$scope.idea = ideaDetails;
		$scope.data = null;
		$scope.myMap = null;

		$scope.isAuthor = function (idea) {
			if(idea && $rootScope.currentUser)
			{
				if ($rootScope.currentUser.id == idea.author.id) {
					return true;
				}
			}
			return false;
		}

		this.promises = ideasFactory.getIdeaById($scope.idea.id).then(
			//success
			function( value )
			{
				$scope.data = value;

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

		//this.data = ideaDetails;

		$scope.back = function(){
			$window.location.href = '#home';
		};

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