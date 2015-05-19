describe('filters unit testing', function(){

	var highlightFilter;

	beforeEach(module('app.filters'));
	
	beforeEach(inject(function($filter) {
		highlightFilter = $filter('highlightTextInDescriptionOfIdea');
	}));
	
	// Verify that the filters can be instantiated
	it('should be instantiable', function () {
		expect(highlightFilter).toBeDefined();		
	});

});
