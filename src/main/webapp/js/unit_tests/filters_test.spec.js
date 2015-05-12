'use strict';
describe("app.filteres module", function() {
   beforeEach(module('mainModule'));
   describe('Create span with class \'highlight\' around each mathing string.', function() {
   
     it('should add a span with class \'highlight\' around each mathing string.', inject(function ($sce, $filter) {
      // Execute
      var result = $filter('highlightTextInDescriptionOfIdea')('My str', 'str');

      // Test
      expect(result.$$unwrapTrustedValue()).toEqual('My <span class="highlight">str</span>'); 
    }));
  });
});