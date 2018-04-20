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
		            content: '@',
		            container: '@'
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
		                $rootScope.insidePopover = false;
		                var popover = $(element).popover({
		                    placement: 'top',
		                    container: scope.container
		                });
		                $(element).data("bs.popover").options.content = scope.content;
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
		            });
		        }
	        };
	    }

})();