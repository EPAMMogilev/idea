(function () {
    'use strict';

    angular.module('app.services')
        .service('stateService', stateService);

    stateService.$inject = ['$http'];

    function stateService($http) {

        var ideaStates = null;

        var promise = $http.get('api/v1/states/').success(function (data) {
            ideaStates = data;
        });

        return {

            getIdeaStates: function () {
                return ideaStates;
            }, //getIdeaStates

            init: function () {
                    return promise;
                } //init

        };
    };
})();