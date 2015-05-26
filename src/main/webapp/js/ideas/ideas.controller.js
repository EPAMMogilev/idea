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

        vm.details = function(idea){
            var ideaDetail = {
                id:idea.id,
                title:idea.title,
                description:idea.description,
                createdAt:idea.createdAt,
                tagName:idea.tags[0].name,
                tagId:idea.tags[0].id,
            };
            //detailsService.setData($scope.ideaDetail);
            //$window.location.href = '#ideaDetails';
			console.log('Go to Details');
			$state.go('ideaDetails', { 'idea': angular.toJson(ideaDetail) });
        };
    }

})();
