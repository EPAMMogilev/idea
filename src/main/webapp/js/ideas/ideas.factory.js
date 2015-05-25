(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('ideasFactory', ideasFactory);

    ideasFactory.$inject = ['restFactory'];

    function ideasFactory(restFactory) {

        var publicMethod = {
            getIdeas: getIdeas,
            getIdeaById: getIdeaById,
            insertIdea: insertIdea,
            updateIdea: updateIdea
        };
        return publicMethod;

        function getIdeas() {
            var promise = restFactory.ideas().get().$promise;
            return promise;
        };

        function getIdeaById(ideaId) {
            var promise = restFactory.ideas().show({id: ideaId}).$promise;
            return promise;
        };

        function updateIdea(idea) {
            var promise = restFactory.ideas().update({id: idea.id}, idea).$promise;
            return promise;
        };

        function insertIdea(idea){
            var promise = restFactory.ideas().create(idea).$promise;
            return promise;
        };

    }
})();
