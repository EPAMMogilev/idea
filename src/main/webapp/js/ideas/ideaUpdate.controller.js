(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('updateIdea', updateIdea);

    updateIdea.$inject = ['$scope', '$window', 'restFactory', 'ideasFactory', 'detailsService', 'ideaDetails'];

    function updateIdea($scope, $window, restFactory, ideasFactory, detailsService, ideaDetails) {

        this.categories =  detailsService.getCategories();
        $scope.bottomButtonName = 'Обновить';
        $scope.data = ideaDetails;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.doWork = function(data){
			var idea = ideasFactory.getIdeaById(data.id);

		    //find tag name
		    /*
		    var categories = detailsService.getCategories();
		    var tagName = '';
		    for(var j=0; j<categories.length; j++){
		    	var tmp = categories[j];
		        if(tmp.id == data.tagId){
		            tagName = tmp.descr;
		            break;
		        }//if
		    }//for

		    var tags = new Array();
		    tags.push(
		        {
		            id:data.tagId,
		            name:tagName
		        }
		    );*/

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
		    };

            //restFactory.ideas().update({id: request.id}, request).$promise;
            ideasFactory.updateIdea(request);
		};
    }


})();