(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('detailsCtrl', detailsCtrl);

    detailsCtrl.$inject = ['$scope', '$window', '$state', 'ideaDetails'];

    function detailsCtrl($scope, $window, $state, ideaDetails) {

        this.data = ideaDetails;
        //$scope.data = ideaDetails;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.edit = function(){
			$state.go('ideaUpdate', { 'idea': angular.toJson(ideaDetails) });
		};

		$scope.remove = function(){
            $window.location.href = '#home';
		};

		//init function: load map point
            $scope.init = function(){
            var myMap = new ymaps.Map("map", {
                //[53.894617; 30.331014]
                center: [30.331014, 53.894617],
                zoom: 11
            });

            if(this.detailsCtrl.data && this.detailsCtrl.data.latitude && this.detailsCtrl.data.longitude){
                /*var geoPoint = new ymaps.Placemark([this.detailsCtrl.data.latitude, this.detailsCtrl.data.longitude], null,{
                    preset: "islands#greenStretchyIcon"
                });*/
                var geoPoint = new ymaps.Placemark([this.detailsCtrl.data.longitude, this.detailsCtrl.data.latitude], null,{
                    preset: "islands#greenStretchyIcon"
                });

                myMap.geoObjects.add(geoPoint);
            }//if
		}
    }


})();