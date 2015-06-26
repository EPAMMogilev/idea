(function() {
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', 'ideasFactory', 'tagsFactory', 'mapGeoService'];

    function ideasCtrl($scope, ideasFactory, tagsFactory, mapGeoService) {

        var vm = this;
        $scope.geoObjects = null;

        $scope.criteria = null;

        vm.ideasVisible = [];
        vm.newIdeas = [];

        //infinity load
        vm.pageRatingRequest = {
            page: 0,
            size: 5,
            sort: 'rating,desc'
        };
        vm.pageNewRequest = {
            page: 0,
            size: 5,
            sort: 'creationTime,desc'
        };

        this.loadMoreRating = function(){

            vm.pageRatingRequest.page += 1;

            ideasFactory.getPage(vm.pageRatingRequest).then(function (ideas) {
                if(ideas){
                    for(var i=0; i < ideas.length; i++){
                        vm.ideasVisible.push(ideas[i]);
                    }//for
                }//if

                //$scope.updateGeoObjects(ideas);
                $scope.geoObjects = mapGeoService.generateGeoObjects(vm.ideasVisible);
            });
        };

        this.loadMoreNew = function(){

            vm.pageNewRequest.page += 1;
            //load most popular ideas
            ideasFactory.getPage(vm.pageNewRequest).then(function (ideas) {
                if(ideas){
                    for(var i=0; i < ideas.length; i++){
                        vm.newIdeas.push(ideas[i]);
                    }//for
                }//if
            });
        };

        vm.selectByCategory =function (tag) {
          tagsFactory.getIdeasByTag(tag).then(function (ideas) {
            vm.ideas = ideas;

            //$scope.updateGeoObjects(ideas);
            $scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
        })
        };

        ideasFactory.getPage(vm.pageRatingRequest).then(function (ideas) {
            vm.ideasVisible = ideas;

            //$scope.updateGeoObjects(ideas);
            $scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
        });


        //load most popular ideas
        ideasFactory.getPage(vm.pageNewRequest).then(function (ideas) {
            vm.newIdeas = ideas;
        });
/*
        ideasFactory.getIdeas().then(function (ideas) {
            vm.ideas = ideas;

            //sort ideas by date
            vm.ideas.sort(
                function(a, b){
                    var keyA = a.rating;
                    var keyB = b.rating;

                    if(keyA<keyB) return -1;
                    if(keyA>keyB) return 1;
                    return 0;
                }
            );

            //$scope.updateGeoObjects(ideas);
            $scope.geoObjects = mapGeoService.generateGeoObjects(ideas);

            //loading array for infinity list
            if(vm.ideas){
                vm.ideasVisible = vm.ideas.slice(0, 5);
            }//if
        });*/

        $scope.$on('ideas-update', function() {
            ideasFactory.getIdeas().then(function (ideas) {
                vm.ideas = ideas;

            //$scope.updateGeoObjects(ideas);
            $scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
            });
        });

        vm.details = function(idea){
            var ideaDetail = {
                id:idea.id
            };

			console.log('Go to Details');
			$state.go('ideaDetails', { 'idea': angular.toJson(ideaDetail) });
        };

        //gui effects
        $scope.mouseenter=function(e){
            e.get('target').options.set('preset', 'islands#greenIcon');
        };
        $scope.mouseleave=function(e){
            e.get('target').options.unset('preset');
        };
    }

})();
