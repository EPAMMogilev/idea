(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('hoverPopover', hoverPopover);

	hoverPopover.$inject = ['$compile', '$timeout', '$rootScope'];

	function hoverPopover($compile, $timeout, $rootScope) {
		   return {
		        restrict: 'A',
		        scope: {
		            content: '@'
		        },
		        controller: ['$scope', '$element',  function ($scope, $element) {
		            $scope.attachEvents = function (element) {
		                $('.popover').on('mouseenter', function () {
		                    $rootScope.insidePopover = true;
		                });
		                $('.popover').on('mouseleave', function () {
		                    $rootScope.insidePopover = false;
		                    $(element).popover('hide');
		                });
		            };
		        }],
		        link: function (scope, element, attrs) {
		            scope.$watch(function () { return scope.content; }, function () {
		            	if(scope.content != "") {
			                $rootScope.insidePopover = false;
			                $(element).popover({
			                    content: scope.content,
			                    placement: 'top'
			                });
			                $(element).bind('mouseenter', function (e) {
			                    $timeout(function () {
			                        if (!$rootScope.insidePopover) {
			                            $(element).popover('show');
			                            scope.attachEvents(element);
			                        }
			                    }, 50);
			                });
			                $(element).bind('mouseleave', function (e) {
			                    $timeout(function () {
			                        if (!$rootScope.insidePopover)
			                            $(element).popover('hide');
			                    }, 50);
			                });
		            	}
		            });
		        }
	        };
	    }

})();