describe('factories unit testing', function(){

	var ideaFactories;

	beforeEach(module('ideaFactories'));

	beforeEach(inject(function(restFactory) {
		//ideaFactories = $factory('restFactory', ideaFactories2);
		ideaFactories = restFactory;
	}));

	// Verify that the filters can be instantiated
	it('should be instantiable', function () {
		expect(ideaFactories).toBeDefined();		
	});

});

	