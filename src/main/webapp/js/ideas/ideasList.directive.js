(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('ideasList', ideasList);

	ideasList.$inject = [];

	function ideasList() {
		return {
			restrict: 'A',
			link: function(scope, element, attrs) {
                element.bind('scroll', function(){
                    alert('scroll: ');
                });
			}//link
		}
	}

})();
