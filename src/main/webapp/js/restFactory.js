
(function () {
    'use strict';
    angular
        .module('ideaFactories')
        .factory('restFactory', restFactory);

    restFactory.$inject = ['$resource'];

    function restFactory($resource) {

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
