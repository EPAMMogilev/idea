(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('ideasFactory', ideasFactory);

    ideasFactory.$inject = ['appFactory'];

    function ideasFactory(appFactory) {

        var publicMethod = {
            getIdeas: getIdeas,
            updateIdea: updateIdea
        };
        return publicMethod;

        function getIdeas() {
            var promise = appFactory.ideas().get().$promise;
            return promise;
        };

        function updateIdea(idea) {
            appFactory.ideas().update({id: idea.id}, idea).$promise;
        };

    }
})();
