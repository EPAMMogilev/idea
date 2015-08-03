(function() {
	'use strict';
	angular
		.module('app.services')
		.service('ideasService', ideasService);

	ideasService.$inject = ['ideasFactory'];

	function ideasService(ideasFactory) {

		return {

			getPage:function(params, tag) {
				if (tag) {
					return ideasFactory.getPageWithTag(params, tag);
				} else {
					return ideasFactory.getPage(params) ;
				}
			},

			resetPageParams:function(params) {
				params.page = 0;
			}

		};
	};
})();