(function() {
    'use strict';

	angular
		.module('app.directives')
		.directive('tagButton', tagButton);

	function tagButton() {
		return {
			restrict: 'E',
			replace: true,
			transclude: true,
			scope: {
				active: '=',
				click: '&'
			},
			link: function(scope, element, attrs) {
				if (scope.active==true) element.addClass('active');
				element.bind('click', function() {
					getElementByClass('.active').removeClass('active');
					element.addClass('active');
					scope.$apply(function() {
						scope.click({tag:element.text()});
					});
				});
				function getElementByClass(className) {
					return angular.element($("#tag-buttons").find(className)[0]);
				}
			},
			template: [
					'<a type="button" class="btn btn-default" ng-transclude></a>'
			].join('')
		}
	}

})();
