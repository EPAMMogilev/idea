(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('sessionFactory', sessionFactory);

    sessionFactory.$inject = ['restFactory'];

    function sessionFactory(restFactory) {

        var publicMethod = {
            createSession: createSession,
            deleteSession: deleteSession,
            getSession: getSession
        };
        return publicMethod;

        function createSession(user) {
            var promise = restFactory.users().createSession(user).$promise;
            return promise;
        }

         function deleteSession(sessionId) {
            var promise = restFactory.users().deleteSession({sessionId: sessionId}).$promise;
            return promise;
         }

          function getSession(sessionId) {
             var promise = restFactory.users().getSession({sessionId: sessionId}).$promise;
             return promise;
          }
    }
})();

