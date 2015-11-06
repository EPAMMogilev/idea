(function () {
    'use strict';

    angular
        .module('app.services')
        .service('usersService',usersService);

        function usersService() {
            var publicMethod = {
            	getlikedUsersListAsString: getlikedUsersListAsString
            };
            return publicMethod;

    		function getlikedUsersListAsString(obj){
    			var users = [];
    			for(var i = 0; i < obj.likedUsers.length; i++) {
    				users.push(obj.likedUsers[i].username);
    			}
    			return users.join(", ");
    		};
    };
})();

