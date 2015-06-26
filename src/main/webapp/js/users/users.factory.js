(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('usersFactory', usersFactory);

    usersFactory.$inject = ['restFactory'];

    function usersFactory(restFactory) {

        var publicMethod = {
            get: get,
            getOne: getOne,
            createUser: createUser,
            getOneRegisteredByEmail: getOneRegisteredByEmail
        };
        return publicMethod;

        function get() {
            restFactory.ideas().get().$promise;
        };

        function getOne(id) {
            restFactory.ideas().getOne({id: id}).$promise;
        };

        function createUser(user) {
            var promise = restFactory.users().create(user).$promise;
            return promise;
        };

        function getOneRegisteredByEmail(email) {
            var promise = restFactory.users().getOneRegisteredByEmail({email: email}).$promise;
            return promise;
        };
    }
})();
