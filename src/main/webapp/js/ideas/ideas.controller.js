(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', '$window', 'ideasFactory', 'tagsFactory', 'Rate', '$state'];

    function ideasCtrl($scope, $window, ideasFactory, tagsFactory, Rate, $state) {

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
