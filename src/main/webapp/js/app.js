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
		'app.controllers'
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

    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    function config($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/home');


    $stateProvider.
        state('root', {
            abstract: true
        }).
        state('home', {
            url: '/home',
            views: {
                'main@': { templateUrl: 'pages/app.html', controller: 'ideasCtrl as ideasCtrl'}
            },
            onEnter:  function(){ ymaps.ready(mapInit)},
            parent: 'root'
        }).
        state('login', {
            url: '/login',
            views: {
                'main@': { templateUrl: 'pages/login.html', controller:'LoginController as vm' }
            },
            parent: 'root'
        });
        }

       run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
            function run($rootScope, $location, $cookieStore, $http) {
                $rootScope.globals = $cookieStore.get('globals') || {};
                if ($rootScope.globals.currentUser) {
                    $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
                }

                $rootScope.$on('$locationChangeStart', function (event, next, current) {
                    var restrictedPage = $.inArray($location.path(), ['/login', '/register', '/home']) === -1;
                    var loggedIn = $rootScope.globals.currentUser;
                    if (restrictedPage && !loggedIn) {
                        $location.path('/login');
                    }
                    var loginPage = $.inArray($location.path(), ['/login']) !== -1;
                    if (loginPage && loggedIn) {
                        $location.path('/home');
                    }
                });
            }
})();

