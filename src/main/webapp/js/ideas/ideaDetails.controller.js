(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('detailsCtrl', detailsCtrl);

    detailsCtrl.$inject = ['$scope', '$window', '$state', 'ideasFactory', 'ideaDetails', 'mapGeoService'];

    function detailsCtrl($scope, $window, $state, ideasFactory, ideaDetails, mapGeoService) {

        $scope.idea = ideaDetails;
        $scope.data = null;

        $scope.myMap = null;

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
            $window.location.href = '#home';
		};

		//init function: load map point
            $scope.init = function(){
            $scope.myMap = new ymaps.Map("map", {
                //[53.894617; 30.331014]
                center: [30.331014, 53.894617],
                zoom: 11
            });
            /*
            if($scope.data && $scope.data.latitude && $scope.data.longitude){

                var geoPoint = new ymaps.Placemark([$scope.data.longitude, $scope.data.latitude], null,{
                    preset: "islands#greenStretchyIcon"
                });

                myMap.geoObjects.add(geoPoint);
            }//if*/
		}
    }


})();