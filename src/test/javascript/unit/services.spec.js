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

   describe('authenticationService', function () {
       var $httpBackend, location;

       var testAuthorizationService;

	    beforeEach(function() {
	    	module('ideaFactories');
	   });

       beforeEach(inject(function (_$httpBackend_, authorizationService, $location) {
    	   $httpBackend = _$httpBackend_;
           testAuthorizationService = authorizationService;
           location = $location;
       }));


       afterEach(function() {
           $httpBackend.verifyNoOutstandingExpectation();
           $httpBackend.verifyNoOutstandingRequest();
       });

       it('check redirect from Edit page to Access Denied if user is not an author or admin', function () {
    	   var user = {
                   id: 1,
                   userName: "firstUser"
        	   };
    	   var idea = {
               id: 1,
               author: user
    	   };
           spyOn(location, 'path');
           $httpBackend.expectGET('api/v1/ideas/1').respond(idea);
           testAuthorizationService.redirectFromEditToAccessDeniedIfNeeded(idea);
           $httpBackend.flush();
           expect(location.path).toHaveBeenCalledWith('/accessDenied');
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

