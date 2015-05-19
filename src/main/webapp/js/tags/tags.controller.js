(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('tagsCtrl', tagsCtrl);

    tagsCtrl.$inject = ['$scope', 'tagsFactory'];

    function tagsCtrl($scope, tagsFactory) {

        var vm = this;
        tagsFactory.getTopNTags().then(function (tags) {
            vm.tagsTop = tags;
        });

        $scope.$on('topTags-update', function() {
            tagsFactory.getTopNTags().then(function (tags) {
                vm.tagsTop = tags;
            });
        });
    }
})();
