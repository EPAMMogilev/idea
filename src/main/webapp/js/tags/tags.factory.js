(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('tagsFactory', tagsFactory);

    tagsFactory.$inject = ['crudFactory'];

    function tagsFactory(crudFactory) {

        var publicMethod = {
            getTopNTags: getTopNTags,
            getIdeasByTag: getIdeasByTag
        };
        return publicMethod;

        function getTopNTags() {
            var tags = crudFactory.tags().get().$promise;
            return tags;
        }


      function getIdeasByTag(tag) {
        if( tag === undefined)
           var ideas = crudFactory.ideas().get().$promise;
        else
            var ideas = crudFactory.tags().getIdeasByTag({id: tag}).$promise;
        return ideas;
      }
    }
})();
