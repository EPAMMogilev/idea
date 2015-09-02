'use strict';

exports.toBeChangedByOne = function() {
    return {
        compare: function(actual, expected) {
            return { pass: (actual == expected + 1) || (actual == expected - 1) };
        }
    };
}

exports.testIncrementAndDecrementRating = function (page, param) {
	var initialRating = page.getRating(param);
	page.like(param);
	expect(page.getRating(param)).toBeChangedByOne(initialRating);
	page.like(param);
	expect(page.getRating(param)).toBe(initialRating);
}