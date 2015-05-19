(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', '$window', 'ideasFactory', 'tagsFactory', 'Rate', 'detailsService'];

    function ideasCtrl($scope, $window, ideasFactory, tagsFactory, Rate, detailsService) {

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

        vm.details = function(idea){
            $scope.ideaDetail = {
                title:idea.title,
                description:idea.description,
                createdAt:idea.createdAt
            };
            detailsService.setData($scope.ideaDetail);
            $window.location.href = '#ideaDetails';
        };
    }



})();
