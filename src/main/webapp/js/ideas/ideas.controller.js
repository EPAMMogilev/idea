(function () {
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', '$location', '$rootScope', 'ideasFactory', 'tagsFactory', 'mapGeoService'];

    function ideasCtrl($scope, $location, $rootScope, ideasFactory, tagsFactory, mapGeoService) {


        var vm = this;
        vm.userId = null;
        var mapObject;
        $scope.geoObjects = null;
        $scope.criteria = null;
        $scope.mapCenter = mapGeoService.getMapCenter();
        $scope.mapZoom = mapGeoService.getMapDefaultZoom();

        $scope.afterMapInit = function (map) {
            mapObject = map;
        };

        vm.tabAll = 'TAB_ALL';
        vm.query = null;

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

        vm.tag = null;

        activate();

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

        function getList(listObj, update) {
            if (update)
                listObj.params.page++;

            var promise = ideasFactory.getPage(listObj.params, vm.userId, vm.tag, vm.query).then(function (ideas) {
                if (ideas && ideas[ideas.length - 1]) {
                    listObj.list = listObj.list.concat(ideas);
                    $scope.geoObjects = mapGeoService.generateGeoObjects(listObj.list);
                } else {
                    //if (update)
                    //  listObj.params.page--;

                    if (vm.tag != null)
                        listObj.list = ideas;
                }
            });

            return promise;

        }

        vm.update = function (listObj) {
            return getList(listObj, true);
        }



        vm.selectByCategory = function (tag) {
            vm.tag = tag;

            vm.latest.params.page = 0;
            vm.popular.params.page = 0;

            getList(vm.popular);
            getList(vm.latest);

            mapObject.setCenter(mapGeoService.getMapCenter(), mapGeoService.getMapDefaultZoom(), {
                checkZoomRange: false,
                duration: 1000
            });

        }

        vm.selectByQuery = function () {
            vm.latest.params.page = 0;
            vm.popular.params.page = 0;

            getList(vm.popular);
            getList(vm.latest);
        }

        $scope.$on('query-update', function (event, query) {
            vm.query = query;
            vm.selectByQuery();
        });

        vm.details = function (idea) {
            var ideaDetail = {
                id: idea.id
            };

            console.log('Go to Details'); /*RemoveLogging:skip*/
            $state.go('ideaDetails', {
                'idea': angular.toJson(ideaDetail)
            });
        };

        $scope.mouseenter = function (e) {
            e.get('target').options.set('preset', 'islands#greenIcon');
        };
        $scope.mouseleave = function (e) {
            e.get('target').options.unset('preset');
        };
    }

})();