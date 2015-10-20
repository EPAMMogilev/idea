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


(function() {
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
                'main@': { templateUrl: 'pages/app.html', controller: 'ideasCtrl as ideasCtrl'}
            },/*
            onEnter:  function(){ ymaps.ready(mapInit)},*/
            parent: 'root'
        }).
        state('login', {
            url: '/login',
            views: {
                'main@': { templateUrl: 'pages/login.html'}
            },
            parent: 'root'
        }).
        state('register', {
            url: '/register',
            views: {
                'main@': { templateUrl: 'pages/registration.html'}
            },
            parent: 'root'
        }).
        state('myIdeas', {
            url: '/myideas',
            views: {
                'main@': { templateUrl: 'pages/app.html', controller: 'ideasCtrl as ideasCtrl'}
            },/*
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
			},/*
            onEnter:  function(){ ymaps.ready(mapInit)},*/
            parent: 'root'
        }).
        state('ideaAddNew', {
            url: '/ideaAddNew',
            views: {
                'main@': { templateUrl: 'pages/addNewIdea.html', controller: 'addNewIdea as ctrl'}
            },/*
            onEnter:  function(){ ymaps.ready(mapInit)},*/
            //onEnter:  function(){ initLoadFile()},
            parent: 'root'
        }).
        state('accessDenied', {
            url: '/accessDenied',
            views: {
                'main@': { templateUrl: 'pages/accessDenied.html', controller: 'accessDenied as ctrl'}
            },
            parent: 'root'
        }).
        state('ideaUpdate', {
            url: '/ideaUpdate:idea',
            views: {
                'main@': {
                    templateUrl: 'pages/addNewIdea.html',
                    controller: 'updateIdea as ctrl',
                    resolve: {
                        ideaDetails: ['$stateParams',
                          function ($stateParams) {

                            var idea = angular.fromJson($stateParams.idea);
                            return idea;
                          }]
                    }
                }
            },/*
            onEnter:  function(){ ymaps.ready(mapInit)},*/
            parent: 'root'
        });

        $translateProvider.useStaticFilesLoader({
          prefix: 'languages/',
          suffix: '.json'
        });
        $translateProvider.useSanitizeValueStrategy('sanitize');
        $translateProvider.preferredLanguage('ru');

    }



       run.$inject = ['$rootScope', '$location', 'authorizationService'];
           function run($rootScope, $location, authorizationService) {
               $rootScope.previousPage;
               $rootScope.$on('$locationChangeStart', function (event, next, current) {
                   var restrictedPage;
                   $rootScope.previousPage = current;
                   if ($rootScope.authenticated !== true) {
                       restrictedPage =  $.inArrayRegEx($location.path(), ['/login', '/register', '/home', '/ideaDetails', '^$']) === -1;
                       if (restrictedPage) {
                           $rootScope.previousPage = next;
                           $location.path("/login");
                       }
                   }
                   else {
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

//                    if (restrictedPage && !loggedIn) {
//                        $location.path('/login');
//                    }
//                    var loginPage = $.inArray($location.path(), ['/login']) !== -1;
//                    if (loginPage && loggedIn) {
//                        $location.path('/home');
               });
           }

           $.inArrayRegEx = function(address, array) {
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


})();

