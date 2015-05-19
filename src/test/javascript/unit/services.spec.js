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

	beforeEach(inject(function(detailsService) {
		details = detailsService;
	}));

   describe('rate', function() {

     it('should add one to rate of idea', inject(function(Rate) {
     expect(Rate.changeRate('1', new Idea()).rating).toEqual(1);
    }))
    });


   describe('detailsService', function() {
     it('should save data in detailsService', inject(function(detailsService) {
        //expect(detailsService).toBeDefined();
        expect(details).toBeDefined();

        details.setData(new dataObject());
        expect(details._data.data).toEqual(1);
    }))
    });
});

function Idea(rating) {
    this.rating = 0;
};


function dataObject() {
    this.data = 1;
};

