describe('directives unit testing', function(){
	var elem, $compile, $rootScope;

	beforeEach(module('app.directives')); // Name of the module my directive is in
	beforeEach(module('ideaFactories')); // dependency
	beforeEach(module('ui.router'));
	beforeEach(module('app.filters'));
	beforeEach(module('idea.templates')); // The external template file referenced by templateUrl
	beforeEach(module('app.services'));

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

	it('Replace the element with the attribute show-comment', function() {
		$rootScope.comment = {
			body: 'Good',
			rating: '3',
			author: {
				username: 'FirstUser'
			},
			likedUsers: [],
			liked: false
		};
		// Create an instance of the directive
		elem = angular.element('<div show-comment comment="comment" is-authenticated="true">');
		$compile(elem)($rootScope); // Compile the directive
		$rootScope.$digest(); // Update the HTML

		// Get the isolate scope for the directive
		var isoScope = elem.isolateScope();

		// Make our assertions
		expect(isoScope.comment.body).toBe('Good');
		expect(isoScope.comment.author.username).toBe('FirstUser');
		expect(isoScope.comment.rating).toBe('3');
	});

});

describe('Directive: compareto', function () {

  // load the directive's module
  beforeEach(module('app.directives'));

  var scope;

  beforeEach(inject(function($compile, $rootScope) {
    scope = $rootScope.$new();
    var element = angular.element(
      '<form name="form">' +
      '<input ng-model="model.password" type="text" name="password" id="password">' +
      '<input compare-to="model.password" ng-model="model.confirmPassword" type="text" name="confirmPassword" id="confirmPassword">' +
      '</form>'
    );

    scope.model = { password: '', confirmPassword: 'Fd565$dD' };
    $compile(element)(scope);

  }));

  describe('Compare To', function() {

    it('Should contain two elements with the same value', function() {

      // element target
      scope.form.password.$setViewValue('Fd565$dD');
      scope.$digest();
      expect(scope.form.confirmPassword.$error.errorCompareTo).toEqual(false);
    });

  });
});
