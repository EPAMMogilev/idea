//------------------------------------------------------------------------------------------------
// addNewIdea
//------------------------------------------------------------------------------------------------
describe('Add Idea controllers testing', function(){

	var state;

	//controllers;
	var addNewIdeaTest;

	//scopes
	var addNewIdeaScope;

	beforeEach(angular.mock.module('ui.router'));
	beforeEach(angular.mock.module('app.controllers'));
	beforeEach(angular.mock.module('app.services'));
	beforeEach(angular.mock.module('ideaFactories'));
	//beforeEach(angular.mock.module('ideaApp'));


	beforeEach(inject(function($state, $rootScope, $controller, $window) {
		addNewIdeaScope = $rootScope.$new();

		state = $state;
		window = $window;

		//details
		var vIdeaDetails = {
				description: 'Some text'
			};

		//init serviceInvoke
		serviceInvoke = function(aId){
			return{
				then: function(func1){
					func1.apply(vIdeaDetails);
				}
			}
		};

		myServiceInvoke = {
			getIdeaById: serviceInvoke
		};

		//details service invoke
		dServiceInvoke = {
			getCategories: function(){
				return ['op1', 'op2'];
			}
		};


		addNewIdeaTest = function(){
				return $controller('addNewIdea', {
					$scope:addNewIdeaScope,
					$window:window,
					$modal:function(){},
					detailsService:dServiceInvoke,
					ideasFactory: myServiceInvoke,
					mapGeoService: function(){},
					imgur: function(){},
					dialogs: function(){},
					Upload: function(){}
				});
		};
	}));

	// Verify that the factory can be instantiated
	it('add new idea controller should be instantiable', function () {
		expect(addNewIdeaTest).toBeDefined();
	});

	//look at scope variable in controller
	it('add new idea controller check scope', function () {
		// Make our assertions
		expect(addNewIdeaScope).toBeDefined();
		//addNewIdeaTest
		var ctrl = addNewIdeaTest();
		expect(ctrl).toBeDefined();

		expect(addNewIdeaScope.bottomButtonName).toBeDefined();
		expect(addNewIdeaScope.bottomButtonName).toEqual('ADD');
		expect(addNewIdeaScope.data).toBeNull();
	});

});


//------------------------------------------------------------------------------------------------
// updateIdeaTest
//------------------------------------------------------------------------------------------------
describe('Update Idea controllers testing', function(){

	var state;

	//controllers;
	var updateIdeaTest;

	//scopes
	var updateIdeaScope;

	beforeEach(angular.mock.module('ui.router'));
	beforeEach(angular.mock.module('app.controllers'));
	beforeEach(angular.mock.module('app.services'));
	beforeEach(angular.mock.module('ideaFactories'));
	//beforeEach(angular.mock.module('ideaApp'));


	beforeEach(inject(function($state, $rootScope, $controller) {
		updateIdeaScope = $rootScope.$new();

		state = $state;

		//details
		var vIdeaDetails = {
				description: 'Some text'
			};

		//updateIdea
		updateIdeaTest = function(){
				return $controller('updateIdea', {
					$scope:updateIdeaScope,
					$modal:function(){},
					$state:state,
					ideaDetails: vIdeaDetails,
					imgur: function(){},
					dialogs: function(){},
					Upload: function(){}
				});
		};
	}));

	// Verify that the factory can be instantiated
	it('update idea details controller should be instantiable', function () {
		expect(updateIdeaTest).toBeDefined();
	});

	//look at scope variable in controller
	it('update new idea controller check scope', function () {
		expect(updateIdeaScope).toBeDefined();

		var ctrl = updateIdeaTest();
		expect(ctrl).toBeDefined();

		// Make our assertions
		expect(updateIdeaScope.bottomButtonName).toBeDefined();
		expect(updateIdeaScope.bottomButtonName).toBe('UPDATE');
		expect(updateIdeaScope.data).toBeDefined();
	});
});

//------------------------------------------------------------------------------------------------
// detailsIdeaTest
//------------------------------------------------------------------------------------------------
describe('Idea details controllers testing', function(){

	var state;

	//controllers;
	var detailsIdeaTest;

	//service mock
	var ideaByIdInvoke, commentsByIdeaIdInvoke, getlikedUsersInvoke, addCommentInvoke;
	var myIdeasFactory, myCommentsFactory, myUsersService;

	//scopes
	var detailsIdeaScope;

	var comments = [{
		id: 11
	},
	{
		id: 12
	}];

	var vIdeaDetails = {
			id:1,
			description: 'Some text',
			likedUsers: []
		};

	var deferedIdeaById, deferedCommentsByIdeaId, deferredAddComment;

	beforeEach(angular.mock.module('ui.router'));
	beforeEach(angular.mock.module('app.controllers'));
	beforeEach(angular.mock.module('app.services'));


	beforeEach(inject(function($state, $rootScope, $controller, $q) {
		detailsIdeaScope = $rootScope.$new();

		state = $state;
		deferedIdeaById = $q.defer();
		deferedIdeaById.resolve(vIdeaDetails);
		deferedCommentsByIdeaId = $q.defer();
		deferedCommentsByIdeaId.resolve(comments);

		deferredAddComment = $q.defer();

		//init ideaByIdInvoke
		ideaByIdInvoke = function(aId){
			return deferedIdeaById.promise;
		};

		//init commentsByIdeaIdInvoke
		commentsByIdeaIdInvoke = function(aId){
			return deferedCommentsByIdeaId.promise;
		};


		getlikedUsersInvoke = function(aId){
			return "";
		};

		myCommentsFactory = {
			getCommentsPageByIdeaId: commentsByIdeaIdInvoke
		};

		myIdeasFactory = {
			getIdeaById: ideaByIdInvoke
		};

		myUsersService = {
				getlikedUsersListAsString: getlikedUsersInvoke
			};

		//detailsCtrl
		detailsIdeaTest  = function(){
				return $controller('detailsCtrl', {
					$scope:detailsIdeaScope,
					$state:state,
					ideasFactory: myIdeasFactory,
					commentsFactory: myCommentsFactory,
					usersService: myUsersService,
					ideaDetails: vIdeaDetails,
					$modal: {}
				});
		};
	}));

	// Verify that the factory can be instantiated
	it('idea details controller should be instantiable', function () {
		expect(detailsIdeaTest).toBeDefined();
	});

	//look at scope variable in controller
	it('idea details controller check scope', function () {
		expect(detailsIdeaScope).toBeDefined();

		var ctrl = detailsIdeaTest();
		expect(ctrl).toBeDefined();
		expect(detailsIdeaScope.data).toBeNull();
		expect(ctrl.comments).toBeNull();

		detailsIdeaScope.$root.$digest();
		expect(ctrl.comments.length).toBe(comments.length);
		expect(ctrl.comments[0].id).toBe(comments[0].id);
		expect(ctrl.comments[1].id).toBe(comments[1].id);
		expect(detailsIdeaScope.data.id).toBe(vIdeaDetails.id);
		expect(detailsIdeaScope.likedUsersList).toBe("");
	});


	it('add comment', function () {
		var body = "new";
		detailsIdeaScope.commentBody = body;
		var comment = {id: 3, body: body};
		deferredAddComment.resolve(comment);
		myCommentsFactory.createComment = function(request) {
			return deferredAddComment.promise;
		}

		var ctrl = detailsIdeaTest();

		detailsIdeaScope.$root.$digest();
		detailsIdeaScope.addComment();

		detailsIdeaScope.$root.$digest();
		expect(ctrl.comments.length).toBe(3);
		expect(ctrl.comments[0].id).toBe(comment.id);
		expect(ctrl.comments[0].body).toBe(body);
	});

});


//------------------------------------------------------------------------------------------------
// ideasCtrlTest
//------------------------------------------------------------------------------------------------

describe('Idea controllers testing', function(){

	var state;

	//controllers;
	var ideasCtrlTest;

	//scopes
	var ideasCtrlIdeaScope;

	beforeEach(angular.mock.module('ui.router'));
	beforeEach(angular.mock.module('app.controllers'));
	beforeEach(angular.mock.module('app.services'));
	beforeEach(angular.mock.module('ideaFactories'));
	//beforeEach(angular.mock.module('ideaApp'));


	beforeEach(inject(function($state, $rootScope, $controller) {
		ideasCtrlIdeaScope = $rootScope.$new();

		state = $state;

		//ideasCtrl
		ideasCtrlTest  = function(){
				return $controller('ideasCtrl', {
					$scope:ideasCtrlIdeaScope
				});
		};
	}));

	// Verify that the factory can be instantiated
	it('idea details controller should be instantiable', function () {
		expect(ideasCtrlTest).toBeDefined();
	});

	//look at scope variable in controller
	it('idea controller check scope', function () {

		var ctrl = ideasCtrlTest();
		expect(ctrl).toBeDefined();
	});


});

//------------------------------------------------------------------------------------------------
//ideasSearchCtrlTest
//------------------------------------------------------------------------------------------------

describe('Ideas Search controllers testing', function(){

	var ideasSearchCtrlTest, rootScope, location, scope;

	beforeEach(angular.mock.module('app.controllers'));

	beforeEach(inject(function($rootScope, $controller, $location) {
		scope = $rootScope.$new();
		rootScope = $rootScope;
		location = $location;

		//ideasSearchCtrl
		ideasSearchCtrlTest  = function(){
				return $controller('ideasSearchCtrl', {
					$scope:scope,
					$rootScope: rootScope,
					$location: location
				});
		};
	}));

	// Verify that the factory can be instantiated
	it('ideas search controller should be instantiable', function () {
		expect(ideasSearchCtrlTest).toBeDefined();
	});


	it('ideas search controller should change query', function () {
		var ctrl = ideasSearchCtrlTest();
		spyOn(rootScope, '$broadcast');
		ctrl.changeQuery();
		expect(rootScope.$broadcast).toHaveBeenCalledWith('query-update', null);
	});

	it('ideas search controller should check that search is visible', function () {
		location.path('/home');
		var ctrl = ideasSearchCtrlTest();
		expect(ctrl.isSearchVisible()).toBe(true);
	});

	it('ideas search controller should check that search is not visible', function () {
		location.path('/login');
		var ctrl = ideasSearchCtrlTest();
		expect(ctrl.isSearchVisible()).toBe(false);
	});


});

