describe('rest factory testing', function(){

	var ideaFactory, $httpBackend;

	beforeEach(module('ideaFactories'));

    var ideas = [{
    		id: 1
    	},
    	{
    		id: 2
    	}];

    var responseIdeas = [];

	beforeEach(inject(function(restFactory, _$httpBackend_) {
		//ideaFactories = $factory('restFactory', ideaFactories2);
		ideaFactory = restFactory;
		$httpBackend = _$httpBackend_;
	}));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    function expectIdeasResponse() {
        expect(responseIdeas.length).toBe(2);
        expect(responseIdeas[0].id).toBe(ideas[0].id);
        expect(responseIdeas[1].id).toBe(ideas[1].id);
    }

	// Verify that the factory can be instantiated
	it('should be instantiable', function () {
		expect(ideaFactory).toBeDefined();
	});

    it('get ideas of user with tag and query', function() {
	    $httpBackend.expectGET('api/v1/ideas?page=0&size=5&sort=rating,desc&sort=title,asc&userId=1&tagId=2&query=queryString').respond(ideas);
	    ideaFactory.ideas().getPage({userId: '1', page: '0', size: '5', sort: 'rating,desc', tagId: '2', query: 'queryString'})
	    	.$promise.then(function (response) {
	    		responseIdeas = response;
	            expect(responseIdeas.length).toBe(2);
	            expect(responseIdeas[0].id).toBe(ideas[0].id);
	            expect(responseIdeas[1].id).toBe(ideas[1].id);
	    	});
        $httpBackend.flush();
    });

});

