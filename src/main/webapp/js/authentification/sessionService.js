(function () {
    'use strict';

    angular
        .module('ideaFactories')
        .factory('sessionService',sessionService)

        function sessionService() {
            var sessionID = '';
            var user = {};
            return {
                getSessionId: function() {
                    if(sessionID=='' || sessionID==null)
                        sessionID = localStorage.getItem("SessionId");

                    console.log("Get sessionId => " + sessionID);

                    return sessionID;
                },

                setSessionId: function(sessId) {
                    console.log("Set sessionId=" + sessId);
                    localStorage.setItem("SessionId", sessId);
                    sessionID = sessId;
                    return;
                },

                getUser: function() {
                    if(user=='' || user==null)
                        user = localStorage.getItem("user");
                    return user;
                },

                setUser: function(user_) {
                    localStorage.setItem("user", user_);
                    user = user_;
                    return;
                }
            }
        };


})();

