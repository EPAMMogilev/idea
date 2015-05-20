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
         .config(config);

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
                'main@': { templateUrl: 'pages/login.html' }
            },
            parent: 'root'
        }).
        state('ideaDetails', {
            url: '/ideaDetails',
            views: {
                'main@': { templateUrl: 'pages/details.html' }
            },
            onEnter:  function(){ ymaps.ready(mapInit)},
            parent: 'root'
        });
        }
})();
