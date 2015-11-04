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
            if(items){
                filtered = items.slice();
            }//if
        }else{
            //if first symbol # or @ - then this is tag filter, else - fulltext filter
            if(criteria.charAt(0) == '@' || criteria.charAt(0) == '#'){
                var tagData = criteria.substring(1);
                if(tagData.length>0){
                    for(var i=0; i<items.length; i++){
                        var item = items[i];
                        if(item.tags){
                            for(var j=0; j<item.tags.length; j++){
                                var tag = item.tags[j];
                                if(tag.name.indexOf(tagData)>=0){
                                    filtered.push(item);
                                    break;
                                }//if
                            }//for
                        }//if
                    }//for
                }else{
                    filtered = items.slice();
                }//if..else..
            }else{
                for(var i=0; i<items.length; i++){
                    var item = items[i];
                    if(item.title.indexOf(criteria)>=0 || item.description.indexOf(criteria)>=0){
                        filtered.push(item);
                    }//if
                }//for
            }//if..else..
        }//if..else..

        return filtered;
    };


};
})();
