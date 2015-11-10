(function () {
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', '$location', '$rootScope', 'ideasFactory', 'tagsFactory', 'mapGeoService'];

    function ideasCtrl($scope, $location, $rootScope, ideasFactory, tagsFactory, mapGeoService) {


        var vm = this;
        var mapObject;
        vm.userId = null;
        vm.tabAll = 'TAB_ALL';
        vm.query = null;
        vm.tag = null;
        vm.popular = {
            list: [],
            params: {
                page: 0,
                size: 5,
                sort: 'rating,desc'
            }
        };
        vm.latest = {
            list: [],
            params: {
                page: 0,
                size: 5,
                sort: 'creationTime,desc'
            }
        };

        $scope.geoObjects = null;
        $scope.criteria = null;
        $scope.mapCenter = mapGeoService.getMapCenter();
        $scope.mapZoom = mapGeoService.getMapDefaultZoom();

        $scope.afterMapInit = function (map) {
            mapObject = map;
        };

        $scope.$on('query-update', function (event, query) {
            vm.query = query;
            vm.selectByQuery();
        });

        $scope.mouseenter = function (e) {
            e.get('target').options.set('preset', 'islands#greenIcon');
        };

        $scope.mouseleave = function (e) {
            e.get('target').options.unset('preset');
        };

        function onMyIdeasPage() {
            var result = false;
            if ($location.path().indexOf("/myideas") !== -1) {
                result = true;
            }
            return result;
        }

        function changeTabAllIdeas() {
            if (onMyIdeasPage()) {
                vm.tabAll = 'TAB_ALL_MY';
            }
        }

        function activate() {
            if (onMyIdeasPage()) {
                vm.userId = $rootScope.currentUser.id;
            } else {
                vm.userId = null;
            }

            getList(vm.popular);
            getList(vm.latest);



            vm.query = null;
            changeTabAllIdeas();
            $rootScope.$broadcast('query-clean');
        }


        function getList(listObject, update) {
            return ideasFactory.getPage(listObject.params, vm.userId, vm.tag, vm.query).then(function (ideas) {
                if (ideas && update) {
                    listObject.list = listObject.list.concat(ideas);
                } else {
                    listObject.list = ideas;
                }
                $scope.geoObjects = mapGeoService.generateGeoObjects(listObject.list);
            });
        }

        vm.update = function (listObject) {
            listObject.params.page++;
            return getList(listObject, true);
        }

        vm.selectByCategory = function (tag) {
            vm.tag = tag;
            vm.popular.params.page = 0;
            vm.latest.params.page = 0;

            getList(vm.popular);
            getList(vm.latest);
        }

        vm.selectByQuery = function () {
            vm.popular.params.page = 0;
            vm.latest.params.page = 0;

            getList(vm.popular);
            getList(vm.latest);
        }

        vm.details = function (idea) {
            var ideaDetail = {
                id: idea.id
            };

            console.log('Go to Details'); /*RemoveLogging:skip*/
            $state.go('ideaDetails', {
                'idea': angular.toJson(ideaDetail)
            });
        };

        activate();

    }

})();