(function() {
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', 'ideasFactory', 'tagsFactory', 'mapGeoService'];

    function ideasCtrl($scope, ideasFactory, tagsFactory, mapGeoService) {

        var vm = this;
        $scope.geoObjects = null;// = new Array();
        /*
        $scope.updateGeoObjects = function(ideas){
            if(ideas){
                for(var i=0; i<ideas.length; i++){
                    var oldObject = ideas[i];

                    if(oldObject && oldObject.longitude && oldObject.latitude){
                        var newGeoObject = {
                            geometry: {
                                        type: 'Point',
                                        coordinates: [oldObject.longitude,oldObject.latitude]
                                    },
                            properties: {
                                balloonContent: oldObject.title,
                                clusterCaption: 'идея #' + oldObject.id
                            }
                        };
                        //add 2 array
                        $scope.geoObjects.push(newGeoObject);
                    }//if
                }//for
            }//if
        };//updateGeoObjects
        */
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
