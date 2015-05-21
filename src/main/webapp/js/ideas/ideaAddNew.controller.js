(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('addNewIdea', addNewIdea);

    addNewIdea.$inject = ['$scope', '$window', 'restFactory', 'detailsService'];

    function addNewIdea($scope, $window, restFactory, detailsService) {

        this.categories =  detailsService.getCategories();

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.addIdea = function(idea){
		    var request = {
		        id:null,
		        description:idea.descr,
		        title:idea.caption,
		        createdAt:new Date().getTime(),
		        lastModifiedAt:new Date().getTime()
		    };

		    var response = restFactory.ideas().create(request).$promise
		        .then(
                   //success
                   function( value )
                   {
                    $window.location.href = '#home';
                   },
                   //error
                   function( error ){
                    alert("Ошибка создания идеии: " + error);
                   }
                 );
		};
    }


})();