(function(){
    'use strict';

	angular
	    .module('app.controllers')
	    .controller('modalLargeMap', modalLargeMap);

	modalLargeMap.$inject = ['$scope', '$rootScope', '$modalInstance', 'mapGeoService', 'geo'];

    function modalLargeMap($scope, $rootScope, $modalInstance, mapGeoService, geo){
		
    	$scope.mapModal = null;
    	
    	$scope.close = function() {
			$modalInstance.close();
        }
          		  
    	$scope.initModal = function(){
			$scope.mapModal = new ymaps.Map("mapModal", {
				center: mapGeoService.getMapCenter(),
				zoom: 11
			});
			
			mapGeoService.setGeoCoordsSimpleMap($scope.mapModal, geo);
		}
    	
	}
    
    
})();