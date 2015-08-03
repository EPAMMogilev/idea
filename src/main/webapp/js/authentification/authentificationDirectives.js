(function() {
'use strict';

angular.module('app.directives')
    .directive('compareTo', compareTo);
     function compareTo() {
        return {
            scope: {
                targetModel: '=compareTo'
            },
            require: 'ngModel',
            link: function postLink(scope, element, attrs, ctrl) {

                var compare = function() {

                    var e1 = element.val();
                    var e2 = scope.targetModel;

                    if (e2 !== null) {
                        return e1 === e2;
                    }

                    return false;
                };

                scope.$watch(compare, function(newValue) {
                    ctrl.$setValidity('errorCompareTo', newValue);
                });

            }
        };
    };
})();

(function() {
'use strict';

angular.module('app.directives').directive('ngModelOnblur',ngModelOnblur);
 function ngModelOnblur() {
    return {
        restrict: 'A',
        require: 'ngModel',
        priority: 1,
        link: function(scope, elm, attr, ngModelCtrl) {
            if (attr.type === 'radio' || attr.type === 'checkbox') return;

            elm.unbind('input').unbind('keydown').unbind('change');
            elm.bind('blur', function() {
                scope.$apply(function() {
                    ngModelCtrl.$setViewValue(elm.val());
                });
            });
        }
    };
};
})();

(function() {
'use strict';

angular.module('app.directives').directive('recordAvailabilityValidator', recordAvailabilityValidator);
recordAvailabilityValidator.$inject = ['$http', 'usersFactory'];
function recordAvailabilityValidator($http, usersFactory) {

  return {
    require : 'ngModel',
    link : function(scope, element, attrs, ngModel) {
//      var apiUrl = attrs.recordAvailabilityValidator;
        element.bind('click', function()
            {
                setAsLoading(false);
                setAsAvailable(false);
            });
            element.bind('blur', function() {
                scope.$apply(function() {
                    if(!element.val() || element.val().length == 0) return;

                        setAsLoading(true);
                        setAsAvailable(false);
                        usersFactory.getOneRegisteredByEmail(element.val()).then(
                         //success
                        function( value1 )
                        {
                          setAsLoading(false);
                          setAsAvailable(true);
                        },
                         //error
                        function( error ){
                          setAsLoading(false);
                          setAsAvailable(false);
                        }
                       );
                 });
             });
      function setAsLoading(bool) {
        ngModel.$setValidity('recordLoading', !bool);
      }

      function setAsAvailable(bool) {
        ngModel.$setValidity('recordAvailable', !bool);
      }


    }
  }
};
})();
