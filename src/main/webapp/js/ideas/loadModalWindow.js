(function(){
    'use strict';

	angular
	    .module('app.controllers')
	    .controller('loadModalWindow', loadModalWindow);

    loadModalWindow.$inject = ['$scope'];

    function loadModalWindow($scope, $rootScope, $modalInstance/*, caption*/){
		  $scope.caption = 'Идет загрузка изображения. Дождитесь окончания...';

	}
})();
