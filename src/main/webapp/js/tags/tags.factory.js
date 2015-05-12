(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('tagsFactory', tagsFactory);

    tagsFactory.$inject = ['appFactory'];

    function tagsFactory(appFactory) {

        var publicMethod = {
            getTopNTags: getTopNTags,
            getIdeasByTag: getIdeasByTag
        };
        return publicMethod;

        function getTopNTags() {
            var promise = appFactory.tags().get().$promise;
            return promise;
        }


      function getIdeasByTag(tag) {
      var promise;
        if( tag === undefined)
           promise = appFactory.ideas().get().$promise;
        else
           promise = appFactory.tags().getIdeasByTag({id: tag}).$promise;
        return promise;
      }
    }
})();
