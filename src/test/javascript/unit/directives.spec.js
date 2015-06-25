describe('directives unit testing', function(){
	var elem, $compile, $rootScope;

	beforeEach(module('app.directives')); // Name of the module my directive is in
	beforeEach(module('ideaFactories')); // dependency
	beforeEach(module('ui.router'));
	beforeEach(module('app.filters'));
	beforeEach(module('idea.templates')); // The external template file referenced by templateUrl

	beforeEach(inject(function(_$compile_, _$rootScope_){
		$compile = _$compile_;
		$rootScope = _$rootScope_;
	}));

	it('Replace the element with the appropriate content and element has class "active"', function() {
		var element = $compile('<tag-button active="true">Все</tag-button>')($rootScope);
		expect(element.hasClass('active')).toBe(true);
	});
	
	it('Replace the element with the templateUrl <div class="idea">...</div>)', function() {
		$rootScope.idea = {
			title: 'Idea title',
			description: 'Idea description',
			rating: '7',
		};
		// Create an instance of the directive
		elem = angular.element('<show-idea idea="idea"></show-idea>');
		$compile(elem)($rootScope); // Compile the directive
		$rootScope.$digest(); // Update the HTML
		
		// Get the isolate scope for the directive
		var isoScope = elem.isolateScope();
		
		// Make our assertions
		expect(isoScope.idea.title).toBe('Idea title');
		expect(isoScope.idea.description).toBe('Idea description');
		expect(isoScope.idea.rating).toBe('7');
	}); 

});

