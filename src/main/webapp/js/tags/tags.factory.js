(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('tagsFactory', tagsFactory);

    tagsFactory.$inject = ['restFactory'];

    function tagsFactory(restFactory) {

        var publicMethod = {
            getTopNTags: getTopNTags,
            getIdeasByTag: getIdeasByTag
        };
        return publicMethod;

        function getTopNTags() {
            var promise = restFactory.tags().get().$promise;
            return promise;
        }


      function getIdeasByTag(tag) {
      var promise;
        if( tag === undefined)
           promise = restFactory.ideas().get().$promise;
        else
           promise = restFactory.tags().getIdeasByTag({id: tag}).$promise;
        return promise;
      }
    }
})();
