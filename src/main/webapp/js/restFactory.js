
(function () {
    'use strict';
    angular
        .module('ideaFactories')
        .factory('restFactory', restFactory);

    restFactory.$inject = ['$resource'];

    function restFactory($resource) {

        var factory = {
            ideas: ideas,
            tags: tags,
            users: users
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
                show: {method: 'GET', params: {ideaId: '@id'}, url: 'api/v1/ideas/:id/'},
                update: {method: 'PUT', params: {id: '@id'}, url: 'api/v1/ideas/:id/'},
                create: {method: 'POST', url: 'api/v1/ideas/'},
                delete: {method: 'DELETE', url: 'api/v1/ideas/:id/'}
                });
        }

         function users() {
            return $resource('api/v1/users/', {}, {
                get: {method: 'GET', isArray: true},
                getOne: {method: 'GET', params: {id: '@id'}, url: 'api/v1/users/:id/'},
                create: {method: 'POST', url: 'api/v1/users/'},
                getOneRegisteredByEmail: {method: 'GET', params: {email: '@email'}, url: 'api/v1/users/:email/email'}
            });
         }
    }
})();
