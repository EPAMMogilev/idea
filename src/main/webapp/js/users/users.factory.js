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
            getUserByEmailAndPassword: getUserByEmailAndPassword
        };
        return publicMethod;

        function get() {
            restFactory.ideas().get().$promise;
        };

        function getOne(id) {
            restFactory.ideas().getOne({id: id}).$promise;
        };

        function getUserByEmailAndPassword(email, password) {
            var promise = restFactory.users().getUserByEmailAndPassword({email: email, password: password}).$promise;
              promise.then(undefined, function (error) {
                });
            return promise;
        };
    }
})();
