'use strict';
/**
 * @name ideaApp
 *
 * @description
 * A main module.
 */

angular
    .module('ideaApp', [
	    'ngResource',
	    'ui.router',
        'ui.bootstrap',
        'ngCookies',
        'angular-md5',
		'app.directives',
		'app.services',
		'app.filters',
        'ideaFactories',
		'app.controllers',
		'yaMap',
		'ngImgur',
		'ngFileUpload',
        'ngMessages',
        'pascalprecht.translate',
        'ngSanitize'
	]);

angular.module('app.directives', []); // set Directives
angular.module('app.services', []); // set Services
angular.module('app.controllers', []); // set Ctrls
angular.module('app.filters', []); // set Filters
angular.module('ideaFactories', ['ngResource']); // set Factories


(function () {
    angular
        .module('ideaApp')
        .config(config)
        .run(run);

    config.$inject = ['$stateProvider', '$urlRouterProvider', '$httpProvider', '$translateProvider'];

    function config($stateProvider, $urlRouterProvider, $httpProvider, $translateProvider) {



        $urlRouterProvider.otherwise('/home');

        $stateProvider.
        state('root', {
            abstract: true
        }).
        state('home', {
            url: '/home',
            views: {
                'main@': {
                    templateUrl: 'pages/app.html',
                    controller: 'ideasCtrl as ideasCtrl'
                }
            },
            /*
                        onEnter:  function(){ ymaps.ready(mapInit)},*/
            parent: 'root'
        }).
        state('login', {
            url: '/login',
            views: {
                'main@': {
                    templateUrl: 'pages/login.html'
                }
            },
            parent: 'root'
        }).
        state('register', {
            url: '/register',
            views: {
                'main@': {
                    templateUrl: 'pages/registration.html'
                }
            },
            parent: 'root'
        }).
        state('myIdeas', {
            url: '/myideas',
            views: {
                'main@': {
                    templateUrl: 'pages/app.html',
                    controller: 'ideasCtrl as ideasCtrl'
                }
            },
            /*
                        onEnter:  function(){ ymaps.ready(mapInit)},*/
            parent: 'root'
        }).
        state('ideaDetails', {
            url: '/ideaDetails:idea',
            views: {
                'main@': {
                    templateUrl: 'pages/details.html',
                    controller: 'detailsCtrl as detailsCtrl',
                    resolve: {
                        ideaDetails: ['$stateParams',
						  function ($stateParams) {
                                var idea = angular.fromJson($stateParams.idea);
                                return idea;
						  }]
                    }
                }
            },
            /*
                        onEnter:  function(){ ymaps.ready(mapInit)},*/
            parent: 'root'
        }).
        state('ideaAddNew', {
            url: '/ideaAddNew',
            views: {
                'main@': {
                    templateUrl: 'pages/createUpdateIdea.html',
                    controller: 'addNewIdea as ctrl'
                }
            },
            /*
                        onEnter:  function(){ ymaps.ready(mapInit)},*/
            //onEnter:  function(){ initLoadFile()},
            parent: 'root'
        }).
        state('accessDenied', {
            url: '/accessDenied',
            views: {
                'main@': {
                    templateUrl: 'pages/accessDenied.html',
                    controller: 'accessDenied as ctrl'
                }
            },
            parent: 'root'
        }).
        state('ideaUpdate', {
            url: '/ideaUpdate:idea',
            views: {
                'main@': {
                    templateUrl: 'pages/createUpdateIdea.html',
                    controller: 'updateIdea as ctrl',
                    resolve: {
                        ideaDetails: ['$stateParams',
                          function ($stateParams) {

                                var idea = angular.fromJson($stateParams.idea);
                                return idea;
                          }]
                    }
                }
            },
            /*
                        onEnter:  function(){ ymaps.ready(mapInit)},*/
            parent: 'root'
        }).
         state('profile', {
            url: '/account',
            views: {
                'main@': {
                    templateUrl: 'pages/profile.html',
                    controller: 'profileCtrl as ctrl'
                }
            },
            parent: 'root'
        });

        $translateProvider.useStaticFilesLoader({
            prefix: 'languages/',
            suffix: '.json'
        });
        $translateProvider.useSanitizeValueStrategy('sanitize');
        $translateProvider.preferredLanguage('ru');

    }



    run.$inject = ['$rootScope', '$location', 'authorizationService', 'stateService', '$q', 'authentificationService'];

    function run($rootScope, $location, authorizationService, stateService, $q, authentificationService) {

        stateService.init();
        authentificationService.init().then(function () {
            initRightsControl();
        });

        function initRightsControl() {
            $rootScope.previousPage;
            $rootScope.$on('$locationChangeStart', function (event, next, current) {
                var restrictedPage;
                $rootScope.previousPage = getStateNameFromUrl(current);
                if ($rootScope.authenticated === false) {
                    restrictedPage = $.inArrayRegEx($location.path(), ['/login', '/register', '/home', '/ideaDetails', '^$']) === -1;
                    if (restrictedPage) {
                        $rootScope.previousPage = getStateNameFromUrl(next);
                        $location.path("/login");
                    }
                } else {
                    restrictedPage = $.inArrayRegEx($location.path(), ['/ideaUpdate']) !== -1;
                    if (restrictedPage) {
                        var idea = getIdeaParamFromUrl($location.path());
                        authorizationService.redirectFromEditToAccessDeniedIfNeeded(idea);
                    } else {
                        restrictedPage = $.inArray($location.path(), ['/login']) !== -1;
                        if (restrictedPage) {
                            $location.path("/home");
                        }
                    }
                }
            });
        };

    }

    $.inArrayRegEx = function (address, array) {
        for (var i = 0; i < array.length; i++) {
            if (RegExp(array[i]).test(address)) {
                return i;
            }
        }
        return -1;
    }

    function getIdeaParamFromUrl(path) {
        var stringIdea = path.substring(path.indexOf("{"), path.indexOf("}") + 1);
        return angular.fromJson(stringIdea);
    }

    function getStateNameFromUrl(url) {
        var stateName = url.substring(url.indexOf("#") + 1);
        return stateName;
    }



})();(function() {
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
(function () {

    'use strict';

    angular
        .module('app.services')
        .service('authentificationService', authentificationService);

    authentificationService.$inject = ['$rootScope', '$http', '$cookies', '$q'];

    function authentificationService($rootScope, $http, $cookies, $q) {

        var publicMethod = {
            init: init,
            authenticate: authenticate
        };

        return publicMethod;

        function init() {
            var deffered = $q.defer();
            $http.get('user', {
                headers: $cookies
            }).success(function (data) {
                if (data.name) {
                    $rootScope.authenticated = true;
                    $rootScope.currentUser = data;
                } else {
                    $rootScope.authenticated = false;
                }
                deffered.resolve();
            }).error(function () {
                $rootScope.authenticated = false;
                deffered.resolve();
            });

            return deffered.promise;
        };

        function authenticate(credentials, callback) {
            var headers = credentials ? {
                authorization: "Basic " + btoa(credentials.email + ":" + credentials.password)
            } : {};
            $http.get('user', {
                headers: headers
            }).success(function (data) {
                if (data.name) {
                    $rootScope.authenticated = true;
                    $rootScope.currentUser = data;
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback();
            }).error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });
        };
    };
})();(function () {
    'use strict';

    angular
        .module('app.controllers')
        .controller('LoginController', LoginController);
    LoginController.$inject = ['$scope', '$http', '$location', '$rootScope', '$cookies', 'authentificationService'];

    function LoginController($scope, $http, $location, $rootScope, $cookies, authentificationService) {
        var vm = this;
        vm.login = login;
        vm.credentials = {};

        function login() {
            authentificationService.authenticate(vm.credentials, function () {
                if ($rootScope.authenticated) {
                    $location.path($rootScope.previousPage);
                    vm.error = false;
                } else {
                    $location.path("login");
                    vm.error = true;
                }
            });
        };
    }

})();(function () {
    'use strict';

    angular
        .module('app.controllers')
        .controller('RegistrationController', RegistrationController);

    RegistrationController.$inject = ['$scope', '$http', '$window', '$rootScope', 'usersFactory'];
    function RegistrationController($scope, $http,  $window, $rootScope, usersFactory) {

        var vm = this;
        vm.registrationRequest = registrationRequest;
        vm.registration = registration;
        vm.credentials = {};
        function registration (){
        var request = {
            username:vm.credentials.username,
            email:vm.credentials.email,
            password: vm.credentials.password
        };
        vm.registrationRequest(request);

        };

        function registrationRequest (user){
         
                usersFactory.createUser(user).then(
                   //success
                   function( value )
                   {
                    $window.location.href = '#home';
                    $window.location.reload();
                   },
                   //error
                   function( error ){
                    alert("Ошибка создания пользователя: " + error.statusText);
                   }
                 );
        };
    }
})();
(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('accessDenied', accessDenied);

    accessDenied.$inject = ['$scope', '$window'];

    function accessDenied($scope, $window) {
		$scope.back = function(){
			$window.location.href = '#home';
		};
    }


})();
(function () {
    'use strict';

    angular
        .module('app.services')
        .service('authorizationService', authorizationService);

        authorizationService.$inject = ['sessionService', 'ideasFactory', '$rootScope', '$location'];

        function authorizationService(sessionService, ideasFactory, $rootScope, $location) {
            var publicMethod = {
                redirectFromEditToAccessDeniedIfNeeded: redirectFromEditToAccessDeniedIfNeeded
            };
            return publicMethod;

            function redirectFromEditToAccessDeniedIfNeeded(idea) {
         	    if(!sessionService.hasRole('ROLE_ADMIN')) {
            	    ideasFactory.getIdeaById(idea.id).then(function(value) {
                	    if(!ideasFactory.isUserAuthorOfIdea($rootScope.currentUser, value)) {
                		    $location.path("/accessDenied");
                	    }
            	    });
        	    }
             };

        };
})();

(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('commentsFactory', commentsFactory);

    commentsFactory.$inject = ['restFactory'];

    function commentsFactory(restFactory) {

        var publicMethod = {
            createComment: createComment,
            getCommentsPageByIdeaId: getCommentsPageByIdeaId,
            changeCommentLike: changeCommentLike
        };
        return publicMethod;

        function createComment(comment) {
            var promise = restFactory.ideas().createComment({id: comment.subject.id}, comment).$promise;
            return promise;
        };

        function getCommentsPageByIdeaId(page, ideaId) {
            var promise = restFactory.ideas().getCommentsPage({id: ideaId, page: page.page, size: page.size, sort: page.sort}).$promise;
            return promise;
        };

        function changeCommentLike(id) {
            var promise = restFactory.comments().changeLike({id: id}).$promise;
            return promise;
        };
    }
})();(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('showComment', showComment);

	showComment.$inject = ['usersService', 'commentsFactory'];

	function showComment(usersService, commentsFactory) {
		return {
			restrict: 'A',
			replace: false,
			scope: {
				comment: '=',
				changeLike: '&',
				isAuthenticated: '='
			},
			link: function(scope, element, attrs) {
				scope.photo = (scope.comment.author.imageUrl) ? scope.comment.author.imageUrl : 'images/no_user_photo.png';
				scope.likedUsersList = usersService.getlikedUsersListAsString(scope.comment);
				scope.changeLike = function() {
					commentsFactory.changeCommentLike(scope.comment.id).then(
						function (comment) {
							scope.comment.rating = comment.rating;
							scope.comment.liked = !scope.comment.liked;
							scope.likedUsersList = usersService.getlikedUsersListAsString(comment);
						}
					);
				}
			},
			templateUrl:'templates/comment.tpl.html'
		}
	}

})();
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
(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('hoverPopover', hoverPopover);

	hoverPopover.$inject = ['$compile', '$timeout', '$rootScope'];

	function hoverPopover($compile, $timeout, $rootScope) {
		   return {
		        restrict: 'A',
		        scope: {
		            content: '@',
		            container: '@'
		        },
		        controller: ['$scope', '$element',  function ($scope, $element) {
		            $scope.attachEvents = function (element) {
		                $('.popover').on('mouseenter', function () {
		                    $rootScope.insidePopover = true;
		                });
		                $('.popover').on('mouseleave', function () {
		                    $rootScope.insidePopover = false;
		                    $(element).popover('hide');
		                });
		            };
		        }],
		        link: function (scope, element, attrs) {
		            scope.$watch(function () { return scope.content; }, function () {
		                $rootScope.insidePopover = false;
		                var popover = $(element).popover({
		                    placement: 'top',
		                    container: scope.container
		                });
		                $(element).data("bs.popover").options.content = scope.content;
		                $(element).bind('mouseenter', function (e) {
		                    $timeout(function () {
		                        if (!$rootScope.insidePopover) {
		                            $(element).popover('show');
		                            scope.attachEvents(element);
		                        }
		                    }, 50);
		                });
		                $(element).bind('mouseleave', function (e) {
		                    $timeout(function () {
		                        if (!$rootScope.insidePopover)
		                            $(element).popover('hide');
		                    }, 50);
		                });
		            });
		        }
	        };
	    }

})();(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('addNewIdea', addNewIdea);

    addNewIdea.$inject = ['$scope', '$window', '$modal', 'detailsService', 'ideasFactory', 'mapGeoService', 'imgur', 'Upload'];

    function addNewIdea($scope, $window, $modal, detailsService, ideasFactory, mapGeoService, imgur, Upload) {

        this.categories =  detailsService.getCategories();
        $scope.bottomButtonName = 'ADD';
        $scope.data = null;
        $scope.submitted = false;
        //$scope.isUpdating = false;
        $scope.imageExist = false;
        $scope.imageFile;
        //$scope.imageUrl = null;
        $scope.imageUrl = "images/photo.gif";
        //$scope.caption = 'Идет загрузка изображения. Дождитесь окончания...';
        $scope.modalInstance = null;
        $scope.files = null;
        $scope.windowTitle = 'ADD_IDEA';
        //maps data
		$scope.center = [30.331014, 53.894617];
		var map = null;
		var ideaCoords = null;

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.openModalWindowLoadingProgress = function(){
			$scope.modalInstance = $modal.open({
				animation: true,
				templateUrl: 'myModalContent.html',
				size: 'lg'
			  });
		};//openModalWindowLoadingProgress

		$scope.closeModalWindow = function(){
			//$scope.modalInstance.dismiss('cancel');
			$scope.modalInstance.close();
			$scope.modalInstance = null;
		}//closeModalWindow

		$scope.$watch('files', function(){
			if ($scope.files && $scope.files.length) {
				$scope.openModalWindow();
				$scope.load2Imgur($scope.files);
			}//if
		});

		$scope.chooseFile = function(){
    		$('input[type=file]').click();

			$('input[type=file]').change(function() {
				//read file
				var input = document.getElementById('imageLoader');

				if(input && input.files && input.files[0]){
					$scope.imageFile = input.files[0];

					$scope.openModalWindowLoadingProgress();
					/*
					//load file to imgur
					imgur.setAPIKey('Client-ID c62cfae02efe4c0');
					imgur.upload($scope.imageFile).then(function then(model) {
							

							$scope.imageUrl = model.link;

							//hide window #modalWindow
							$scope.closeModalWindow();
					});*/
					$scope.load2Imgur($scope.imageFile);
				}//if
			});
		};//chooseFile

		$scope.load2Imgur = function(file){
			//load file to imgur
			imgur.setAPIKey('Client-ID c62cfae02efe4c0');
			imgur.upload(file).then(function then(model) {
					

					if(Object.getPrototypeOf(model) === Object.prototype){
						$scope.imageUrl = model.link;
					}else{
						$scope.imageUrl = model[0].link;
					}

					$scope.imageExist = true;

					//hide window #modalWindow
					$scope.closeModalWindow();
			});
		};//load2Imgur

		$scope.doWork = function(data){
		    //find tag name
			//todo:make more input fields for more tags and use this for
			$scope.submitted = true;
			if ($scope.ideaForm.$invalid) {return;}
			var tags = new Array();
			for(var i=0; i<1; i++){
				if (tags[i] != null && tags[i] != undefined) {
					tags.push(
						{
							id:i,
							name:data.tagName
						}
					);
				}
			}//for


			var request = {
				id:null,
				description:data.description,
				title:data.title,
				createdAt:new Date().getTime(),
				lastModifiedAt:new Date().getTime(),
				tags: tags,
				latitude: (ideaCoords)?ideaCoords[1]:0,
				longitude: (ideaCoords)?ideaCoords[0]:0
			};

			if($scope.imageExist == true){
				request.imageUrl = $scope.imageUrl;
			}//if..
			$scope.insertIdea(request);
		};

		$scope.insertIdea = function(idea){
				ideasFactory.insertIdea(idea).then(
				   //success
				   function( value )
				   {
					$window.location.href = '#home';
				   },
				   //error
				   function( error ){
					alert("Ошибка создания идеии: " + error.statusText);
				   }
				 );
		};//insertIdea

		$scope.beforeInit = function(){
			var geolocation = ymaps.geolocation;

			ideaCoords = [geolocation.longitude, geolocation.latitude];
		};

		$scope.afterInit = function($map){
			map = $map;

			if(ideaCoords){
				mapGeoService.setGeoCoordsDirective(map, ideaCoords);
			}//if
		};

		$scope.mapClick = function(e){
			var coords = e.get('coords');
			//alert(coords.join(', '));
			ideaCoords = coords;
			var geoPoints = {
				latitude: coords[1],
				longitude: coords[0]
			};

			mapGeoService.setGeoCoordsDirective(map, geoPoints);
		};
    }


})();
(function(){
	'use strict';

	angular
		.module('app.controllers')
		.controller('detailsCtrl', detailsCtrl);

	detailsCtrl.$inject = ['$scope', '$window', '$modal', '$state', '$rootScope', 'ideasFactory', 'ideaDetails', 'mapGeoService', 'usersService', 'commentsFactory'];

	function detailsCtrl($scope, $window, $modal, $state, $rootScope, ideasFactory, ideaDetails, mapGeoService, usersService, commentsFactory) {
		$scope.modalInstance = null;
		$scope.ideaDetails = ideaDetails;
		$scope.likedUsersList = null;
		$scope.idea = null;
		$scope.myMap = null;

		$scope.commentBody = null;

    	var vm = this;
    	vm.comments = null;
		vm.paramsForComments = {
				page: 0,
				size: 10,
				sort: 'creationTime,desc'
			};

		$scope.loadModalLargeMap = function(){
			$scope.modalInstance = $modal.open({
				animation: true,
				templateUrl: 'pages/public/largeMap.html',
				controller: 'modalLargeMap',
				resolve: {
					geo: function () {
			            return $scope.geoPoints;
			        }
			    }	
			  });
		};//loadModalLargeMap
		
		this.loadPageForComments = function(){
			vm.paramsForComments.page++;
			var promiseResponse = commentsFactory.getCommentsPageByIdeaId(vm.paramsForComments, $scope.ideaDetails.id);
			promiseResponse.then(function (comments) {
				if (comments) {
					vm.comments = vm.comments.concat(comments);
				}
			});
			return promiseResponse;
		};

		$scope.isAuthor = function(ideaDetails) {
			return ideasFactory.isUserAuthorOfIdea($rootScope.currentUser, ideaDetails);
		};

		this.promises = ideasFactory.getIdeaById($scope.ideaDetails.id).then(
			//success
			function( value )
			{
				$scope.idea = value;
				if($scope.idea != null) {
					$scope.likedUsersList = usersService.getlikedUsersListAsString($scope.idea);
				}

				//set geo point
				if($scope.idea && $scope.idea.latitude && $scope.idea.longitude){
					$scope.geoPoints = {
						latitude: $scope.idea.latitude,
						longitude: $scope.idea.longitude
					};
					mapGeoService.setGeoCoordsSimpleMap($scope.myMap, $scope.geoPoints);
				}//if

				//set image
				if($scope.idea != null && $scope.idea.imageUrl == null){
				$scope.idea.imageUrl = "images/300x300.png";
				}//if
			}
		);

		commentsFactory.getCommentsPageByIdeaId(vm.paramsForComments, $scope.ideaDetails.id).then(
			//success
			function(comments)
			{
				vm.comments = comments;
			}
		);

		$scope.edit = function(){
			$state.go('ideaUpdate', { 'idea': angular.toJson($scope.ideaDetails) });
		};

		$scope.remove = function(){
			ideasFactory.removeIdea($scope.ideaDetails).then(
				function( value )
				{
					alert("Удалено");
					$state.go('home');
				});
		};

		$scope.changeMapVisibility = function(){
			$("#map").toggle();
		};

		$scope.changeLike = function() {
			ideasFactory.changeIdeaLike($scope.ideaDetails.id).then(
				function (ideaDetails) {
					$scope.idea.rating = ideaDetails.rating;
					$scope.idea.liked = !$scope.idea.liked;
					$scope.likedUsersList = usersService.getlikedUsersListAsString(ideaDetails);
				}
			);
		};

		//init function: load map point
		$scope.init = function(){
			$scope.myMap = new ymaps.Map("map", {
				center: mapGeoService.getMapCenter(),
				zoom: 11
			});
		}

		$scope.addComment = function(){
			var request = {
					id: null,
					body: $scope.commentBody,
					subject: {
						id: $scope.ideaDetails.id
					}
				};
			commentsFactory.createComment(request).then(
				function (addedComment) {
					vm.comments.unshift(addedComment);
					$scope.commentBody = null;
				}
			);
		};

		$scope.cancelAddComment = function(){
			$scope.commentBody = null;
		};
		
	}

})();(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('updateIdea', updateIdea);

    updateIdea.$inject = ['$scope', '$window', '$modal', 'ideasFactory', 'stateService', 'ideaDetails', 'mapGeoService', 'imgur', 'Upload'];

    function updateIdea($scope, $window, $modal, ideasFactory, stateService, ideaDetails, mapGeoService, imgur, Upload) {

        $scope.states = stateService.getIdeaStates();
    	
        $scope.bottomButtonName = 'UPDATE';
        $scope.idea = ideaDetails;
        $scope.data = null;
        $scope.data
        
        $scope.imageExist = false;
        $scope.imageFile;
        $scope.imageUrl = null;
        $scope.modalInstance = null;
        $scope.files = null;
        $scope.windowTitle = 'CHANGE_IDEA';
        
        $scope.isUpdating = true;
        
        //maps data
		$scope.center = [30.331014, 53.894617];
		var map = null;
		var ideaCoords = null;

		$scope.openModalWindow = function(){
			$scope.modalInstance = $modal.open({
				animation: true,
				templateUrl: 'myModalContent.html',
				size: 'lg'
			  });
		};//openModalWindow

		$scope.closeModalWindow = function(){
			//$scope.modalInstance.dismiss('cancel');
			$scope.modalInstance.close();
			$scope.modalInstance = null;
		}//closeModalWindow

		$scope.$watch('files', function(){
			if ($scope.files && $scope.files.length) {
				$scope.openModalWindow();
				$scope.load2Imgur($scope.files);
			}//if
		});

		$scope.chooseFile = function(){
    		$('input[type=file]').click();

			$('input[type=file]').change(function() {
				//read file
				var input = document.getElementById('imageLoader');

				if(input && input.files && input.files[0]){
					$scope.imageFile = input.files[0];

					$scope.openModalWindow();
					$scope.load2Imgur($scope.imageFile);
				}//if
			});
		};//chooseFile

		$scope.load2Imgur = function(file){
			//load file to imgur
			imgur.setAPIKey('Client-ID c62cfae02efe4c0');
			imgur.upload(file).then(function then(model) {
					

					if(Object.getPrototypeOf(model) === Object.prototype){
						$scope.imageUrl = model.link;
					}else{
						$scope.imageUrl = model[0].link;
					}

					$scope.imageExist = true;

					//hide window #modalWindow
					$scope.closeModalWindow();
			});
		};//load2Imgur

        this.promises = ideasFactory.getIdeaById($scope.idea.id).then(
        		//success
        		function( value ) {
        			$scope.data = value;
        			//set geo point
        			if($scope.data && $scope.data.latitude && $scope.data.longitude && map){
        				var geoPoints = {
        					latitude: $scope.data.latitude,
        					longitude: $scope.data.longitude
                        };
        				$scope.imageUrl = $scope.data.imageUrl;
        				mapGeoService.setGeoCoordsDirective(map, geoPoints);
        			}//if
        		}
        );

		$scope.back = function(){
            $window.location.href = '#home';
		};

		$scope.doWork = function(data){
			if ($scope.ideaForm.$invalid) {return;}
			var idea = ideasFactory.getIdeaById(data.id);

			//todo:make more input fields for more tags and use this for
			var tags = new Array();
			for(var i=0; i<1; i++){
				tags.push(
					{
						id:i,
						name:data.tagName
					}
				);
			}//for

		    //create request
		    var request = {
		        id:data.id,
		        description:data.description,
		        title:data.title,
		        rating:idea.rating,
		        createdAt:idea.createdAt,
		        lastModifiedAt:new Date().getTime(),
                links:data.links,
                tags:tags,
                author:idea.author,
                latitude: (ideaCoords)?ideaCoords[1]:0,
				longitude: (ideaCoords)?ideaCoords[0]:0,
				state: data.state
		    };

			if($scope.imageExist == true){
				request.imageUrl = $scope.imageUrl;
			}//if..

            //ideasFactory.updateIdea(request);

			ideasFactory.updateIdea(request).then(
			   //success
			   function( value )
			   {
				$window.location.href = '#home';
			   },
			   //error
			   function( error ){
				alert("Ошибка обновления идеии: " + error.statusText);
			   }
			 );
		};

		$scope.afterInit = function($map){
			map = $map;
		};

		$scope.mapClick = function(e){
			var coords = e.get('coords');
			//alert(coords.join(', '));
			ideaCoords = coords;

			//map.Point.show(coords);
			map.balloon.open(coords, 'Моя идея');
		};
    }


})();
(function () {
    'use strict';
    angular
        .module('app.controllers')
        .controller('ideasCtrl', ideasCtrl);

    ideasCtrl.$inject = ['$scope', '$location', '$rootScope', 'ideasFactory', 'tagsFactory', 'mapGeoService'];

    function ideasCtrl($scope, $location, $rootScope, ideasFactory, tagsFactory, mapGeoService) {


        var vm = this;
        var mapObject;
        vm.userId = null;
        vm.tabAll = 'TAB_ALL';
        vm.query = null;
        vm.tag = null;
        vm.popular = {
            list: [],
            params: {
                page: 0,
                size: 5,
                sort: 'rating,desc'
            }
        };
        vm.latest = {
            list: [],
            params: {
                page: 0,
                size: 5,
                sort: 'creationTime,desc'
            }
        };

        $scope.geoObjects = null;
        $scope.criteria = null;
        $scope.mapCenter = mapGeoService.getMapCenter();
        $scope.mapZoom = mapGeoService.getMapDefaultZoom();

        $scope.afterMapInit = function (map) {
            mapObject = map;
        };

        $scope.$on('query-update', function (event, query) {
            vm.query = query;
            vm.selectByQuery();
        });

        $scope.mouseenter = function (e) {
            e.get('target').options.set('preset', 'islands#greenIcon');
        };

        $scope.mouseleave = function (e) {
            e.get('target').options.unset('preset');
        };

        function onMyIdeasPage() {
            var result = false;
            if ($location.path().indexOf("/myideas") !== -1) {
                result = true;
            }
            return result;
        }

        function changeTabAllIdeas() {
            if (onMyIdeasPage()) {
                vm.tabAll = 'TAB_ALL_MY';
            }
        }

        function activate() {
            if (onMyIdeasPage()) {
                vm.userId = $rootScope.currentUser.id;
            } else {
                vm.userId = null;
            }

            getList(vm.popular);
            getList(vm.latest);



            vm.query = null;
            changeTabAllIdeas();
            $rootScope.$broadcast('query-clean');
        }


        function getList(listObject, update) {
            return ideasFactory.getPage(listObject.params, vm.userId, vm.tag, vm.query).then(function (ideas) {
                if (ideas && update) {
                    listObject.list = listObject.list.concat(ideas);
                } else {
                    listObject.list = ideas;
                }
                $scope.geoObjects = mapGeoService.generateGeoObjects(listObject.list);
            });
        }

        vm.update = function (listObject) {
            listObject.params.page++;
            return getList(listObject, true);
        }

        vm.selectByCategory = function (tag) {
            vm.tag = tag;
            vm.popular.params.page = 0;
            vm.latest.params.page = 0;

            getList(vm.popular);
            getList(vm.latest);
        }

        vm.selectByQuery = function () {
            vm.popular.params.page = 0;
            vm.latest.params.page = 0;

            getList(vm.popular);
            getList(vm.latest);
        }

        vm.details = function (idea) {
            var ideaDetail = {
                id: idea.id
            };

            console.log('Go to Details'); /*RemoveLogging:skip*/
            $state.go('ideaDetails', {
                'idea': angular.toJson(ideaDetail)
            });
        };

        activate();

    }

})();(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('ideasFactory', ideasFactory);

    ideasFactory.$inject = ['restFactory'];

    function ideasFactory(restFactory) {

        var publicMethod = {
            getIdeas: getIdeas,
            getIdeaById: getIdeaById,
            insertIdea: insertIdea,
            updateIdea: updateIdea,
            removeIdea: removeIdea,
            changeIdeaLike: changeIdeaLike,
            getPage: getPage,
            isUserAuthorOfIdea: isUserAuthorOfIdea 
        };
        return publicMethod;

        function isUserAuthorOfIdea(user, idea) {
			if(idea && user) {
				if (user.id == idea.author.id) {
					return true;
				}
			}
			return false;
		}

        function getIdeas() {
            var promise = restFactory.ideas().get().$promise;
            return promise;
        };

        function getIdeaById(ideaId) {
            var promise = restFactory.ideas().show({id: ideaId}).$promise;
            return promise;
        };

        function updateIdea(idea) {
            var promise = restFactory.ideas().update({id: idea.id}, idea).$promise;
            return promise;
        };

        function insertIdea(idea) {
            var promise = restFactory.ideas().create(idea).$promise;
            return promise;
        };

        function removeIdea(idea) {
            var promise = restFactory.ideas().delete(idea).$promise;
            return promise;
        };

        function changeIdeaLike(id) {
            var promise = restFactory.ideas().changeLike({id: id}).$promise;
            return promise;
        };

        function getPage(page, userId, tag, query) {
            var promise = restFactory.ideas().getPage({userId: userId, page: page.page, size: page.size, sort: page.sort, tagId: tag != null ? tag.id : null, query: query}).$promise;
            return promise;
        };
    }
})();(function(){
	'use strict';

	angular
		.module('app.controllers')
		.controller('ideasSearchCtrl', ideasSearchCtrl);

	ideasSearchCtrl.$inject = ['$rootScope', '$scope', '$location'];

	function ideasSearchCtrl($rootScope, $scope, $location) {

		var vm = this;
		$scope.query = null;

		vm.changeQuery = function() {
			$rootScope.$broadcast('query-update', $scope.query);
		}

		vm.isSearchVisible = function() {
			if($location.path().indexOf("/myideas") !== -1 || $location.path().indexOf("/home") !== -1) {
				return true;
			} else {
				return false;
			}
		}

		$scope.$on('query-clean', function() {
			$scope.query = null;
		});
	}
})();(function () {
    'use strict';

    angular
        .module('app.services')
        .service('mapGeoService', mapGeoService);

    mapGeoService.$inject = ['$translate'];

    function mapGeoService($translate) {

        var myIdea = $translate.instant('MY_IDEA');
        var idea = $translate.instant('IDEA');

        return {
            setGeoCoordsDirective: function (map, coords) {
                if (coords) {
                    var coords = [coords.longitude, coords.latitude];
                    map.balloon.open(coords, myIdea);
                } //if
            }, //setGeoCoordsDirective
            setGeoCoordsSimpleMap: function (map, coords) {
                if (coords) {
                    var geoPoint = new ymaps.Placemark([coords.longitude, coords.latitude], null, {
                        preset: "islands#greenStretchyIcon"
                    });

                    map.geoObjects.add(geoPoint);
                } //if
            }, //setGeoCoordsSimpleMap
            generateGeoObjects: function (ideas) {
                var geoObjects = new Array();
                if (ideas) {
                    for (var i = 0; i < ideas.length; i++) {
                        var oldObject = ideas[i];

                        if (oldObject && oldObject.longitude && oldObject.latitude) {
                            var newGeoObject = {
                                geometry: {
                                    type: 'Point',
                                    coordinates: [oldObject.longitude, oldObject.latitude]
                                },
                                properties: {
                                    balloonContent: oldObject.title,
                                    clusterCaption: idea + ' #' + oldObject.id
                                }
                            };
                            //add 2 array
                            geoObjects.push(newGeoObject);
                        } //if
                    } //for
                } //if
                return geoObjects;
            }, //generateGeoObjects
            getMapCenter: function () {
                return [30.331014, 53.894617];
            }, //getMapCenter
            getMapDefaultZoom: function () {
                    return 11;
                } //getMapDefaultZoom
        };
    };
})();(function(){
    'use strict';

	angular
	    .module('app.controllers')
	    .controller('modalLargeMap', modalLargeMap);

	modalLargeMap.$inject = ['$scope', '$rootScope', '$modalInstance', 'mapGeoService', 'geo'];

    function modalLargeMap($scope, $rootScope, $modalInstance, mapGeoService, geo){
		
    	$scope.mapModal = null;
    	
    	$scope.close = function() {
			$modalInstance.close();
        }
          		  
    	$scope.initModal = function(){
			$scope.mapModal = new ymaps.Map("mapModal", {
				center: mapGeoService.getMapCenter(),
				zoom: 11
			});
			
			mapGeoService.setGeoCoordsSimpleMap($scope.mapModal, geo);
		}
    	
	}
    
    
})();(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('showIdea', showIdea);

	showIdea.$inject = ['ideasFactory', '$state'];

	function showIdea(ideasFactory, $state) {
		return {
			restrict: 'E',
			replace: true,
			scope: {
				photo: '@',
				idea: '=',
				ngModel: '=',
				details: '&',
				changeRating: '&',
				changeLike: '&',
				isAuthenticated: '='

			},
			link: function(scope, element, attrs) {
				scope.photo = (scope.idea.imageUrl)?scope.idea.imageUrl:'images/photo.gif';
				scope.details = function() {
					var ideaDetail = {
						id: scope.idea.id
					};
					$state.go('ideaDetails', { 'idea': angular.toJson(ideaDetail) });
				};
				scope.changeRating = function (mark) {
					scope.idea.rating += mark;
					ideasFactory.updateIdea(scope.idea);
				};
				scope.changeLike = function() {
					ideasFactory.changeIdeaLike(scope.idea.id).then(
						function (idea) {
							scope.idea.rating = idea.rating;
						}
					);
					scope.idea.liked = !scope.idea.liked;
				}
			},
			templateUrl:'templates/idea.tpl.html'
		}
	}

})();
(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('ideaStateLabel', ideaStateLabel);
	
	ideaStateLabel.$inject = ['$compile'];
	
	function ideaStateLabel($compile) {
		return {
			restrict: 'A',
			replace: true,
			compile: function compile(element, attrs) {
				element.attr('ng-class', "{'label label-success' : idea.state.name == 'New', 'label label-warning' : idea.state.name == 'Assigned', 'label label-danger' : idea.state.name == 'Deleted', 'label label-info' : idea.state.name == 'InProgress'}");
				element.removeAttr('idea-state-label');
				return {
					pre: function preLink(scope, iElement, iAttrs, controller) {  },
					post: function postLink(scope, iElement, iAttrs, controller) {  
						$compile(iElement)(scope);
					}
		        }
		      }
		    }
	}

})();
(function() {
	'use strict';

	angular
		.module('app.directives')
		.directive('showIdeaStateUpdate', showIdeaStateUpdate);

	function showIdeaStateUpdate() {
		return {
			restrict: 'E',
			replace: true,
			templateUrl:'templates/ideaStateUpdate.tpl.html'
		}
	}

})();
(function () {
    'use strict';

    angular.module('app.services')
        .service('stateService', stateService);

    stateService.$inject = ['$http'];

    function stateService($http) {

        var ideaStates = null;

        var promise = $http.get('api/v1/states/').success(function (data) {
            ideaStates = data;
        });

        return {

            getIdeaStates: function () {
                return ideaStates;
            }, //getIdeaStates

            init: function () {
                    return promise;
                } //init

        };
    };
})();function mapInit(){
	var myMap = new ymaps.Map("map", {
		//[53.894617; 30.331014]
		center: [53.894617, 30.331014],
		zoom: 11,
		controls: ["zoomControl", "fullscreenControl"]
	});

    addMapClickEvent(myMap);
}

function mapInitIdeaDetails(idea){
	var myMap = new ymaps.Map("map", {
		//[53.894617; 30.331014]
		center: [53.894617, 30.331014],
		zoom: 11,
		controls: ["zoomControl", "fullscreenControl"]
	});

    if(idea && idea.latitude && idea.longitude){
        var geoPoint = new ymaps.Placemark([idea.latitude, idea.longitude], null,{
            preset: "islands#redIcon"
        });

        myMap.geoObjects.add(geoPoint);
    }//if

    addMapClickEvent(myMap);
}

function addMapClickEvent(map){

    map.events.add('click', function(e){
    	var coords = e.get('coords');
    	//alert(coords.join(', '));

    	map.geoObjects.removeAll();

        var geoPoint = new ymaps.Placemark([coords[0], coords[1]], null,{
            preset: "islands#redIcon"
        });

        map.geoObjects.add(geoPoint);
    });
}(function () {
	'use strict';
	angular
		.module('ideaFactories')
		.factory('restFactory', restFactory);

	restFactory.$inject = ['$resource'];

	function restFactory($resource) {

		var factory = {
			ideas: ideas,
			tags: tags,
			users: users,
			comments: comments
		};

		return factory;

		function tags() {
			return $resource('api/v1/tags/', {}, {
				get: {method: 'GET', isArray: true},
				getIdeasByTag: {method: 'GET', params: {id: '@id'}, url: 'api/v1/tags/:id/ideas/', isArray: true},
				});
		}

		function ideas() {
			return $resource('api/v1/ideas/', {}, {
				get: {method: 'GET', isArray: true},
				show: {method: 'GET', params: {ideaId: '@id'}, url: 'api/v1/ideas/:id/'},
				update: {method: 'PUT', params: {id: '@id'}, url: 'api/v1/ideas/:id/'},
				create: {method: 'POST', url: 'api/v1/ideas/'},
				delete: {method: 'DELETE', url: 'api/v1/ideas/:id/'},
				changeLike: {method: 'POST', params: {id: '@id'}, url: 'api/v1/ideas/:id/like/'},
				createComment: {method: 'POST', params: {id: '@id'}, url: 'api/v1/ideas/:id/comments/'},
				getCommentsPage: {method: 'GET', params: {id: '@id', page: '@page', size: '@size', sort: '@sort'}, url: 'api/v1/ideas/:id/comments?page=:page&size=:size&sort=:sort', isArray: true},
				getPage: {method: 'GET', params: {userId: '@userId', page: '@page', size: '@size', sort: '@sort', tagId: '@tagId', query: "@query"}, url: 'api/v1/ideas?page=:page&size=:size&sort=:sort&sort=title,asc&userId=:userId&tagId=:tagId&query=:query', isArray: true}
			});
		}

		function users() {
			return $resource('api/v1/users/', {}, {
				get: {method: 'GET', isArray: true},
				getOne: {method: 'GET', params: {id: '@id'}, url: 'api/v1/users/:id/'},
				create: {method: 'POST', url: 'user/register/'},
				getOneRegisteredByEmail: {method: 'GET', params: {email: '@email'}, url: 'api/v1/users/:email/email'},
				update: {method: 'PUT', params: {id: '@id'}, url: 'api/v1/users/:id/'},
			});
		}

		function comments() {
			return $resource('api/v1/comments/', {}, {
				changeLike: {method: 'POST', params: {id: '@id'}, url: 'api/v1/comments/:id/like/'}
			});
		}
		
		function ideaStates() {
			return $resource('api/v1/states/', {}, {
				get:  {method:'GET', isArray:true}
			
			});
		}
	}
})();(function () {
    'use strict';

    angular
        .module('app.directives')
        .directive('scrollableList', scrollableList);

    scrollableList.$inject = [];

    function scrollableList() {
        return {
            restrict: 'A',
            scope: {
                updateFunction: '&'
            },
            link: function (scope, element, attrs) {
                    var scrollFunc = function () {
                        var workDiv = $(element);
                        if (workDiv) {
                            if (workDiv[0].scrollHeight - workDiv.scrollTop() == workDiv.outerHeight()) {
                                element.unbind('scroll');
                                scope.updateFunction().then(function () {
                                    element.bind('scroll', scrollFunc);
                                });
                            }
                        } //if
                    };
                    element.bind('scroll', scrollFunc);
                } //link
        }
    }

})();(function() {
'use strict';

/*
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
*/

angular.module('app.services')
.service('detailsService', detailsService);
function detailsService() {

       return{
            getCategories:function(){
                return [
                    {
                        id:0,
                        descr:'Sport'
                    },
                    {
                        id:1,
                        descr:'Transport'
                    },
                    {
                        id:2,
                        descr:'Культура'
                    }
                    ];
            }//getCategories
       };
};
})();(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('sessionFactory', sessionFactory);

    sessionFactory.$inject = ['restFactory'];

    function sessionFactory(restFactory) {

        var publicMethod = {
            createSession: createSession,
            deleteSession: deleteSession,
            getSession: getSession
        };
        return publicMethod;

        function createSession(user) {
            var promise = restFactory.users().createSession(user).$promise;
            return promise;
        }

         function deleteSession(sessionId) {
            var promise = restFactory.users().deleteSession({sessionId: sessionId}).$promise;
            return promise;
         }

          function getSession(sessionId) {
             var promise = restFactory.users().getSession({sessionId: sessionId}).$promise;
             return promise;
          }
    }
})();

(function () {
    'use strict';
    angular
        .module('app.controllers')
        .controller('sessionCtrl', sessionCtrl);

    sessionCtrl.$inject = ['$rootScope', '$http', '$location', '$translate', 'sessionService', 'authentificationService'];

    function sessionCtrl($rootScope, $http, $location, $translate, sessionService, authentificationService) {

        var vm = this;
        vm.logout = sessionService.logout;

        vm.useLang = function (lang) {
            $translate.use(lang);
        }

        vm.getCurrentLang = function () {
            return $translate.use();
        }

        vm.hasRole = function (role, user) {
            return sessionService.hasRole(role, user);
        }

        vm.getIconClassForCurrentUser = function () {
            var user = $rootScope.currentUser;
            return sessionService.getIconClassForCurrentUser(user);
        }
    }



})();(function () {
    'use strict';

    angular
        .module('app.services')
        .service('sessionService',sessionService);

         sessionService.$inject = ['$rootScope', '$http', '$location'];

        function sessionService($rootScope, $http, $location) {
            var sessionID = '';
            var user = {};
            var publicMethod = {
                getSessionId: getSessionId,
                setSessionId: setSessionId,
                getUser: getUser,
                setUser: setUser,
                hasRole: hasRole,
                getIconClassForCurrentUser: getIconClassForCurrentUser,
                logout: logout
            };
            return publicMethod;

            function logout() {
                $http.post("logout", {}).success(function () {
                    $rootScope.authenticated = false;
                    $rootScope.currentUser = {};
                    $location.path("/home");
                }).error(function (data) {
                    
                });
            }

            function getSessionId() {
                if(sessionID=='' || sessionID==null)
                    sessionID = localStorage.getItem("SessionId");
                return sessionID;
            };

            function setSessionId (sessId) {

                localStorage.setItem("SessionId", sessId);
                sessionID = sessId;
                return;
            };

            function getUser () {
                if(user=='' || user==null)
                    user = localStorage.getItem("user");
                return user;
            };

            function setUser (user_) {
                localStorage.setItem("user", user_);
                user = user_;
                return;
            };

            function hasRole (role, user) {
                if(user == undefined) {
                    if($rootScope.currentUser == undefined) {
                    return false;
                        } else {
                            user = $rootScope.currentUser;
                        }
                    }

                for (var i = 0; i < user.authorities.length; i++) {
                    if(user.authorities[i].authority == role) {
                        return true;
                    }

                return false;
                };

            };

            function getIconClassForCurrentUser(user) {
                if(user.socialSignInProvider === 'GOOGLE') {
                	return 'fa fa-google-plus-square color-google-plus';
                } else {
                	if(user.socialSignInProvider === 'FACEBOOK') {
                		return 'fa fa-facebook-square color-facebook';
                	} else {
                		if(user.socialSignInProvider === 'VKONTAKTE') {
                			return 'fa fa-vk color-vk';
                		} else {
                			return 'fa fa-user';
                		}
                	}
                }
            };
    };
})();

(function () {
    'use strict';

    angular
        .module('app.directives')
        .directive('tagButton', tagButton);

    function tagButton() {
        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            scope: {
                active: '=',
                click: '&',
                identifier: '@'
            },
            link: function (scope, element, attrs) {
                if (scope.active == true) element.addClass('active');
                element.bind('click', function () {
                    getElementByClass('.active').removeClass('active');
                    element.addClass('active');
                    scope.$apply(function () {
                        scope.click({
                            tag: element.text()
                        });
                    });
                });
                
                function getElementByClass(className) {
                    return angular.element($("#tag-buttons").find(className)[0]);
                }
            },
            template: [
					'<a id="{{identifier}}" type="button" class="btn btn-default" ng-transclude></a>'
			].join('')
        }
    }
})();(function(){
    'use strict';
    angular
        .module('app.controllers')
        .controller('tagsCtrl', tagsCtrl);

    tagsCtrl.$inject = ['$scope', 'tagsFactory'];

    function tagsCtrl($scope, tagsFactory) {

        var vm = this;
        tagsFactory.getTopNTags().then(function (tags) {
            vm.tagsTop = tags;
        });

        $scope.$on('topTags-update', function() {
            tagsFactory.getTopNTags().then(function (tags) {
                vm.tagsTop = tags;
            });
        });
    }
})();
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
(function(){
    'use strict';

    angular
        .module('app.controllers')
        .controller('profileCtrl', profileCtrl);

    profileCtrl.$inject = ['$scope', '$window', '$modal', 'usersFactory', 'sessionService'];

    function profileCtrl($scope, $window, $modal, usersFactory, sessionService) {

        var imageUrl = $scope.currentUser.imageUrl ? $scope.currentUser.imageUrl : "images/logo.png"

        $scope.profile = {id: $scope.currentUser.id, username: $scope.currentUser.username, email: $scope.currentUser.email, password: $scope.currentUser.password, imageUrl: imageUrl};

        $scope.back = function(){
            $window.location.href = '#home';
        };

		$scope.doWork = function(data) {
            if ($scope.profileForm.$invalid) {return;}

            var request = {
                id: data.id,
                email: data.email,
                password: data.password
            };

            usersFactory.updateUser(request).then(
               //success
               function( value ) {
                   sessionService.logout();
               },
               //error
               function( error ){
                   alert("Ошибка обновления профайла пользователя: " + error.statusText);
               }
           );
        };

    }


})();
(function(){
    'use strict';

    angular
        .module('ideaFactories')
        .factory('usersFactory', usersFactory);

    usersFactory.$inject = ['restFactory'];

    function usersFactory(restFactory) {

        var publicMethod = {
            get: get,
            getOne: getOne,
            createUser: createUser,
            getOneRegisteredByEmail: getOneRegisteredByEmail,
            updateUser: updateUser
        };
        return publicMethod;

        function get() {
            restFactory.ideas().get().$promise;
        };

        function getOne(id) {
            restFactory.ideas().getOne({id: id}).$promise;
        };

        function createUser(user) {
            var promise = restFactory.users().create(user).$promise;
            return promise;
        };

        function getOneRegisteredByEmail(email) {
            var promise = restFactory.users().getOneRegisteredByEmail({email: email}).$promise;
            return promise;
        };

        function updateUser(user) {
            var promise = restFactory.users().update(user).$promise;
            return promise;
        };
    }
})();
(function () {
    'use strict';

    angular
        .module('app.services')
        .service('usersService',usersService);

        function usersService() {
            var publicMethod = {
            	getlikedUsersListAsString: getlikedUsersListAsString
            };
            return publicMethod;

    		function getlikedUsersListAsString(obj){
    			var users = [];
    			for(var i = 0; i < obj.likedUsers.length; i++) {
    				users.push(obj.likedUsers[i].username);
    			}
    			return users.join(", ");
    		};
    };
})();

