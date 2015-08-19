(function() {
	'use strict';
	angular
		.module('app.controllers')
		.controller('ideasCtrl', ideasCtrl);

	ideasCtrl.$inject = ['$scope', '$location', '$rootScope', 'ideasFactory', 'tagsFactory', 'mapGeoService'];

	function ideasCtrl($scope, $location, $rootScope, ideasFactory, tagsFactory, mapGeoService) {

		var vm = this;
		var userId = null;
		$scope.geoObjects = null;
		$scope.criteria = null;
		$scope.query = null;

		vm.paramsForPopular = {
			page: 0,
			size: 5,
			sort: 'rating,desc'
		};
		vm.paramsForLatest = {
			page: 0,
			size: 5,
			sort: 'creationTime,desc'
		};

		vm.popular = [];
		vm.latest = [];
		vm.tag = null;

		activate();

        function activate() {
            if($location.path().indexOf("/myideas") !== -1) {
            	userId = $rootScope.currentUser.id;
            } else {
            	userId = null;
            }
        }

		this.loadPageForPopular = function(){
			vm.paramsForPopular.page++;
			var promiseResponse = ideasFactory.getPage(vm.paramsForPopular, userId, vm.tag, null);
			promiseResponse.then(function (ideas) {
				if (ideas) {
					vm.popular = vm.popular.concat(ideas);
				}
			$scope.geoObjects = mapGeoService.generateGeoObjects(vm.popular);
			});
			return promiseResponse;
		};

		this.loadPageForLatest = function(){
			vm.paramsForLatest.page++;
			var promiseResponse = ideasFactory.getPage(vm.paramsForLatest, userId, vm.tag, null);
			promiseResponse.then(function (ideas) {
				if (ideas) {
					vm.latest = vm.latest.concat(ideas);
				}
				$scope.geoObjects = mapGeoService.generateGeoObjects(vm.latest);
			});
			return promiseResponse;
		};

		vm.selectByCategory = function(tag) {
			vm.tag = tag;

			vm.paramsForLatest.page = 0;
			vm.paramsForPopular.page = 0;

			ideasFactory.getPage(vm.paramsForPopular, userId, vm.tag, null).then(function (ideas) {
				vm.popular = ideas;
				$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
			});

			ideasFactory.getPage(vm.paramsForLatest, userId, vm.tag, null).then(function (ideas) {
				vm.latest = ideas;
			});
		}

		vm.selectByQuery = function() {
			vm.paramsForLatest.page = 0;
			vm.paramsForPopular.page = 0;

			ideasFactory.getPage(vm.paramsForPopular, userId, vm.tag, $scope.query).then(function (ideas) {
				vm.popular = ideas;
				$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
			});

			ideasFactory.getPage(vm.paramsForLatest, userId, vm.tag, $scope.query).then(function (ideas) {
				vm.latest = ideas;
			});
		}

		ideasFactory.getPage(vm.paramsForPopular, userId, vm.tag, $scope.query).then(function (ideas) {
			vm.popular = ideas;

			$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
		});

		ideasFactory.getPage(vm.paramsForLatest, userId, vm.tag, $scope.query).then(function (ideas) {
			vm.latest = ideas;

			$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
		});

		$scope.$on('ideas-update', function() {
			ideasFactory.getIdeas().then(function (ideas) {
			vm.ideas = ideas;
			$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
			});
		});

		vm.details = function(idea){
			var ideaDetail = {
				id:idea.id
			};

			console.log('Go to Details');
			$state.go('ideaDetails', { 'idea': angular.toJson(ideaDetail) });
		};

		$scope.mouseenter=function(e){
			e.get('target').options.set('preset', 'islands#greenIcon');
		};
		$scope.mouseleave=function(e){
			e.get('target').options.unset('preset');
		};
	}

})();