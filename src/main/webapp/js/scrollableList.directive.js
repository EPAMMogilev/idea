(function () {
    'use strict';

    angular
        .module('app.directives')
        .directive('scrollableList', scrollableList);

    scrollableList.$inject = [];

    function scrollableList() {
        return {
            restrict: 'A',
            scope: {
                updateFunction: '&'
            },
            link: function (scope, element, attrs) {
                    var scrollFunc = function () {
                        var workDiv = $(element);
                        if (workDiv) {
                            if (workDiv[0].scrollHeight - workDiv.scrollTop() == workDiv.outerHeight()) {
                                element.unbind('scroll');
                                scope.updateFunction().then(function () {
                                    element.bind('scroll', scrollFunc);
                                });
                            }
                        } //if
                    };
                    element.bind('scroll', scrollFunc);
                } //link
        }
    }

})();