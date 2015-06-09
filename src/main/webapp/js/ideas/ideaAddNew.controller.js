(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('addNewIdea', addNewIdea);

    addNewIdea.$inject = ['$scope', '$window', /*'restFactory', */'detailsService', 'ideasFactory', 'mapGeoService'];

    function addNewIdea($scope, $window, /*restFactory, */detailsService, ideasFactory, mapGeoService) {

        this.categories =  detailsService.getCategories();
        $scope.bottomButtonName = 'Добавить';
        $scope.data = null;

        //maps data
		$scope.center = [30.331014, 53.894617];
		var map = null;
		var ideaCoords = null;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.doWork = function(data){
		    //find tag name
			//todo:make more input fields for more tags and use this for
			var tags = new Array();
			for(var i=0; i<1; i++){
				tags.push(
					{
						id:i,
						name:data.tagName
					}
				);
			}//for

		    var request = {
		        id:null,
		        description:data.description,
		        title:data.title,
		        createdAt:new Date().getTime(),
		        lastModifiedAt:new Date().getTime(),
		        tags: tags,
		        latitude: (ideaCoords)?ideaCoords[1]:0,
		        longitude: (ideaCoords)?ideaCoords[0]:0
		    };

            ideasFactory.insertIdea(request).then(
			   //success
			   function( value )
			   {
				$window.location.href = '#home';
			   },
			   //error
			   function( error ){
				alert("Ошибка создания идеии: " + error.statusText);
			   }
			 );
		};

		$scope.afterInit = function($map){
			map = $map;
		};

		$scope.mapClick = function(e){
			var coords = e.get('coords');
			//alert(coords.join(', '));
			ideaCoords = coords;
			var geoPoints = {
				latitude: coords[1],
				longitude: coords[0]
			};

			//map.Point.show(coords);
			//map.balloon.open(coords, 'Моя идея');

			mapGeoService.setGeoCoordsDirective(map, geoPoints);
		};
    }


})();