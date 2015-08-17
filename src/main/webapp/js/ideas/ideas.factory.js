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
            updateIdea: updateIdea,
            removeIdea: removeIdea,
            changeIdeaLike: changeIdeaLike,
            getPage: getPage,
            getPageWithTag: getPageWithTag,
            isUserAuthorOfIdea: isUserAuthorOfIdea,
            getPageWithQuery: getPageWithQuery,
            getPageWithTagAndQuery: getPageWithTagAndQuery,
            getPageOfUser: getPageOfUser,
            getPageOfUserWithTag: getPageOfUserWithTag,
            getPageOfUserWithQuery: getPageOfUserWithQuery,
            getPageOfUserWithTagAndQuery: getPageOfUserWithTagAndQuery
        };
        return publicMethod;

        function isUserAuthorOfIdea(user, idea) {
			if(idea && user) {
				if (user.id == idea.author.id) {
					return true;
				}
			}
			return false;
		}

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

        function insertIdea(idea) {
            var promise = restFactory.ideas().create(idea).$promise;
            return promise;
        };

        function removeIdea(idea) {
            var promise = restFactory.ideas().delete(idea).$promise;
            return promise;
        };

        function changeIdeaLike(id) {
            var promise = restFactory.ideas().changeLike({id: id}).$promise;
            return promise;
        };

        function getPage(page) {
            var promise = restFactory.ideas().getPage({page: page.page, size: page.size, sort: page.sort}).$promise;
            return promise;
        };

        function getPageWithTag(page, tag) {
            var promise = restFactory.ideas().getPageWithTag({page: page.page, size: page.size, sort: page.sort, tagId: tag.id}).$promise;
            return promise;
        };

        function getPageWithQuery(page, query) {
            var promise = restFactory.ideas().getPageWithQuery({page: page.page, size: page.size, sort: page.sort, query: query}).$promise;
            return promise;
        };

        function getPageWithTagAndQuery(page, tag, query) {
            var promise = restFactory.ideas().getPageWithTagAndQuery({page: page.page, size: page.size, sort: page.sort, tagId: tag.id, query: query}).$promise;
            return promise;
        };

        function getPageOfUser(page, userId) {
            var promise = restFactory.ideas().getPageOfUser({userId: userId, page: page.page, size: page.size, sort: page.sort}).$promise;
            return promise;
        };

        function getPageOfUserWithTag(page, userId, tag) {
            var promise = restFactory.ideas().getPageOfUserWithTag({userId: userId, page: page.page, size: page.size, sort: page.sort, tagId: tag.id}).$promise;
            return promise;
        };

        function getPageOfUserWithQuery(page, userId, query) {
            var promise = restFactory.ideas().getPageOfUserWithQuery({userId: userId, page: page.page, size: page.size, sort: page.sort, query: query}).$promise;
            return promise;
        };

        function getPageOfUserWithTagAndQuery(page, userId, tag, query) {
            var promise = restFactory.ideas().getPageOfUserWithTagAndQuery({userId: userId, page: page.page, size: page.size, sort: page.sort, tagId: tag.id, query: query}).$promise;
            return promise;
        };
    }
})();