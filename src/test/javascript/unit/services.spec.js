'use strict';
describe("app.services module", function() {
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
   describe('rate', function() {

     it('should add one to rate of idea', inject(function(Rate) {
     expect(Rate.changeRate('1', new Idea()).rating).toEqual(1);
    }))
    });
});

function Idea(rating) {
    this.rating = 0;
};
