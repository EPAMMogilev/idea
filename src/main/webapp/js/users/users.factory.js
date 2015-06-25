(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('usersFactory', usersFactory);

    usersFactory.$inject = ['restFactory'];

    function usersFactory(restFactory) {

        var publicMethod = {
            get: get,
            getOne: getOne
        };
        return publicMethod;

        function get() {
            restFactory.ideas().get().$promise;
        };

        function getOne(id) {
            restFactory.ideas().getOne({id: id}).$promise;
        };
    }
})();
