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
			    var scrollFunc = function(){
                                             var workDiv = $(scope.divName);
                                             if(workDiv){
                                                if(workDiv[0].scrollHeight - workDiv.scrollTop() == workDiv.outerHeight())
                                                {
                                                     element.unbind('scroll');
                                                     scope.updateFunction().then(function(){
                                                        element.bind('scroll', scrollFunc);
                                                     });
                                                }
                                             }//if
                                         };
                element.bind('scroll', scrollFunc);
			}//link
		}
	}

})();
