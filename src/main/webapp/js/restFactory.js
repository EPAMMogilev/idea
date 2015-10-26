(function () {
	'use strict';
	angular
		.module('ideaFactories')
		.factory('restFactory', restFactory);

	restFactory.$inject = ['$resource'];

	function restFactory($resource) {

		var factory = {
			ideas: ideas,
			tags: tags,
			users: users,
			comments: comments,
			ideaStates: ideaStates
		};

		return factory;

		function tags() {
			return $resource('api/v1/tags/', {}, {
				get: {method: 'GET', isArray: true},
				getIdeasByTag: {method: 'GET', params: {id: '@id'}, url: 'api/v1/tags/:id/ideas/', isArray: true},
				});
		}

		function ideas() {
			return $resource('api/v1/ideas/', {}, {
				get: {method: 'GET', isArray: true},
				show: {method: 'GET', params: {ideaId: '@id'}, url: 'api/v1/ideas/:id/'},
				update: {method: 'PUT', params: {id: '@id'}, url: 'api/v1/ideas/:id/'},
				create: {method: 'POST', url: 'api/v1/ideas/'},
				delete: {method: 'DELETE', url: 'api/v1/ideas/:id/'},
				changeLike: {method: 'POST', params: {id: '@id'}, url: 'api/v1/ideas/:id/like/'},
				createComment: {method: 'POST', params: {id: '@id'}, url: 'api/v1/ideas/:id/comments/'},
				getCommentsPage: {method: 'GET', params: {id: '@id', page: '@page', size: '@size', sort: '@sort'}, url: 'api/v1/ideas/:id/comments?page=:page&size=:size&sort=:sort', isArray: true},
				getPage: {method: 'GET', params: {userId: '@userId', page: '@page', size: '@size', sort: '@sort', tagId: '@tagId', query: "@query"}, url: 'api/v1/ideas?page=:page&size=:size&sort=:sort&sort=title,asc&userId=:userId&tagId=:tagId&query=:query', isArray: true}
			});
		}

		function users() {
			return $resource('api/v1/users/', {}, {
				get: {method: 'GET', isArray: true},
				getOne: {method: 'GET', params: {id: '@id'}, url: 'api/v1/users/:id/'},
				create: {method: 'POST', url: 'user/register/'},
				getOneRegisteredByEmail: {method: 'GET', params: {email: '@email'}, url: 'api/v1/users/:email/email'}

			});
		}

		function comments() {
			return $resource('api/v1/comments/', {}, {
				changeLike: {method: 'POST', params: {id: '@id'}, url: 'api/v1/comments/:id/like/'}
			});
		}
		
		function ideaStates() {
			return $resource('api/v1/states/', {}, {
				get:  {method:'GET', isArray:true}
			
			});
		}
	}
})();