(function() {
'use strict';

angular.module('app.services')
.service('IdeasSelectedByTag',  ['ideasFactory', IdeasSelectedByTag]);
function IdeasSelectedByTag (ideasFactory) {
       return {
        getIdeas:function (tag) {
         var newData;
         ideasFactory.getIdeas().then(function (ideas) {
            newData = ideas;
         });

          if(tag !== undefined) {
              newData = [];
              for(var i=0;i<ideasFactory.getIdeas().length; i++)
                if(tag.toLowerCase() ==ideasFactory.getIdeas()[i].tag.toLowerCase())
                  newData.push(ideasFactory.getIdeas()[i]);
            }
            return newData;
          }
        };
       
};

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
