(function () {
    'use strict';

    angular
        .module('app.services')
        .service('authorizationService', authorizationService);

        authorizationService.$inject = ['sessionService', 'ideasFactory', '$rootScope', '$location'];

        function authorizationService(sessionService, ideasFactory, $rootScope, $location) {
            var publicMethod = {
                redirectFromEditToAccessDeniedIfNeeded: redirectFromEditToAccessDeniedIfNeeded
            };
            return publicMethod;

            function redirectFromEditToAccessDeniedIfNeeded(idea) {
         	    if(!sessionService.hasRole('ROLE_ADMIN')) {
            	    ideasFactory.getIdeaById(idea.id).then(function(value) {
                	    if(!ideasFactory.isUserAuthorOfIdea($rootScope.currentUser, value)) {
                		    $location.path("/accessDenied");
                	    }
            	    });
        	    }
             };

        };
})();

