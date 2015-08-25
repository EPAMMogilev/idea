(function () {
    'use strict';

    angular
        .module('app.services')
        .service('sessionService',sessionService);

         sessionService.$inject = ['$rootScope'];

        function sessionService($rootScope) {
            var sessionID = '';
            var user = {};
            var publicMethod = {
                getSessionId: getSessionId,
                setSessionId: setSessionId,
                getUser: getUser,
                setUser: setUser,
                hasRole: hasRole,
                getIconClassForCurrentUser: getIconClassForCurrentUser
            };
            return publicMethod;

            function getSessionId() {
                if(sessionID=='' || sessionID==null)
                    sessionID = localStorage.getItem("SessionId");
                return sessionID;
            };

            function setSessionId (sessId) {

                localStorage.setItem("SessionId", sessId);
                sessionID = sessId;
                return;
            };

            function getUser () {
                if(user=='' || user==null)
                    user = localStorage.getItem("user");
                return user;
            };

            function setUser (user_) {
                localStorage.setItem("user", user_);
                user = user_;
                return;
            };

            function hasRole (role, user) {
                if(user == undefined) {
                    if($rootScope.currentUser == undefined) {
                    return false;
                        } else {
                            user = $rootScope.currentUser;
                        }
                    }

                for (var i = 0; i < user.authorities.length; i++) {
                    if(user.authorities[i].authority == role) {
                        return true;
                    }

                return false;
                };

            };

            function getIconClassForCurrentUser(user) {
                if(user.socialSignInProvider === 'GOOGLE') {
                	return 'fa fa-google-plus-square color-google-plus';
                } else {
                	if(user.socialSignInProvider === 'FACEBOOK') {
                		return 'fa fa-facebook-square color-facebook';
                	} else {
                		if(user.socialSignInProvider === 'VKONTAKTE') {
                			return 'fa fa-vk color-vk';
                		} else {
                			return 'fa fa-user';
                		}
                	}
                }
            };
    };
})();

