(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('detailsCtrl', detailsCtrl);

    detailsCtrl.$inject = ['$scope', '$window', 'restFactory', 'detailsService', 'ideaDetails', 'wndType'];

    function detailsCtrl($scope, $window, restFactory, detailsService, ideaDetails, wndType) {

        this.data = ideaDetails;
        this.wndType = wndType;

        this.categories =  detailsService.getCategories();

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.addNewIdea = function(idea){
		    var request = {
		        id:null,
		        description:idea.descr,
		        title:idea.caption,
		        createdAt:new Date().getTime(),
		        lastModifiedAt:new Date().getTime()
		    };

		    //var response = restFactory.ideas().create(request).$promise;
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