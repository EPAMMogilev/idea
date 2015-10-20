(function(){
    'use strict';

	angular
	    .module('app.controllers')
	    .controller('loadModalWindow', loadModalWindow);

    loadModalWindow.$inject = ['$scope', '$rootScope', '$modalInstance', 'mapGeoService', 'geo'];

    function loadModalWindow($scope, $rootScope, $modalInstance, mapGeoService, geo){
		
    	$scope.caption = 'Идет загрузка изображения. Дождитесь окончания...';
    	
    	$scope.mapModal = null;
    	
    	$scope.close = function() {
			$modalInstance.close();
        }
          		  
    	$scope.initModal = function(){
			$scope.mapModal = new ymaps.Map("mapModal", {
				center: [30.331014, 53.894617],
				zoom: 11
			});
			
			mapGeoService.setGeoCoordsSimpleMap($scope.mapModal, geo);
		}
    	
	}
    
    
})();
