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

        this.loadMore = function(){
            var count = 3;
            if(this.ideas){
                var startPos = (this.ideasVisible && this.ideasVisible.length >= 0 )?this.ideasVisible.length - 1 : 0;
                var subarr = this.ideas.slice(0, startPos + count);
                this.ideasVisible.length = 0;
                for(var i=0; i<subarr.length; i++){
                    this.ideasVisible.push(subarr[i]);
                }//for
            }//if
        };

        vm.selectByCategory =function (tag) {
          tagsFactory.getIdeasByTag(tag).then(function (ideas) {
            vm.ideas = ideas;

            //$scope.updateGeoObjects(ideas);
            $scope.geoObjects = mapGeoService.generateGeoObjects(ideas);
        })
        };

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
        });

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
