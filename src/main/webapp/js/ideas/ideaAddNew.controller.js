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
		$scope.center = [30.331014, 53.894617];

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
		        tags: tags
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
    }


})();