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

/*angular.module('app.services')
.service('detailsService',[detailsService]);
function detailsService() {
       this._data = {};

       return{
            getData:function(){
                return this._data;
            },
            setData:function(data){
                this._data = data;
            }
       };
};*/

})();
