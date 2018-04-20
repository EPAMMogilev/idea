(function(){
	'use strict';

	angular
		.module('app.controllers')
		.controller('detailsCtrl', detailsCtrl);

	detailsCtrl.$inject = ['$scope', '$window', '$modal', '$state', '$rootScope', 'ideasFactory', 'ideaDetails', 'mapGeoService', 'usersService', 'commentsFactory'];

	function detailsCtrl($scope, $window, $modal, $state, $rootScope, ideasFactory, ideaDetails, mapGeoService, usersService, commentsFactory) {
		$scope.modalInstance = null;
		$scope.ideaDetails = ideaDetails;
		$scope.likedUsersList = null;
		$scope.idea = null;
		$scope.myMap = null;

		$scope.commentBody = null;

    	var vm = this;
    	vm.comments = null;
		vm.paramsForComments = {
				page: 0,
				size: 10,
				sort: 'creationTime,desc'
			};

		$scope.loadModalLargeMap = function(){
			$scope.modalInstance = $modal.open({
				animation: true,
				templateUrl: 'pages/public/largeMap.html',
				controller: 'modalLargeMap',
				resolve: {
					geo: function () {
			            return $scope.geoPoints;
			        }
			    }	
			  });
		};//loadModalLargeMap
		
		this.loadPageForComments = function(){
			vm.paramsForComments.page++;
			var promiseResponse = commentsFactory.getCommentsPageByIdeaId(vm.paramsForComments, $scope.ideaDetails.id);
			promiseResponse.then(function (comments) {
				if (comments) {
					vm.comments = vm.comments.concat(comments);
				}
			});
			return promiseResponse;
		};

		$scope.isAuthor = function(ideaDetails) {
			return ideasFactory.isUserAuthorOfIdea($rootScope.currentUser, ideaDetails);
		};

		this.promises = ideasFactory.getIdeaById($scope.ideaDetails.id).then(
			//success
			function( value )
			{
				$scope.idea = value;
				if($scope.idea != null) {
					$scope.likedUsersList = usersService.getlikedUsersListAsString($scope.idea);
				}

				//set geo point
				if($scope.idea && $scope.idea.latitude && $scope.idea.longitude){
					$scope.geoPoints = {
						latitude: $scope.idea.latitude,
						longitude: $scope.idea.longitude
					};
					mapGeoService.setGeoCoordsSimpleMap($scope.myMap, $scope.geoPoints);
				}//if

				//set image
				if($scope.idea != null && $scope.idea.imageUrl == null){
				$scope.idea.imageUrl = "images/300x300.png";
				}//if
			}
		);

		commentsFactory.getCommentsPageByIdeaId(vm.paramsForComments, $scope.ideaDetails.id).then(
			//success
			function(comments)
			{
				vm.comments = comments;
			}
		);

		$scope.edit = function(){
			$state.go('ideaUpdate', { 'idea': angular.toJson($scope.ideaDetails) });
		};

		$scope.remove = function(){
			ideasFactory.removeIdea($scope.ideaDetails).then(
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
			ideasFactory.changeIdeaLike($scope.ideaDetails.id).then(
				function (ideaDetails) {
					$scope.idea.rating = ideaDetails.rating;
					$scope.idea.liked = !$scope.idea.liked;
					$scope.likedUsersList = usersService.getlikedUsersListAsString(ideaDetails);
				}
			);
		};

		//init function: load map point
		$scope.init = function(){
			$scope.myMap = new ymaps.Map("map", {
				center: mapGeoService.getMapCenter(),
				zoom: 11
			});
		}

		$scope.addComment = function(){
			var request = {
					id: null,
					body: $scope.commentBody,
					subject: {
						id: $scope.ideaDetails.id
					}
				};
			commentsFactory.createComment(request).then(
				function (addedComment) {
					vm.comments.unshift(addedComment);
					$scope.commentBody = null;
				}
			);
		};

		$scope.cancelAddComment = function(){
			$scope.commentBody = null;
		};
		
	}

})();