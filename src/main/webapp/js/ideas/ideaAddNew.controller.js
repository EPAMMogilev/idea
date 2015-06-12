(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('addNewIdea', addNewIdea);

    addNewIdea.$inject = ['$scope', '$window', '$modal', 'detailsService', 'ideasFactory', 'mapGeoService', 'imgur'];

    function addNewIdea($scope, $window, $modal, detailsService, ideasFactory, mapGeoService, imgur) {

        this.categories =  detailsService.getCategories();
        $scope.bottomButtonName = 'Добавить';
        $scope.data = null;

        $scope.imageExist = false;
        $scope.imageFile;
        $scope.imageUrl = null;
        //$scope.caption = 'Идет загрузка изображения. Дождитесь окончания...';
        $scope.modalInstance = null;

        //maps data
		$scope.center = [30.331014, 53.894617];
		var map = null;
		var ideaCoords = null;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.openModalWindow = function(){
			$scope.modalInstance = $modal.open({
				animation: true,
				templateUrl: 'myModalContent.html',
				controller: 'loadModalWindow',
				size: 'lg'/*,
				resolve: {
				  caption: function () {
					return $scope.caption;
				  }
				}*/
			  });
		};//openModalWindow

		$scope.closeModalWindow = function(){
			$scope.modalInstance.dismiss('cancel');
		}//closeModalWindow

		$scope.chooseFile = function(){
    		$('input[type=file]').click();

			$('input[type=file]').change(function() {
				//read file
				var input = document.getElementById('imageLoader');

				if(input && input.files && input.files[0]){
					$scope.imageFile = input.files[0];
					$scope.imageExist = true;

					$scope.openModalWindow();

					//load file to imgur
					imgur.setAPIKey('Client-ID c62cfae02efe4c0');
					imgur.upload($scope.imageFile).then(function then(model) {
							console.log('Your adorable cat be here: ' + model.link);

							$scope.imageUrl = model.link;

							//hide window #modalWindow
							$scope.closeModalWindow();
					});
				}//if
			});
		};//chooseFile

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

			if($scope.imageExist == true){
				request.imageUrl = $scope.imageUrl;
				/*
				//load file to imgur
				imgur.setAPIKey('Client-ID c62cfae02efe4c0');
				imgur.upload($scope.imageFile).then(function then(model) {
						console.log('Your adorable cat be here: ' + model.link);

						request.imageUrl = model.link;

						$scope.insertIdea(request);
				});*/
			}//if..
			$scope.insertIdea(request);
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