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
