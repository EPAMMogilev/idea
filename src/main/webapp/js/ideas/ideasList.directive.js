(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('ideasList', ideasList);

	ideasList.$inject = [];

	function ideasList() {
		return {
			restrict: 'A',
			scope:{
			    updateFunction: '&',
			    divName: '@'
			},
			link: function(scope, element, attrs) {
                element.bind('scroll', function(){
                    var scrollTop = element.scrollTop();
                    var scrollH = $(scope.divName).height();

                    var scrollPercent = 100 - Math.round(scrollTop / (scrollH/100));

                    if(scrollPercent > 96){
                        alert('% = ' + scrollPercent);
                        scope.updateFunction();
                    }//if
                });
			}//link
		}
	}

})();
