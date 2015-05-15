describe('rest factory testing', function(){

	var ideaFactory;

	beforeEach(module('ideaFactories'));

	beforeEach(inject(function(restFactory) {
		//ideaFactories = $factory('restFactory', ideaFactories2);
		ideaFactory = restFactory;
	}));

	// Verify that the factory can be instantiated
	it('should be instantiable', function () {
		expect(ideaFactory).toBeDefined();		
	});

});

	