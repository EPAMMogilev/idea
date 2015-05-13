(function() {
'use strict';

angular.module('app.services')
.service('Rate',['ideasFactory'  ,Rate]);
function Rate(ideasFactory) {
       return {
        changeRate:function (mark, idea) {
          idea.rating= idea.rating + (+mark);
          ideasFactory.updateIdea(idea);
          return idea;
          }
      };
};
})();
