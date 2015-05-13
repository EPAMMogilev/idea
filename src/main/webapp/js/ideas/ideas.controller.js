(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', 'ideasFactory', 'tagsFactory', 'Rate'];

    function ideasCtrl($scope, ideasFactory, tagsFactory, Rate) {

        var vm = this;

        vm.selectByCategory =function (tag) {
          tagsFactory.getIdeasByTag(tag).then(function (ideas) {
            vm.ideas = ideas;

        })
        };

        vm.changeRate = function (mark, idea) {
            idea=Rate.changeRate(mark, idea);
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
