describe('rest factory testing', function(){

	var ideaFactory, $httpBackend;

	beforeEach(module('ideaFactories'));

	beforeEach(inject(function(restFactory, _$httpBackend_) {
		//ideaFactories = $factory('restFactory', ideaFactories2);
		ideaFactory = restFactory;
		$httpBackend = _$httpBackend_;
	}));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

	// Verify that the factory can be instantiated
	it('should be instantiable', function () {
		expect(ideaFactory).toBeDefined();
	});

    function expectResponse(response, array) {
        expect(response.length).toBe(array.length);
        for(var i = 0; i < response.length; i++) {
        	expect(response[i].id).toBe(array[i].id);
        }
    }

    it('get ideas of user with tag and query', function() {
        var ideas = [{
    		id: 1
    	},
    	{
    		id: 2
    	}];
	    $httpBackend.expectGET('api/v1/ideas?page=0&size=5&sort=rating,desc&sort=title,asc&userId=1&tagId=2&query=queryString').respond(ideas);
	    ideaFactory.ideas().getPage({userId: '1', page: '0', size: '5', sort: 'rating,desc', tagId: '2', query: 'queryString'})
	    	.$promise.then(function (response) {
	    		expectResponse(response, ideas);
	    	});
        $httpBackend.flush();
    });

    it('get comments by ideaId', function() {
    	var comments = [{
    		id: 11
    	},
    	{
    		id: 12
    	}];
	    $httpBackend.expectGET('api/v1/ideas/1/comments').respond(comments);
	    ideaFactory.ideas().getComments({id: '1'})
	    	.$promise.then(function (response) {
	    		expectResponse(response, comments);
	    	});
        $httpBackend.flush();
    });

});

