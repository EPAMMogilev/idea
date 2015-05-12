(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('ideasFactory', ideasFactory);

    ideasFactory.$inject = ['crudFactory'];

    function ideasFactory(crudFactory) {

        var publicMethod = {
            getIdeas: getIdeas,
            updateIdea: updateIdea
        };
        return publicMethod;

        function getIdeas() {
            var ideas = crudFactory.ideas().get().$promise;
            return ideas;
        };

        function updateIdea(idea) {
            crudFactory.ideas().update({id: idea.id}, idea).$promise;
        };

    }
})();
