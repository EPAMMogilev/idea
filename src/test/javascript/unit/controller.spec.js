describe('Idea controllers testing', function(){

	var scope;
	var $location;

	//controllers;
	var addNewIdeaTest;
	var updateIdeaTest;
	var detailsIdeaTest;
	var ideasCtrlTest;

	beforeEach(module('app.controllers'));

	beforeEach(inject(function($rootScope, $controller, _$location_) {
		$location = _$location_;
		scope = $rootScope.$new();

		addNewIdeaTest = function(){
				return $controller('addNewIdea', {
					'$scope':scope
				});
		};

		//detailsCtrl
		detailsIdeaTest  = function(){
				return $controller('detailsCtrl', {
					'$scope':scope
				});
		};
		//ideasCtrl
		ideasCtrlTest  = function(){
				return $controller('ideasCtrl', {
					'$scope':scope
				});
		};
		//updateIdea
		updateIdeaTest = function(){
				return $controller('updateIdea', {
					'$scope':scope
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

});

