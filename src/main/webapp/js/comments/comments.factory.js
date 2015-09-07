(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('commentsFactory', commentsFactory);

    commentsFactory.$inject = ['restFactory'];

    function commentsFactory(restFactory) {

        var publicMethod = {
            getCommentsByIdeaId: getCommentsByIdeaId
        };
        return publicMethod;

        function getCommentsByIdeaId(ideaId) {
            var promise = restFactory.comments().get({ideaId: ideaId}).$promise;
            return promise;
        }
    }
})();
