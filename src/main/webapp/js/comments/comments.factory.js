(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('commentsFactory', commentsFactory);

    commentsFactory.$inject = ['restFactory'];

    function commentsFactory(restFactory) {

        var publicMethod = {
            getCommentsPageByIdeaId: getCommentsPageByIdeaId,
            changeCommentLike: changeCommentLike
        };
        return publicMethod;

        function getCommentsPageByIdeaId(page, ideaId) {
            var promise = restFactory.ideas().getCommentsPage({id: ideaId, page: page.page, size: page.size, sort: page.sort}).$promise;
            return promise;
        };

        function changeCommentLike(id) {
            var promise = restFactory.comments().changeLike({id: id}).$promise;
            return promise;
        };
    }
})();