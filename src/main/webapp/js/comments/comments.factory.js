(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('commentsFactory', commentsFactory);

    commentsFactory.$inject = ['restFactory'];

    function commentsFactory(restFactory) {

        var publicMethod = {
            createComment: createComment,
            getCommentsPageByIdeaId: getCommentsPageByIdeaId,
            changeCommentLike: changeCommentLike
        };
        return publicMethod;

        function createComment(comment) {
            var promise = restFactory.ideas().createComment({id: comment.subject.id}, comment).$promise;
            return promise;
        };

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