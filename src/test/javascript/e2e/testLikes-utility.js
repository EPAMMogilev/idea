'use strict';

exports.toBeChangedByOne = function() {
    return {
        compare: function(actual, expected) {
            return { pass: (actual == expected + 1) || (actual == expected - 1) };
        }
    };
}

exports.testIncrementAndDecrementRating = function (pageOrElem, param) {
	var initialRating = pageOrElem.getRating(param);
	pageOrElem.like(param);
	expect(pageOrElem.getRating(param)).toBeChangedByOne(initialRating);
	pageOrElem.like(param);
	expect(pageOrElem.getRating(param)).toBe(initialRating);
}