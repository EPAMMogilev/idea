(function() {
	'use strict';
	angular
		.module('app.controllers')
		.controller('ideasCtrl', ideasCtrl);

	ideasCtrl.$inject = ['$scope', 'ideasService', 'ideasFactory', 'tagsFactory', 'mapGeoService'];

	function ideasCtrl($scope, ideasService, ideasFactory, tagsFactory, mapGeoService) {

		var vm = this;
		$scope.geoObjects = null;
		$scope.criteria = null;

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

		this.loadPageForPopular = function(){
			vm.paramsForPopular.page++;
			var promiseResponse = ideasService.getPage(vm.paramsForPopular, vm.tag);
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
			var promiseResponse = ideasService.getPage(vm.paramsForLatest, vm.tag);
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

			ideasService.getPage(vm.paramsForPopular, vm.tag).then(function (ideas) {

				vm.popular = ideas;
				$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
			});

			ideasService.getPage(vm.paramsForLatest, vm.tag).then(function (ideas) {
				vm.latest = ideas;

			});
		}

		ideasService.getPage(vm.paramsForPopular, vm.tag).then(function (ideas) {
			vm.popular = ideas;

			$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
		});

		ideasService.getPage(vm.paramsForLatest, vm.tag).then(function (ideas) {
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