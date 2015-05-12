'use strict';
describe("app.services module", function() {
   beforeEach(module('mainModule'));
   describe('rate', function() {
   
     it('should add one to rate of idea', inject(function(Rate) {
     expect(Rate.changeRate('1', new Idea()).rate).toEqual(1); 
    }))
    });
    
     describe('tags', function() {
   
     it('should return tags with tag', inject(function(IdeasSelectedByTag) {
     expect(IdeasSelectedByTag.getIdeas("sport").length).toEqual(2); 
    }))
  })
});