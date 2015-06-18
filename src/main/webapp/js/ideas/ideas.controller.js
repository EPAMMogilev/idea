(function() {
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', 'ideasFactory', 'tagsFactory', 'mapGeoService'];

    function ideasCtrl($scope, ideasFactory, tagsFactory, mapGeoService) {

        var vm = this;
        $scope.geoObjects = null;

        $scope.criteria = null;

        vm.selectByCategory =function (tag) {
          tagsFactory.getIdeasByTag(tag).then(function (ideas) {
            vm.ideas = ideas;

            //$scope.updateGeoObjects(ideas);
            $scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
        })
        };

        ideasFactory.getIdeas().then(function (ideas) {
            vm.ideas = ideas;

            //$scope.updateGeoObjects(ideas);
            $scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
        });

        $scope.$on('ideas-update', function() {
            ideasFactory.getIdeas().then(function (ideas) {
                vm.ideas = ideas;

            //$scope.updateGeoObjects(ideas);
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

        //gui effects
        $scope.mouseenter=function(e){
            e.get('target').options.set('preset', 'islands#greenIcon');
        };
        $scope.mouseleave=function(e){
            e.get('target').options.unset('preset');
        };
    }

})();
