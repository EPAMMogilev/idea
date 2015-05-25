(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('addNewIdea', addNewIdea);

    addNewIdea.$inject = ['$scope', '$window', /*'restFactory', */'detailsService', 'ideasFactory'];

    function addNewIdea($scope, $window, /*restFactory, */detailsService, ideasFactory) {

        this.categories =  detailsService.getCategories();
        $scope.bottomButtonName = 'Добавить';
        $scope.data = null;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.doWork = function(data){
		    //find tag name
		    /*
		    var categories = detailsService.getCategories();
		    var tagName = '';
		    for(var i=0; i<categories.length; i++){
		    	var tmp = categories[i];
		        if(tmp.id == data.tagId){
		            tagName = tmp.descr;
		            break;
		        }//if
		    }//for*/

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
		        tags: tags
		    };
			/*
		    var response = restFactory.ideas().create(request).$promise
		        .then(
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
            */
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
    }


})();