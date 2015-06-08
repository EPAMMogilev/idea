(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('updateIdea', updateIdea);

    updateIdea.$inject = ['$scope', '$window', /*'restFactory', */'ideasFactory', 'detailsService', 'ideaDetails'];

    function updateIdea($scope, $window, /*restFactory, */ideasFactory, detailsService, ideaDetails) {

        this.categories =  detailsService.getCategories();
        $scope.bottomButtonName = 'Обновить';
        $scope.data = ideaDetails;

        //maps data
		$scope.center = [30.331014, 53.894617];
		var map = null;
		var ideaCoords = null;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.doWork = function(data){
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
				longitude: (ideaCoords)?ideaCoords[0]:0
		    };

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