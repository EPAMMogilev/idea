(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('ideasFactory', ideasFactory);

    ideasFactory.$inject = ['restFactory'];

    function ideasFactory(restFactory) {

        var publicMethod = {
            getIdeas: getIdeas,
            updateIdea: updateIdea
        };
        return publicMethod;

        function getIdeas() {
            var promise = restFactory.ideas().get().$promise;
            return promise;
        };

        function updateIdea(idea) {
            restFactory.ideas().update({id: idea.id}, idea).$promise;
        };

    }
})();
