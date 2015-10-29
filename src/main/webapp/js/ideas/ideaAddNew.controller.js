(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('addNewIdea', addNewIdea);

    addNewIdea.$inject = ['$scope', '$window', '$modal', 'detailsService', 'ideasFactory', 'mapGeoService', 'imgur', 'Upload'];

    function addNewIdea($scope, $window, $modal, detailsService, ideasFactory, mapGeoService, imgur, Upload) {

        this.categories =  detailsService.getCategories();
        $scope.bottomButtonName = 'ADD';
        $scope.data = null;
        $scope.submitted = false;
        //$scope.isUpdating = false;
        $scope.imageExist = false;
        $scope.imageFile;
        //$scope.imageUrl = null;
        $scope.imageUrl = "images/photo.gif";
        //$scope.caption = 'Идет загрузка изображения. Дождитесь окончания...';
        $scope.modalInstance = null;
        $scope.files = null;
        $scope.windowTitle = 'ADD_IDEA';
        //maps data
		$scope.center = [30.331014, 53.894617];
		var map = null;
		var ideaCoords = null;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.openModalWindowLoadingProgress = function(){
			$scope.modalInstance = $modal.open({
				animation: true,
				templateUrl: 'myModalContent.html',
				size: 'lg'
			  });
		};//openModalWindowLoadingProgress

		$scope.closeModalWindow = function(){
			//$scope.modalInstance.dismiss('cancel');
			$scope.modalInstance.close();
			$scope.modalInstance = null;
		}//closeModalWindow

		$scope.$watch('files', function(){
			if ($scope.files && $scope.files.length) {
				$scope.openModalWindow();
				$scope.load2Imgur($scope.files);
			}//if
		});

		$scope.chooseFile = function(){
    		$('input[type=file]').click();

			$('input[type=file]').change(function() {
				//read file
				var input = document.getElementById('imageLoader');

				if(input && input.files && input.files[0]){
					$scope.imageFile = input.files[0];

					$scope.openModalWindowLoadingProgress();
					/*
					//load file to imgur
					imgur.setAPIKey('Client-ID c62cfae02efe4c0');
					imgur.upload($scope.imageFile).then(function then(model) {
							console.log('Your adorable cat be here: ' + model.link);

							$scope.imageUrl = model.link;

							//hide window #modalWindow
							$scope.closeModalWindow();
					});*/
					$scope.load2Imgur($scope.imageFile);
				}//if
			});
		};//chooseFile

		$scope.load2Imgur = function(file){
			//load file to imgur
			imgur.setAPIKey('Client-ID c62cfae02efe4c0');
			imgur.upload(file).then(function then(model) {
					console.log('Your adorable cat be here: ' + model.link);

					if(Object.getPrototypeOf(model) === Object.prototype){
						$scope.imageUrl = model.link;
					}else{
						$scope.imageUrl = model[0].link;
					}

					$scope.imageExist = true;

					//hide window #modalWindow
					$scope.closeModalWindow();
			});
		};//load2Imgur

		$scope.doWork = function(data){
		    //find tag name
			//todo:make more input fields for more tags and use this for
			$scope.submitted = true;
			if ($scope.ideaForm.$invalid) {return;}
			var tags = new Array();
			for(var i=0; i<1; i++){
				if (tags[i] != null && tags[i] != undefined) {
					tags.push(
						{
							id:i,
							name:data.tagName
						}
					);
				}
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

		$scope.beforeInit = function(){
			var geolocation = ymaps.geolocation;

			ideaCoords = [geolocation.longitude, geolocation.latitude];
		};

		$scope.afterInit = function($map){
			map = $map;

			if(ideaCoords){
				mapGeoService.setGeoCoordsDirective(map, ideaCoords);
			}//if
		};

		$scope.mapClick = function(e){
			var coords = e.get('coords');
			//alert(coords.join(', '));
			ideaCoords = coords;
			var geoPoints = {
				latitude: coords[1],
				longitude: coords[0]
			};

			mapGeoService.setGeoCoordsDirective(map, geoPoints);
		};
    }


})();
