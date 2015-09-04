(function () {
    'use strict';

    angular
        .module('app.services')
        .service('ideaDetailsService',ideaDetailsService);

        function ideaDetailsService() {
            var publicMethod = {
            	getlikedUsersListAsString: getlikedUsersListAsString
            };
            return publicMethod;

    		function getlikedUsersListAsString(idea){
    			var users = [];
    			for(var i = 0; i < idea.likedUsers.length; i++) {
    				users.push(idea.likedUsers[i].username);
    			}
    			return users.join(", ");
    		};
    };
})();

