
(function () {
    'use strict';
    angular
        .module('ideaFactories')
        .factory('appFactory', appFactory);

    appFactory.$inject = ['$resource'];

    function appFactory($resource) {

        var factory = {
            ideas: ideas,
            tags: tags
        };

        return factory;

        function tags() {
            return $resource('api/v1/tags/', {}, {
                get: {method: 'GET', isArray: true},
                getIdeasByTag: {method: 'GET', params: {id: '@id'}, url: 'api/v1/tags/:id/ideas/', isArray: true},
               });
        }

        function ideas() {
            return $resource('api/v1/ideas/', {}, {
                get: {method: 'GET', isArray: true},
                update: {method: 'PUT', params: {id: '@id'}, url: 'api/v1/ideas/:id/'},
                });
        }
    }
})();
