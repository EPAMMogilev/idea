(function() {
	'use strict';
	angular
		.module('app.services')
		.service('ideasService', ideasService);

	ideasService.$inject = ['ideasFactory'];

	function ideasService(ideasFactory) {

		return {

			getPage:function(params, user, tag, query) {
				if(user) {
					return getPageIfUserIsNotNull(params, user, tag, query);
				} else {
					return getPageIfUserIsNull(params, tag, query);
				}
			},

			resetPageParams:function(params) {
				params.page = 0;
			}

		};

		function getPageIfUserIsNotNull(params, user, tag, query) {
			if (tag && query) {
				return ideasFactory.getPageOfUserWithTagAndQuery(params, user, tag, query);
			} else {
				if(tag) {
					return ideasFactory.getPageOfUserWithTag(params, user, tag);
				} else {
					if (query) {
						return ideasFactory.getPageOfUserWithQuery(params, user, query);
					} else {
						return ideasFactory.getPageOfUser(params, user) ;
					}
				}
			}
		};

		function getPageIfUserIsNull(params, tag, query) {
			if (tag && query) {
				return ideasFactory.getPageWithTagAndQuery(params, tag, query);
			} else {
				if(tag) {
					return ideasFactory.getPageWithTag(params, tag);
				} else {
					if (query) {
						return ideasFactory.getPageWithQuery(params, query);
					} else {
						return ideasFactory.getPage(params) ;
					}
				}
			}
		};
	}
})();