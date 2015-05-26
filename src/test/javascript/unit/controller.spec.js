describe('Idea controllers testing', function(){

	var scope;
	var state;

	//controllers;
	var addNewIdeaTest;
	var updateIdeaTest;
	var detailsIdeaTest;
	var ideasCtrlTest;

	//scopes
	var addNewIdeaScope;
	var detailsIdeaScope;
	var ideasCtrlIdeaScope;
	var updateIdeaScope;

	//state
	var state;

	beforeEach(angular.mock.module('ui.router'));
	beforeEach(angular.mock.module('app.controllers'));
	beforeEach(angular.mock.module('app.services'));
	beforeEach(angular.mock.module('ideaFactories'));
	//beforeEach(angular.mock.module('ideaApp'));


	beforeEach(inject(function($state, $rootScope, $controller) {
		addNewIdeaScope = $rootScope.$new();
		detailsIdeaScope = $rootScope.$new();
		ideasCtrlIdeaScope = $rootScope.$new();
		updateIdeaScope = $rootScope.$new();

		state = $state;

		//details
		var vIdeaDetails = {
				description: 'Some text'
			};

		addNewIdeaTest = function(){
				return $controller('addNewIdea', {
					$scope:addNewIdeaScope
				});
		};

		//detailsCtrl
		detailsIdeaTest  = function(){
				return $controller('detailsCtrl', {
					$scope:detailsIdeaScope,
					$state:state,
					ideaDetails: vIdeaDetails
				});
		};
		//ideasCtrl
		ideasCtrlTest  = function(){
				return $controller('ideasCtrl', {
					$scope:ideasCtrlIdeaScope
				});
		};
		//updateIdea
		updateIdeaTest = function(){
				return $controller('updateIdea', {
					$scope:updateIdeaScope,
					$state:state,
					ideaDetails: vIdeaDetails
				});
		};
	}));

	// Verify that the factory can be instantiated
	it('add new idea controller should be instantiable', function () {
		expect(addNewIdeaTest).toBeDefined();
	});
	it('update idea details controller should be instantiable', function () {
		expect(updateIdeaTest).toBeDefined();
	});
	it('idea details controller should be instantiable', function () {
		expect(detailsIdeaTest).toBeDefined();
	});
	it('idea details controller should be instantiable', function () {
		expect(detailsIdeaTest).toBeDefined();
	});

	//look at scope variable in controller	
	it('add new idea controller check scope', function () {
		// Make our assertions
		expect(addNewIdeaScope).toBeDefined();
		//addNewIdeaTest
		var ctrl = addNewIdeaTest();
		expect(ctrl).toBeDefined();
		
		expect(addNewIdeaScope.bottomButtonName).toBeDefined();
		expect(addNewIdeaScope.bottomButtonName).toEqual('Добавить');
		expect(addNewIdeaScope.data).toBeNull();
	});

	//look at scope variable in controller	
	it('update new idea controller check scope', function () {
		expect(updateIdeaScope).toBeDefined();

		var ctrl = updateIdeaTest();
		expect(ctrl).toBeDefined();

		// Make our assertions		
		expect(updateIdeaScope.bottomButtonName).toBeDefined();
		expect(updateIdeaScope.bottomButtonName).toBe('Обновить');
		expect(updateIdeaScope.data).toBeDefined();
	});


	//look at scope variable in controller	
	it('idea details controller check scope', function () {
		expect(detailsIdeaScope).toBeDefined();

		var ctrl = detailsIdeaTest();
		expect(ctrl).toBeDefined();
		// Make our assertions
		expect(ctrl.data).toBeDefined();
	});

	//look at scope variable in controller	
	it('idea controller check scope', function () {

		var ctrl = ideasCtrlTest();
		expect(ctrl).toBeDefined();
	});


});

