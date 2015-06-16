(function () {
    'use strict';

    angular
        .module('app.services')
        .service('sessionService',sessionService);

         sessionService.$inject = ['sessionFactory'];

        function sessionService(sessionFactory) {
            var sessionID = '';
            var user = {};
            var publicMethod = {
                getSessionId: getSessionId,
                setSessionId: setSessionId,
                getUser: getUser,
                setUser: setUser
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

        };


})();

