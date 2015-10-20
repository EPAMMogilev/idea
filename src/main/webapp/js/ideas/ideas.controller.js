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
		$scope.mapCenter = mapGeoService.getMapCenter();
		
		vm.tabAll = 'TAB_ALL';
		vm.query = null;

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

		function onMyIdeasPage () {
			var result = false;
			if ($location.path().indexOf("/myideas") !== -1){
				result = true;
			}
			return result;
		}
		
		function changeTabAllIdeas () {
			if (onMyIdeasPage()) {
				vm.tabAll = 'TAB_ALL_MY';
			}
		}
		
        function activate() {
            if(onMyIdeasPage()) {
            	userId = $rootScope.currentUser.id;
            } else {
            	userId = null;
            }
            vm.query = null;
            changeTabAllIdeas();
            $rootScope.$broadcast('query-clean');
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

			ideasFactory.getPage(vm.paramsForPopular, userId, vm.tag, vm.query).then(function (ideas) {
				vm.popular = ideas;
				$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
			});

			ideasFactory.getPage(vm.paramsForLatest, userId, vm.tag, vm.query).then(function (ideas) {
				vm.latest = ideas;
			});
		}

		ideasFactory.getPage(vm.paramsForPopular, userId, vm.tag, vm.query).then(function (ideas) {
			vm.popular = ideas;

			$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
		});

		ideasFactory.getPage(vm.paramsForLatest, userId, vm.tag, vm.query).then(function (ideas) {
			vm.latest = ideas;

			$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
		});

		$scope.$on('ideas-update', function() {
			ideasFactory.getIdeas().then(function (ideas) {
			vm.ideas = ideas;
			$scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
			});
		});

		$scope.$on('query-update', function(event, query) {
			vm.query = query;
			vm.selectByQuery();
		});

		vm.details = function(idea){
			var ideaDetail = {
				id:idea.id
			};

			console.log('Go to Details');/*RemoveLogging:skip*/
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