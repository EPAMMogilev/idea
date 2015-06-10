(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('addNewIdea', addNewIdea);

    addNewIdea.$inject = ['$scope', '$window', /*'restFactory', */'detailsService', 'ideasFactory', 'mapGeoService', 'imgur'];

    function addNewIdea($scope, $window, /*restFactory, */detailsService, ideasFactory, mapGeoService, imgur) {

        this.categories =  detailsService.getCategories();
        $scope.bottomButtonName = 'Добавить';
        $scope.data = null;

        $scope.imageExist = false;
        $scope.imageFile;

        //maps data
		$scope.center = [30.331014, 53.894617];
		var map = null;
		var ideaCoords = null;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.chooseFile = function(){
    		$('input[type=file]').click();

			$('input[type=file]').change(function() {
				//read file
				var input = document.getElementById('imageLoader');

				if(input && input.files && input.files[0]){
					$scope.imageFile = input.files[0];
					$scope.imageExist = true;
				}//if
			});
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

			if($scope.imageExist == true){
				//load file to imgur
				imgur.setAPIKey('Client-ID c62cfae02efe4c0');
				imgur.upload($scope.imageFile).then(function then(model) {
						console.log('Your adorable cat be here: ' + model.link);

						var request = {
							id:null,
							description:data.description,
							title:data.title,
							createdAt:new Date().getTime(),
							lastModifiedAt:new Date().getTime(),
							tags: tags,
							latitude: (ideaCoords)?ideaCoords[1]:0,
							longitude: (ideaCoords)?ideaCoords[0]:0,
							imageUrl: model.link
						};

						$scope.insertIdea(request);
				});
			}else{
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

				$scope.insertIdea(request);
			}//if..else..
		};

		$scope.insertIdea = function(idea){
				ideasFactory.insertIdea(idea).then(
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
		};//insertIdea

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