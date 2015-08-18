'use strict';
describe("app.services module", function() {

   var details;

   beforeEach(function() {
     module('app.services');

     module(function($provide) {
       $provide.factory('ideasFactory', function() {
          var publicMethod = {
          updateIdea: function () {}
          }
          return publicMethod;
       });
     });
   });

   describe('authorizationService', function () {
       var $httpBackend, location;

       var testAuthorizationService;

	   var role = {
               id: 1,
               authority: "ROLE_USER"
    	   };
	   var user = {
               id: 1,
               userName: "firstUser",
               authorities: [role]
    	   };
	   var idea = {
           id: 1,
           author: user
	   };

	    beforeEach(function() {
	    	module('ideaFactories');
	   });

       beforeEach(inject(function (_$httpBackend_, authorizationService, $location) {
    	   $httpBackend = _$httpBackend_;
           testAuthorizationService = authorizationService;
           location = $location;
           spyOn(location, 'path');
       }));


       afterEach(function() {
           $httpBackend.verifyNoOutstandingExpectation();
           $httpBackend.verifyNoOutstandingRequest();
       });

       it('check redirect from Edit page to Access Denied if user is not an author or admin', inject(function(_$rootScope_) {
    	   var currUser = {
                   id: 2,
                   userName: "secondUser",
                   authorities: [role]
        	   };
    	   _$rootScope_.currentUser = currUser;
           $httpBackend.expectGET('api/v1/ideas/1').respond(idea);
           testAuthorizationService.redirectFromEditToAccessDeniedIfNeeded(idea);
           $httpBackend.flush();
           expect(location.path).toHaveBeenCalledWith('/accessDenied');
       }));

       it('check no redirect from Edit page to Access Denied if user is an author', inject(function(_$rootScope_) {
    	   _$rootScope_.currentUser = user;
           $httpBackend.expectGET('api/v1/ideas/1').respond(idea);
           testAuthorizationService.redirectFromEditToAccessDeniedIfNeeded(idea);
           $httpBackend.flush();
           expect(location.path).not.toHaveBeenCalledWith('/accessDenied');
       }));

       it('check no redirect from Edit page to Access Denied if user is admin', inject(function(_$rootScope_) {
    	   var roleAdmin = {
                   id: 1,
                   authority: "ROLE_ADMIN"
        	   };
    	   var currUser = {
                   id: 3,
                   userName: "admin",
                   authorities: [roleAdmin]
        	   };
    	   _$rootScope_.currentUser = currUser;
           testAuthorizationService.redirectFromEditToAccessDeniedIfNeeded(idea);
           expect(location.path).not.toHaveBeenCalledWith('/accessDenied');
       }));
    });

   describe('ideasService', function () {

	   var service;
       var ideasFactoryMock = {
    		   getPageOfUser: function(params, user){
    		   },
    		   getPageOfUserWithQuery: function(params, user, query) {
    		   },
    		   getPageOfUserWithTag: function(params, user, tag) {
    		   },
    		   getPageOfUserWithTagAndQuery: function(params, user, tag, query) {
    		   },
    		   getPage: function(params){
    		   },
    		   getPageWithQuery: function(params, query) {
    		   },
    		   getPageWithTag: function(params, tag) {
    		   },
    		   getPageWithTagAndQuery: function(params, tag, query) {
    		   }
       };


	    beforeEach(function() {
	    	module('ideaFactories');
	        spyOn(ideasFactoryMock, 'getPageOfUser');
	        spyOn(ideasFactoryMock, 'getPageOfUserWithQuery');
	        spyOn(ideasFactoryMock, 'getPageOfUserWithTag');
	        spyOn(ideasFactoryMock, 'getPageOfUserWithTagAndQuery');
	        spyOn(ideasFactoryMock, 'getPage');
	        spyOn(ideasFactoryMock, 'getPageWithQuery');
	        spyOn(ideasFactoryMock, 'getPageWithTag');
	        spyOn(ideasFactoryMock, 'getPageWithTagAndQuery');

            module(function($provide) {
                $provide.value('ideasFactory', ideasFactoryMock);
              });
	   });

       beforeEach(inject(function (ideasService) {
    	   service = ideasService;
       }));


       it('get page of user', function() {
           service.getPage({}, 1, null, null);
           expect(ideasFactoryMock.getPageOfUser).toHaveBeenCalledWith({}, 1);
       });

       it('get page of user with query', function() {
           service.getPage({}, 1, null, "query");
           expect(ideasFactoryMock.getPageOfUserWithQuery).toHaveBeenCalledWith({}, 1, "query");
       });

       it('get page of user with tag', function() {
           service.getPage({}, 1, 2, null);
           expect(ideasFactoryMock.getPageOfUserWithTag).toHaveBeenCalledWith({}, 1, 2);
       });

       it('get page of user with tag and query', function() {
           service.getPage({}, 1, 2, "query");
           expect(ideasFactoryMock.getPageOfUserWithTagAndQuery).toHaveBeenCalledWith({}, 1, 2, "query");
       });

       it('get page', function() {
           service.getPage({}, null, null, null);
           expect(ideasFactoryMock.getPage).toHaveBeenCalledWith({});
       });

       it('get page with query', function() {
           service.getPage({}, null, null, "query");
           expect(ideasFactoryMock.getPageWithQuery).toHaveBeenCalledWith({}, "query");
       });

       it('get page with tag', function() {
           service.getPage({}, null, 2, null);
           expect(ideasFactoryMock.getPageWithTag).toHaveBeenCalledWith({}, 2);
       });

       it('get page with tag and query', function() {
           service.getPage({}, null, 2, "query");
           expect(ideasFactoryMock.getPageWithTagAndQuery).toHaveBeenCalledWith({}, 2, "query");
       });
    });
	/*beforeEach(inject(function(detailsService) {
		details = detailsService;
	}));*/

	/* !! Failed */
   /*describe('rate', function() {
	it('should add one to rate of idea', inject(function(Rate) {
     expect(Rate.changeRate('1', new Idea()).rating).toEqual(1);
    }))
    });*/


   /*describe('detailsService', function() {
     it('should save data in detailsService', inject(function(detailsService) {
        //expect(detailsService).toBeDefined();
        expect(details).toBeDefined();

        details.setData(new dataObject());
        expect(details._data.data).toEqual(1);
    }))
    });*/
});

function Idea(rating) {
    this.rating = 0;
};


function dataObject() {
    this.data = 1;
};

