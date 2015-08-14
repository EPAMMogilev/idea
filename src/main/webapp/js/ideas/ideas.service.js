(function() {
	'use strict';
	angular
		.module('app.services')
		.service('ideasService', ideasService);

	ideasService.$inject = ['ideasFactory'];

	function ideasService(ideasFactory) {

		return {

			getPage:function(params, tag, query) {
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
			},

			resetPageParams:function(params) {
				params.page = 0;
			}

		};
	};
})();