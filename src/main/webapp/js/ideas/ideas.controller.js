(function() {
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', 'ideasFactory', 'tagsFactory'];

    function ideasCtrl($scope, ideasFactory, tagsFactory) {

        var vm = this;

        vm.selectByCategory =function (tag) {
          tagsFactory.getIdeasByTag(tag).then(function (ideas) {
            vm.ideas = ideas;
        })
        };

        ideasFactory.getIdeas().then(function (ideas) {
            vm.ideas = ideas;
        });

        $scope.$on('ideas-update', function() {
            ideasFactory.getIdeas().then(function (ideas) {
                vm.ideas = ideas;
            });
        });

    }

})();
