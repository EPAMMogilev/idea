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

        $translateProvider.translations('en', {
            USERNAME: 'Username',
            PASSWORD: 'Password',
            ENTER_USERNAME: 'Enter your username',
            ENTER_PASSWORD:'Enter your password',
            ENTER_EMAIL:'Enter your email',
            CONFIRM_PASSWORD:'Confirm your password',
            PASSWORD_DOES_NOT_MUCH:'Password does not much',
            ENTER:'Enter',
            LOGIN:'Login',
            LOGOUT:'Logout',
            SIGN_UP:'Sign up',
            REGISTRATION:'Registration',
            I_HAVE_IDEA:'I Have Idea!',
            SIGN_IN_WITH:'Sign in with',
            ADD_IDEA:'Add Idea',
            PROFILE:'Profile',
            MY_IDEAS:'My ideas',
            WRONG_EMAIL_PASSWORD:'Wrong email or password',
            USERNAME_IS_TOO_LONG:'Username is too long',
            EMAIL_IS_TOO_LONG:'Email is too long',
            ENTER_CORRECT_EMAIL:'Enter correct email',
            EMAIL_ALREADY_USED:'This email use by another user',
            PASSWORD_IS_TOO_LONG:'Password is too long',
            PASSWORD_IS_TOO_SHORT:'Password is too short',
            BUTTON_LANG_EN: 'en',
            BUTTON_LANG_RU: 'ru'
        });
        $translateProvider.translations('ru', {
            USERNAME: 'Имя прользователя',
            PASSWORD: 'Пароль',
            ENTER_USERNAME: "Введите имя пользователя",
            ENTER_PASSWORD:'Введите пароль',
            ENTER_EMAIL:'Введите email',
            CONFIRM_PASSWORD:'Подтвердить пароль',
            PASSWORD_DOES_NOT_MUCH:'Пароль не совпадает',
            ENTER:'Вход',
            LOGIN:'Войти',
            LOGOUT:'Выйти',
            SIGN_UP:'Зарегистрироваться',
            REGISTRATION:'Регистрация',
            I_HAVE_IDEA:'У меня есть идея!',
            SIGN_IN_WITH:'Войти через',
            ADD_IDEA:'Добавить идею',
            PROFILE:'Профиль',
            MY_IDEAS:'Мои идеи',
            WRONG_EMAIL_PASSWORD:'Неверный email и/или пароль',
            USERNAME_IS_TOO_LONG:'Имя пользователя слишком длинное',
            EMAIL_IS_TOO_LONG:'E-mail пользователя слишком длинный',
            ENTER_CORRECT_EMAIL:'Введите корректный e-mail',
            EMAIL_ALREADY_USED:'Данный e-mail занят другим пользователем',
            PASSWORD_IS_TOO_LONG:'Пароль пользователя слишком длинный',
            PASSWORD_IS_TOO_SHORT:'Пароль пользователя слишком короткий',
            BUTTON_LANG_EN: 'en',
            BUTTON_LANG_RU: 'ru'
        });
        $translateProvider.useSanitizeValueStrategy('sanitize');
        $translateProvider.preferredLanguage('ru');

    }



       run.$inject = ['$rootScope', '$location', 'sessionService'];
           function run($rootScope, $location, sessionService) {
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
                       restrictedPage = $.inArray($location.path(), ['/login']) !== -1;
                       if (restrictedPage) {
                           $location.path("/home");
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


})();

