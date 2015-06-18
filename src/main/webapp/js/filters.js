(function() {
'use strict';

angular
		.module('app.filters')
		.filter('highlightTextInDescriptionOfIdea', ['$sce',  highlightText]);

 function highlightText ($sce){
    return function(text, criteria){
    if (criteria) text = text.replace(new RegExp('('+criteria+')', 'gi'),
            '<span class="highlight">$1</span>')

        return $sce.trustAsHtml(text);
        
    };
    
    
};
})();

(function() {
'use strict';

angular
		.module('app.filters')
		.filter('searchLineFilter', searchLineFilter);

 function searchLineFilter (){
    return function(items, criteria){
        var filtered = [];

        if(!criteria || 0 === criteria.length){
            filtered = items.slice();
        }else{
            for(var i=0; i<items.length; i++){
                var item = items[i];
                if(item.title.indexOf(criteria)>=0 || item.description.indexOf(criteria)>=0){
                    filtered.push(item);
                }//if
            }//for
        }//if..else..

        return filtered;
    };


};
})();
