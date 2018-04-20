(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('ideaStateLabel', ideaStateLabel);
	
	ideaStateLabel.$inject = ['$compile'];
	
	function ideaStateLabel($compile) {
		return {
			restrict: 'A',
			replace: true,
			compile: function compile(element, attrs) {
				element.attr('ng-class', "{'label label-success' : idea.state.name == 'New', 'label label-warning' : idea.state.name == 'Assigned', 'label label-danger' : idea.state.name == 'Deleted', 'label label-info' : idea.state.name == 'InProgress'}");
				element.removeAttr('idea-state-label');
				return {
					pre: function preLink(scope, iElement, iAttrs, controller) {  },
					post: function postLink(scope, iElement, iAttrs, controller) {  
						$compile(iElement)(scope);
					}
		        }
		      }
		    }
	}

})();
