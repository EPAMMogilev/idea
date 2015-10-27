(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('updateIdea', updateIdea);

    updateIdea.$inject = ['$scope', '$window', '$modal', 'ideasFactory', 'stateService', 'ideaDetails', 'mapGeoService', 'imgur', 'Upload'];

    function updateIdea($scope, $window, $modal, ideasFactory, stateService, ideaDetails, mapGeoService, imgur, Upload) {

        $scope.states = stateService.getIdeaStates();
    	
        $scope.bottomButtonName = 'UPDATE';
        $scope.idea = ideaDetails;
        $scope.data = null;
        $scope.data
        
        $scope.imageExist = false;
        $scope.imageFile;
        $scope.imageUrl = null;
        $scope.modalInstance = null;
        $scope.files = null;
        $scope.windowTitle = 'CHANGE_IDEA';
        
        $scope.isUpdating = true;
        
        //maps data
		$scope.center = [30.331014, 53.894617];
		var map = null;
		var ideaCoords = null;

		$scope.openModalWindow = function(){
			$scope.modalInstance = $modal.open({
				animation: true,
				templateUrl: 'myModalContent.html',
				size: 'lg'
			  });
		};//openModalWindow

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

					$scope.openModalWindow();
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

        this.promises = ideasFactory.getIdeaById($scope.idea.id).then(
        		//success
        		function( value ) {
        			$scope.data = value;
        			//set geo point
        			if($scope.data && $scope.data.latitude && $scope.data.longitude && map){
        				var geoPoints = {
        					latitude: $scope.data.latitude,
        					longitude: $scope.data.longitude
                        };
        				$scope.imageUrl = $scope.data.imageUrl;
        				mapGeoService.setGeoCoordsDirective(map, geoPoints);
        			}//if
        		}
        );

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.doWork = function(data){
			if ($scope.ideaForm.$invalid) {return;}
			var idea = ideasFactory.getIdeaById(data.id);

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

		    //create request
		    var request = {
		        id:data.id,
		        description:data.description,
		        title:data.title,
		        rating:idea.rating,
		        createdAt:idea.createdAt,
		        lastModifiedAt:new Date().getTime(),
                links:data.links,
                tags:tags,
                author:idea.author,
                latitude: (ideaCoords)?ideaCoords[1]:0,
				longitude: (ideaCoords)?ideaCoords[0]:0,
				state: data.state.name
		    };

			if($scope.imageExist == true){
				request.imageUrl = $scope.imageUrl;
			}//if..

            //ideasFactory.updateIdea(request);

			ideasFactory.updateIdea(request).then(
			   //success
			   function( value )
			   {
				$window.location.href = '#home';
			   },
			   //error
			   function( error ){
				alert("Ошибка обновления идеии: " + error.statusText);
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

			//map.Point.show(coords);
			map.balloon.open(coords, 'Моя идея');
		};
    }


})();
